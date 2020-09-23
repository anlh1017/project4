package vn.aptech.project4.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;

import vn.aptech.project4.entity.Ingredient;
import vn.aptech.project4.entity.Product;
import vn.aptech.project4.entity.ProductIngredient;
import vn.aptech.project4.repository.IngredientRepository;
import vn.aptech.project4.repository.ProductRepository;

@Controller
@RequestMapping("/product")
public class ProductController {
	private ProductRepository productRepository;
	private IngredientRepository ingredientRepository;

	@Autowired
	public ProductController(ProductRepository productRepository, IngredientRepository ingredientRepository) {
		this.productRepository = productRepository;
		this.ingredientRepository = ingredientRepository;
	}

	@GetMapping("/list")
	public String showProducts(Model theModel) {
		theModel.addAttribute("products", productRepository.findAll());
		return "list-products";
	}

	@GetMapping("/create")
	public String addProduct(Model theModel) {
		theModel.addAttribute("product", new Product());
		return "add-products";
	}

	@PostMapping("/create")
	public String addProduct(@ModelAttribute(value = "product") Product product, ModelMap theModelMap) {
		theModelMap.addAttribute("product", product);
		productRepository.save(product);
		return "redirect:/product/list";
	}

	@GetMapping("/update/{id}")
	public String editIngredient(@PathVariable(value = "id") int theId, Model theModel) {
		Optional<Product> tempProduct = productRepository.findById(theId);
		if (tempProduct.isPresent()) {
			Product theProduct = tempProduct.get();
			theModel.addAttribute("product", theProduct);
		}
		theModel.addAttribute("ingredients", ingredientRepository.findAll());
		return "add-products";
	}

	@PostMapping("/create/{id}/ingredient")
	public String addIngredient(@PathVariable(value = "id") int id,
			@RequestParam(value = "ingredientId") int ingredientId, @RequestParam(value = "quantity") int quantity,
			Model theModel) {
		Product pro = productRepository.findById(id).get();
		System.out.println(pro);
		System.out.println("Product ID: " + id);
		Ingredient ingre = ingredientRepository.findById(ingredientId).get();
		System.out.println("Ingredient ID: " + ingredientId);
		System.out.println(ingre);
		System.out.println(pro.getProductIngredients());
		Product pro2 = new Product();
		if (pro != null) {
			if (!pro.hasIngredient(ingre)) {
				/*
				 * pro.getProductIngredients().add(new ProductIngredient(ingre,pro,quantity));
				 * pro2 = productRepository.findById(id).get();
				 * pro2.setProductIngredients(pro.getProductIngredients());
				 */
				productRepository.saveIngredient(ingredientId, id, quantity);
			}
			System.out.println(pro2);
			System.out.println(pro2.getProductIngredients());
		}
		return "redirect:/product/list";
	}
	@GetMapping("/delete/{id}")
	public String deleteProduct(@PathVariable(value = "id") int theId, Model theModel) {
		Optional<Product> tempProduct = productRepository.findById(theId);
		if (tempProduct.isPresent()) {
			Product theProduct = tempProduct.get();
			productRepository.delete(theProduct);
		}
		return "redirect:/product/list";
	}
}
