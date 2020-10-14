package vn.aptech.project4.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.aptech.project4.entity.Ingredient;
import vn.aptech.project4.entity.Product;
import vn.aptech.project4.entity.ProductIngredient;
import vn.aptech.project4.entity.Recipe;
import vn.aptech.project4.entity.Size;
import vn.aptech.project4.repository.IngredientRepository;
import vn.aptech.project4.repository.ProductRepository;
import vn.aptech.project4.repository.RecipeRepository;
import vn.aptech.project4.repository.SizeRepository;

@Controller
@RequestMapping("/admin/recipe")
public class RecipeController {
	private ProductRepository productRepository;
	private IngredientRepository ingredientRepository;
	private SizeRepository sizeRepository;
	private RecipeRepository recipeRepository;

	@Autowired
	public RecipeController(ProductRepository productRepository, IngredientRepository ingredientRepository,
			SizeRepository sizeRepository, RecipeRepository recipeRepository) {
		this.productRepository = productRepository;
		this.ingredientRepository = ingredientRepository;
		this.sizeRepository = sizeRepository;
		this.recipeRepository = recipeRepository;
	}

	@GetMapping("/view/{id}")
	public String viewIngredient(@PathVariable(value = "id") int theId, Model theModel) {
		Product theProduct = productRepository.findById(theId).get();
		List<Recipe> recipes = recipeRepository.findAll();
		List<Recipe> viewRecipes = new ArrayList<Recipe>();
		for(Recipe temp: recipes) {
			if(temp.getProductIngredient().getProduct().getId()==theId) {
				viewRecipes.add(temp);
			}
		}
		theModel.addAttribute("recipes", viewRecipes);
		theModel.addAttribute("product", theProduct);
		theModel.addAttribute("ingredients", ingredientRepository.findAll());
		theModel.addAttribute("size", sizeRepository.findAll());
		return "add-ingredient-product";
	}

	@GetMapping("/update/{id}")
	public String editIngredient(@PathVariable(value = "id") int theId, Model theModel) {
		Optional<Recipe> tempRecipe = recipeRepository.findById(theId);
		if (tempRecipe.isPresent()) {
			Recipe theRecipe = tempRecipe.get();
			theModel.addAttribute("recipe", theRecipe);

		}
		
		theModel.addAttribute("ingredients", ingredientRepository.findAll());
		theModel.addAttribute("size", sizeRepository.findAll());
		return "update-recipe";
	}

	@PostMapping("/create/{id}/ingredient")
	public String addIngredient(@PathVariable(value = "id") int id,
			@RequestParam(value = "ingredientId") int ingredientId, @RequestParam(value = "quantity") int quantity,
			@RequestParam(value = "sizeId") int sizeId, Model theModel) {
		System.out.println("Product ID: " + id);
		Product pro = productRepository.findById(id).get();

		System.out.println("Ingredient ID: " + ingredientId);
		Ingredient ingre = ingredientRepository.findById(ingredientId).get();

		System.out.println("Size ID: " + sizeId);
		Size size = sizeRepository.findById(sizeId).get();
		Recipe recipe = null;
		if (pro != null) {

			if (!pro.hasIngredient(ingre)) {
				/*
				 * pro.getProductIngredients().add(new ProductIngredient(ingre,pro,quantity));
				 * pro2 = productRepository.findById(id).get();
				 * pro2.setProductIngredients(pro.getProductIngredients());
				 */
				pro.addIngredient(ingre);

				productRepository.save(pro);
			}

			for (ProductIngredient temp : pro.getProductIngredients()) {
				if (temp.getIngredient().getId() == ingredientId && temp.getProduct().getId() == id) {
					recipe = new Recipe(temp, size, quantity);
				}
			}
			recipeRepository.save(recipe);
		}

		/*
		 * System.out.println("Product: " +pro);
		 * System.out.println("ProductIngredient: " + pro.getProductIngredients());
		 * List<ProductIngredient> proIngres = pro.getProductIngredients();
		 * System.out.println("ProIngres: " + proIngres);
		 */
		/*
		 * for( ProductIngredient temp:proIngres) { System.out.println("Inside Loop: "
		 * +temp); List<Recipe> recipes = new ArrayList<Recipe>(); Recipe recipe = new
		 * Recipe(temp.getId(),sizeId, quantity); System.out.println("Recipe: "
		 * +recipe); recipes.add(recipe); temp.setRecipes(recipes);
		 * System.out.println("Inside Loop ProductIngredient: " + temp.getRecipes()); }
		 * pro.setProductIngredients(proIngres); productRepository.save(pro);
		 * System.out.println("ProductIngredient: " + pro.getProductIngredients());
		 */

		return "redirect:/admin/recipe/view/" + id;

	}

	@PostMapping("/update/{id}")
	public String editRecipe(@PathVariable(value = "id") int theId, Model theModel,
			@RequestParam(value = "quantity") int quantity) {
		Recipe tempRecipe = recipeRepository.findById(theId).get();
		tempRecipe.setQuantity(quantity);
		int proId = tempRecipe.getProductIngredient().getProduct().getId();
		recipeRepository.save(tempRecipe);
		return "redirect:/admin/recipe/view/" + proId;
	}

	@GetMapping("/delete/{id}")
	public String deleteRecipe(@PathVariable(value = "id") int theId, Model theModel) {
		System.out.println("Finding Recipe by Id: " + theId);
		Optional<Recipe> tempRecipe = recipeRepository.findById(theId);

		Recipe deleteRecipe = null;
		if (tempRecipe.isPresent()) {
			deleteRecipe = tempRecipe.get();
		}
		recipeRepository.deleteRecipe(theId);



		/*
		 * if (proIngreId == temp.getId()) { if (temp.getRecipes().size() > 1) {
		 * System.out.println("not delete"); } else { System.out.println("delete");
		 * listProIngre.remove(temp); } }
		 */

		return "redirect:/admin/product/list";
	}
}
