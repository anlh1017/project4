package vn.aptech.project4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.aptech.project4.entity.Inventory;
import vn.aptech.project4.entity.Membership;
import vn.aptech.project4.repository.InventoryRepository;
import vn.aptech.project4.repository.MembershipRepository;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/membership")
public class MembershipController {
	private MembershipRepository membershipRepository;
	private InventoryRepository inventoryRepository;
	private int lowStock;
	@Autowired
	public MembershipController(InventoryRepository inventoryRepository,MembershipRepository membershipRepository) {
		this.inventoryRepository = inventoryRepository;
		this.membershipRepository = membershipRepository;
	}
	public void getInventoryNotification(Model theModel){
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
		}
		theModel.addAttribute("lowInventory", lowStock);
	}

	@GetMapping("/list")
	public String ShowMembership(Model theModel, @ModelAttribute("successMsg")String message) {
		getInventoryNotification(theModel);
		if(message.isEmpty()){
			message=null;
		}
		theModel.addAttribute("successMsg",message);
		getInventoryNotification(theModel);
		List<Membership> membership = membershipRepository.findAll();
		theModel.addAttribute("membership", membership);
		return "membership-list";
	}
	@GetMapping("/createMembership")
	public String createMembership(Model theModel) {
		getInventoryNotification(theModel);
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
		getInventoryNotification(theModel);
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
