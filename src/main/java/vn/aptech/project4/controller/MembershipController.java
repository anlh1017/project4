package vn.aptech.project4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.aptech.project4.entity.Inventory;
import vn.aptech.project4.entity.Membership;
import vn.aptech.project4.entity.Order;
import vn.aptech.project4.repository.InventoryRepository;
import vn.aptech.project4.repository.MembershipRepository;
import vn.aptech.project4.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/membership")
public class MembershipController {
	private MembershipRepository membershipRepository;
	private InventoryRepository inventoryRepository;
	private OrderRepository orderRepository;
	private int lowStock;
	private int newOrder;

	public MembershipController(InventoryRepository inventoryRepository,MembershipRepository membershipRepository,OrderRepository orderRepository) {
		this.inventoryRepository = inventoryRepository;
		this.membershipRepository = membershipRepository;
		this.orderRepository = orderRepository;
	}
	public void getNotification(Model theModel){
		newOrder = 0;
		List<Order> orders = orderRepository.findAllByStatus(1);
		for(int i = 0; i<orders.size();i++){
			newOrder++;
		}
		if(newOrder==1){
			theModel.addAttribute("newOrderMsg",newOrder+" New Order");
		}else if (newOrder>1){
			theModel.addAttribute("newOrderMsg",newOrder+" New Orders");
		}else{
			theModel.addAttribute("newOrderMsg",null);
		}
		theModel.addAttribute("newOrder",newOrder);
		lowStock=0;
		List<Inventory> theList = inventoryRepository.findAll();
		for(Inventory temp:theList){
			if(temp.getQuantity()<temp.getSafetyStock()){
				lowStock+=1;
			}
		}
		if(lowStock==1){
			theModel.addAttribute("lowStockMsg",lowStock+" Item Inventory Low");
		}else if (lowStock>1){
			theModel.addAttribute("lowStockMsg",lowStock+" Items Inventory Low");
		}else{
			theModel.addAttribute("lowStockMsg",null);
		}
		theModel.addAttribute("lowInventory",lowStock);
	}

	@GetMapping("/list")
	public String ShowMembership(Model theModel, @ModelAttribute("successMsg")String message) {
		getNotification(theModel);
		if(message.isEmpty()){
			message=null;
		}
		theModel.addAttribute("successMsg",message);
		getNotification(theModel);
		List<Membership> membership = membershipRepository.findAll();
		theModel.addAttribute("membership", membership);
		return "membership-list";
	}
	@GetMapping("/createMembership")
	public String createMembership(Model theModel) {
		getNotification(theModel);
		Membership membership = new Membership();
		theModel.addAttribute("membership", membership);
		return "membership-create";
	}
	@PostMapping("/saveMembership")
	public String saveMembership(@ModelAttribute("membership") Membership membership, ModelMap theModelMap,RedirectAttributes redirectAttributes) {
		theModelMap.addAttribute("membership", membership);
		if(null == membership.getMembership_id()){
			redirectAttributes.addFlashAttribute("successMsg","Add New Membership ");
		}else{
			redirectAttributes.addFlashAttribute("successMsg","Update Membership ");
		}
		membershipRepository.save(membership);
		return"redirect:/admin/membership/list";
	}
	@GetMapping("/editMembership/{id}")
	public String editMembership(@PathVariable (value = "id") int id, Model theModel) {
		getNotification(theModel);
		Optional<Membership> membership= membershipRepository.findById(id);
		theModel.addAttribute("membership",membership);
		return "membership-edit";
	
	}
	@GetMapping("/deleteMembership/{id}")
	public String deleteMembership(@PathVariable (value = "id") int id, RedirectAttributes theModel) {
		try {
			this.membershipRepository.deleteById(id);
			theModel.addAttribute("message","Cannot delete, please check customer !");
		} catch (Exception e) {
			theModel.addAttribute("message",e.getMessage());
		}
		
		return"redirect:/admin/membership/list";
	}

}
