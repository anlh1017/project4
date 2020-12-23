package vn.aptech.project4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import vn.aptech.project4.entity.Ingredient;
import vn.aptech.project4.entity.Inventory;
import vn.aptech.project4.repository.IngredientRepository;
import vn.aptech.project4.repository.InventoryRepository;

import java.util.Optional;

@Controller
@RequestMapping("/admin/ingredient")
public class IngredientController {
	private IngredientRepository ingredientRepository;
	private InventoryRepository inventoryRepository;

	@Autowired
	public IngredientController(IngredientRepository ingredientRepository,InventoryRepository inventoryRepository) {
		this.ingredientRepository = ingredientRepository;
		this.inventoryRepository = inventoryRepository;
	}

	@GetMapping("/list")
	public String showIngredients(Model theModel) {
			theModel.addAttribute("ingredients", ingredientRepository.findAll() );
		return "list-ingredients";
	}

	@GetMapping("/create")
	public String addIngredient(Model theModel) {
		theModel.addAttribute("ingredient", new Ingredient());
		return "form-ingredient";
	}

	@PostMapping("/create")
	public String addIngredient(@ModelAttribute(value = "ingredient") Ingredient ingredient, ModelMap theModelMap) {
		theModelMap.addAttribute("ingredient", ingredient);
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
		return "redirect:/admin/ingredient/list";
	}

	@GetMapping("/update/{id}")
	public String editIngredient(@PathVariable(value = "id") int theId, Model theModel) {
		Optional<Ingredient> theIngredient1 = ingredientRepository.findById(theId);
		if (theIngredient1.isPresent()) {
			Ingredient theIngredient = theIngredient1.get();
			theModel.addAttribute("ingredient", theIngredient);
		}
		return "update-ingredient";
	}

	@GetMapping("/delete/{id}")
	public String deleteIngredient(@PathVariable(value = "id") int theId, Model theModel) {
		Optional<Ingredient> theIngredient1 = ingredientRepository.findById(theId);
		if (theIngredient1.isPresent()) {
			Ingredient theIngredient = theIngredient1.get();
			ingredientRepository.delete(theIngredient);
		}
		return "redirect:/admin/ingredient/list";
	}
	@PostMapping("/update")
	public String updateIngredient(@ModelAttribute(value = "ingredient") Ingredient ingredient, ModelMap theModelMap) {
		theModelMap.addAttribute("ingredient", ingredient);
		ingredientRepository.save(ingredient);
		return "redirect:/admin/ingredient/list";
	}
}
