package vn.aptech.project4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.aptech.project4.entity.*;
import vn.aptech.project4.repository.*;

import java.util.*;

@Controller
@RequestMapping("/admin/recipe")
public class RecipeController {
    private ProductRepository productRepository;
    private IngredientRepository ingredientRepository;
    private SizeRepository sizeRepository;
    private RecipeRepository recipeRepository;
    private ProductIngredientRepository productIngredientRepository;
    private InventoryRepository inventoryRepository;
    private int lowStock=0;
    @Autowired
    public RecipeController(ProductRepository productRepository, IngredientRepository ingredientRepository,
                            SizeRepository sizeRepository, RecipeRepository recipeRepository, ProductIngredientRepository productIngredientRepository,InventoryRepository inventoryRepository) {
        this.productRepository = productRepository;
        this.ingredientRepository = ingredientRepository;
        this.sizeRepository = sizeRepository;
        this.recipeRepository = recipeRepository;
        this.productIngredientRepository = productIngredientRepository;
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
    @GetMapping("/view/{id}")
    public String viewIngredient(@PathVariable(value = "id") int theId, Model theModel, @ModelAttribute(value = "errorMsg") String message) {
        theModel.addAttribute("errorMsg",message);
        getInventoryNotification(theModel);
        Product theProduct = productRepository.findById(theId).get();
        List<Recipe> recipes = recipeRepository.findAll();
        List<Recipe> viewRecipes = new ArrayList<Recipe>();
        for (Recipe temp : recipes) {
            if (temp.getProductIngredient().getProduct().getId() == theId) {
                viewRecipes.add(temp);

            }
        }
        float cost = 0;
        Map<String, Float> costBySize = new HashMap<>();
        for (ProductSize temp : theProduct.getSizes()) {
            for (Recipe recipe : viewRecipes) {
                if (recipe.getSize().getId() == temp.getSize().getId()) {
                    cost += recipe.getQuantity() * recipe.getProductIngredient().getIngredient().getCost();
                }
            }
            costBySize.put(temp.getSize().getName(), cost);
        }
        System.out.println(viewRecipes);
        theModel.addAttribute("recipes", viewRecipes);
        theModel.addAttribute("product", theProduct);
        theModel.addAttribute("ingredients", ingredientRepository.findAll());
        theModel.addAttribute("size", sizeRepository.findAll());
        theModel.addAttribute("costBySize", costBySize);
        return "add-ingredient-product";
    }

    @GetMapping("/update/{id}")
    public String editIngredient(@PathVariable(value = "id") int theId, Model theModel) {
        getInventoryNotification(theModel);
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
/*	public String addIngredient(@PathVariable(value = "id") int id,
			@RequestParam(value = "ingredientId") int ingredientId, @RequestParam(value = "quantity") int quantity,
			@RequestParam(value = "sizeId") int sizeId, Model theModel) {*/
    public String addIngredient(@PathVariable(value = "id") int id,
                                @RequestParam(value = "ingredientId") int ingredientId, Model theModel) {
        System.out.println("Product ID: " + id);
        Product pro = productRepository.findById(id).get();

        System.out.println("Ingredient ID: " + ingredientId);
        Ingredient ingre = ingredientRepository.findById(ingredientId).get();

        List<ProductSize> sizeList = pro.getSizes();
        Recipe recipe = null;
        if (pro != null) {

            if (!pro.hasIngredient(ingre)) {
                ProductIngredient theProductIngredient = new ProductIngredient(ingre, pro);
                productIngredientRepository.save(theProductIngredient);
                List<Recipe> recipeList = new ArrayList<>();
                for (ProductSize productSize : sizeList) {
                    recipe = new Recipe(theProductIngredient, productSize.getSize(), 0);
                    recipeList.add(recipe);
                }
                for (Recipe temp : recipeList) {
                    recipeRepository.save(temp);
                }
            } else {
                theModel.addAttribute("message", "Already Exist!");
                return "redirect:/admin/recipe/view/" + id;
            }
        }
        return "redirect:/admin/recipe/view/" + id;
    }

    @PostMapping("/update/{id}")
    public String editRecipe(@PathVariable(value = "id") int theId, Model theModel,
                             @RequestParam(value = "quantity") int quantity, RedirectAttributes redirectAttributes) {
        Recipe tempRecipe = recipeRepository.findById(theId).get();
        int proId = tempRecipe.getProductIngredient().getProduct().getId();
        List<Recipe> theList = recipeRepository.findAllByProductIngredient_Id(tempRecipe.getProductIngredient().getId());
        for(Recipe temp :theList){
            int testQuantity = 0;
            if(temp.getQuantity()>=testQuantity){
                testQuantity = temp.getQuantity();
            }else{
                redirectAttributes.addAttribute("errorMsg", "Quantity must be Size S < Size M < Size L");
                return "redirect:/admin/recipe/view/" + proId;
            }
        }
        tempRecipe.setQuantity(quantity);

        recipeRepository.save(tempRecipe);
        return "redirect:/admin/recipe/view/" + proId;
    }

    @Transactional
    @GetMapping("/delete/{id}")
    public String deleteRecipe(@PathVariable(value = "id") int theId, Model theModel) {
        Optional<Recipe> tempRecipe = recipeRepository.findById(theId);

        Recipe deleteRecipe = null;
        if (tempRecipe.isPresent()) {
            deleteRecipe = tempRecipe.get();
        }
        int pro_ingre_id = deleteRecipe.getProductIngredient().getId();

        List<Recipe> theList = recipeRepository.findAllByProductIngredient_Id(pro_ingre_id);
        System.out.println(theList);
        for (Recipe temp : theList) {
            recipeRepository.deleteRecipe(temp.getId());
        }
        return "redirect:/admin/product/list";
    }

}
