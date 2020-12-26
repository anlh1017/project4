package vn.aptech.project4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import vn.aptech.project4.entity.Category;
import vn.aptech.project4.entity.Inventory;
import vn.aptech.project4.entity.Product;
import vn.aptech.project4.repository.CategoryRepository;
import vn.aptech.project4.repository.InventoryRepository;
import vn.aptech.project4.repository.ProductRepository;
import vn.aptech.project4.repository.ProductSizeRepository;
import vn.aptech.project4.service.SizeService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {
	private CategoryRepository categoryRepository;
	private ProductRepository productRepository;
	private ProductSizeRepository productSizeRepository;
	private SizeService SizeRepository;
	private InventoryRepository inventoryRepository;
	private int lowStock=0;
@Autowired
 public CategoryController(ProductRepository productRepository, CategoryRepository categoryRepository,ProductSizeRepository productSizeRepository, InventoryRepository inventoryRepository
		) {
	// TODO Auto-generated constructor stub
	this.productRepository = productRepository;
	this.categoryRepository = categoryRepository;
	this.productSizeRepository = productSizeRepository;
	this.inventoryRepository = inventoryRepository;
}
	public void getInventoryNotification(Model theModel){
		List<Inventory> theList = inventoryRepository.findAll();
		for(Inventory temp:theList){
			if(temp.getQuantity()<temp.getSafetyStock()){
				lowStock+=1;
			}
		}
		theModel.addAttribute("lowInventory", lowStock);
	}
@GetMapping("/list")
public String showCategory(Model theModel) {
	getInventoryNotification(theModel);
	List<Category> categorys = categoryRepository.findAll();
	theModel.addAttribute("categorys",categorys);
	return "category-list";
}
@GetMapping("/listCategory/{id}")
public String listCategory(Model theModel,@PathVariable(value = "id") int id) {
	getInventoryNotification(theModel);
	List<Product> products = productRepository.findAll();
	List<Product> newproducts = new ArrayList<>();
	for (Product product : products) {
		if(product.getCategory().getId()==id) {
			newproducts.add(product);
		}
	}
	theModel.addAttribute("products",newproducts);
	return "product-category";
}
@GetMapping("/create")
public String create(Model theModel) {
	getInventoryNotification(theModel);
	theModel.addAttribute("category", new Category());
	return "category-form";
}
@PostMapping("/save")
public String Addproduct(@ModelAttribute("category") Category category, ModelMap modelMap) {		
	modelMap.addAttribute("category",category);
	categoryRepository.save(category);
	return "redirect:/admin/category/list";
}	
@GetMapping("deleteCategory/{id}")
public String deleteCategory(@PathVariable (value = "id") int id) {
	this.categoryRepository.deleteById(id);
	return"redirect:/admin/category/list";
}

}