package vn.aptech.project4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.aptech.project4.entity.Ingredient;
import vn.aptech.project4.entity.Inventory;
import vn.aptech.project4.entity.Order;
import vn.aptech.project4.repository.IngredientRepository;
import vn.aptech.project4.repository.InventoryRepository;
import vn.aptech.project4.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/ingredient")
public class IngredientController {
	private IngredientRepository ingredientRepository;
	private InventoryRepository inventoryRepository;
	private OrderRepository orderRepository;
	private int lowStock;
	private int newOrder;

	public IngredientController(IngredientRepository ingredientRepository,InventoryRepository inventoryRepository, OrderRepository orderRepository) {
		this.ingredientRepository = ingredientRepository;
		this.inventoryRepository = inventoryRepository;
		this.orderRepository = orderRepository;
	}
	public void getNewOrderNotification(Model theModel){
		newOrder = 0;
		List<Order> orders = orderRepository.findAllByStatus(1);
		for (int i = 0; i < orders.size(); i++) {
			newOrder++;
		}
		if (newOrder == 1) {
			theModel.addAttribute("newOrderMsg", newOrder + " New Order");
		} else if (newOrder > 1) {
			theModel.addAttribute("newOrderMsg", newOrder + " New Orders");
		} else {
			theModel.addAttribute("newOrderMsg", null);
		}
		theModel.addAttribute("newOrder", newOrder);
		lowStock = 0;
		List<Inventory> theList = inventoryRepository.findAll();
		for (Inventory temp : theList) {
			if (temp.getQuantity() < temp.getSafetyStock()) {
				lowStock += 1;
			}
		}
		if (lowStock == 1) {
			theModel.addAttribute("lowStockMsg", lowStock + " Item Inventory Low");
		} else if (lowStock > 1) {
			theModel.addAttribute("lowStockMsg", lowStock + " Items Inventory Low");
		} else {
			theModel.addAttribute("lowStockMsg", null);
		}
		theModel.addAttribute("lowInventory", lowStock);
	}
	public void getInventoryNotification(Model theModel){
		getNewOrderNotification(theModel);
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
	public String showIngredients(Model theModel,@ModelAttribute(value = "successMsg")String message,@ModelAttribute(value = "errorMsg")String errMessage) {
		if(message.isEmpty()){
			message=null;
		}
		getInventoryNotification(theModel);
		theModel.addAttribute("successMsg",message);
		theModel.addAttribute("errorMsg",errMessage);
		getInventoryNotification(theModel);
		theModel.addAttribute("ingredients", ingredientRepository.findAll() );
		return "list-ingredients";
	}

	@GetMapping("/create")
	public String addIngredient(Model theModel,@ModelAttribute(value = "errorMsg")String message) {
		theModel.addAttribute("errorMsg",message);
		getInventoryNotification(theModel);
		theModel.addAttribute("ingredient", new Ingredient());
		return "form-ingredient";
	}

	@PostMapping("/create")
	public String addIngredient(@ModelAttribute(value = "ingredient") Ingredient ingredient, ModelMap theModelMap, RedirectAttributes redirectAttributes) {
		theModelMap.addAttribute("ingredient", ingredient);
		if(ingredient.getIngredientName().trim().isEmpty()){
			redirectAttributes.addFlashAttribute("errorMsg","Ingredient Name must not be empty!");
			return "redirect:/admin/ingredient/create";
		}
		if(ingredient.getUnit().isEmpty()){
			redirectAttributes.addFlashAttribute("errorMsg","Please select Unit!");
			return "redirect:/admin/ingredient/create";
		}
		Optional<Ingredient> testIngredient = ingredientRepository.findByIngredientName(ingredient.getIngredientName().trim());
		if(testIngredient.isPresent()){
			redirectAttributes.addFlashAttribute("errorMsg","Ingredient Name already exists!");
			return "redirect:/admin/ingredient/create";
		}
		ingredientRepository.save(ingredient);
		Inventory inventory = new Inventory();
		inventory.setIngredient(ingredient);
		inventory.setQuantity(0);
		inventory.setPrice(0);
        inventory.setStatus(1);
        inventory.setRatio(1);
		inventory.setUnit("N/A");
		inventory.setVendorName("N/A");
		inventoryRepository.save(inventory);
		redirectAttributes.addFlashAttribute("successMsg","Add New Ingredient Successfully");
		return "redirect:/admin/ingredient/list";
	}

	@GetMapping("/update/{id}")
	public String editIngredient(@PathVariable(value = "id") int theId, Model theModel) {
		getInventoryNotification(theModel);
		Optional<Ingredient> theIngredient1 = ingredientRepository.findById(theId);
		if (theIngredient1.isPresent()) {
			Ingredient theIngredient = theIngredient1.get();
			theModel.addAttribute("ingredient", theIngredient);
		}
		return "update-ingredient";
	}

	@GetMapping("/delete/{id}")
	public String deleteIngredient(@PathVariable(value = "id") int theId, Model theModel, RedirectAttributes redirectAttributes) {
		Optional<Ingredient> theIngredient1 = ingredientRepository.findById(theId);
		try {
			if (theIngredient1.isPresent()) {
				Ingredient theIngredient = theIngredient1.get();
				if(theIngredient.getInventory().getStatus()==1){
					inventoryRepository.delete(theIngredient.getInventory());
				}
				ingredientRepository.delete(theIngredient);
				redirectAttributes.addFlashAttribute("successMsg", "Delete Successfully!");
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMsg", "Cannot Delete, Please Check Inventory!");
		}
		
		return "redirect:/admin/ingredient/list";
	}
	@PostMapping("/update")
	public String updateIngredient(@ModelAttribute(value = "ingredient") Ingredient ingredient, ModelMap theModelMap, RedirectAttributes redirectAttributes) {
		theModelMap.addAttribute("ingredient", ingredient);
		ingredientRepository.save(ingredient);
		redirectAttributes.addFlashAttribute("successMsg","Update Ingredient Successfully");
		return "redirect:/admin/ingredient/list";
	}
}
