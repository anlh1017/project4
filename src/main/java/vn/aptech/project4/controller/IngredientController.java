package vn.aptech.project4.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.aptech.project4.entity.Ingredient;
import vn.aptech.project4.repository.IngredientRepository;

@Controller
@RequestMapping("/ingredient")
public class IngredientController {
	private IngredientRepository ingredientRepository;

	@Autowired
	public IngredientController(IngredientRepository ingredientRepository) {
		this.ingredientRepository = ingredientRepository;
	}

	@GetMapping("/list")
	public String showIngredients(Model theModel) {
		theModel.addAttribute("ingredients", ingredientRepository.findAll());
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
		return "redirect:/ingredient/list";
	}

	@GetMapping("/update/{id}")
	public String editIngredient(@PathVariable(value = "id") int theId, Model theModel) {
		Optional<Ingredient> theIngredient1 = ingredientRepository.findById(theId);
		if (theIngredient1.isPresent()) {
			Ingredient theIngredient = theIngredient1.get();
			theModel.addAttribute("ingredient", theIngredient);
		}
		return "redirect:/ingredient/list";
	}

	@GetMapping("/delete/{id}")
	public String deleteIngredient(@PathVariable(value = "id") int theId, Model theModel) {
		Optional<Ingredient> theIngredient1 = ingredientRepository.findById(theId);
		if (theIngredient1.isPresent()) {
			Ingredient theIngredient = theIngredient1.get();
			ingredientRepository.delete(theIngredient);
		}
		return "redirect:/ingredient/list";
	}
}
