package vn.aptech.project4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @Autowired
    public RecipeController(ProductRepository productRepository, IngredientRepository ingredientRepository,
                            SizeRepository sizeRepository, RecipeRepository recipeRepository, ProductIngredientRepository productIngredientRepository) {
        this.productRepository = productRepository;
        this.ingredientRepository = ingredientRepository;
        this.sizeRepository = sizeRepository;
        this.recipeRepository = recipeRepository;
        this.productIngredientRepository = productIngredientRepository;
    }

    @GetMapping("/view/{id}")
    public String viewIngredient(@PathVariable(value = "id") int theId, Model theModel) {
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
                /*
                 * pro.getProductIngredients().add(new ProductIngredient(ingre,pro,quantity));
                 * pro2 = productRepository.findById(id).get();
                 * pro2.setProductIngredients(pro.getProductIngredients());
                 */
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

    @Transactional
    @GetMapping("/delete/{id}")
    public String deleteRecipe(@PathVariable(value = "id") int theId, Model theModel) {
        System.out.println("Finding Recipe by Id: " + theId);
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
        // productIngredientRepository.deleteProductIngredientsByProduct_IdAndAndIngredient_Id(deleteRecipe.getProductIngredient().getProduct().getId(), deleteRecipe.getProductIngredient().getIngredient().getId());


        /*
         * if (proIngreId == temp.getId()) { if (temp.getRecipes().size() > 1) {
         * System.out.println("not delete"); } else { System.out.println("delete");
         * listProIngre.remove(temp); } }
         */

        return "redirect:/admin/product/list";
    }

}
