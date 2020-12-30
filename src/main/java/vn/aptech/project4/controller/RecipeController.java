package vn.aptech.project4.controller;

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
    private OrderRepository orderRepository;
    private int lowStock;
    private int newOrder;

    public RecipeController(ProductRepository productRepository, IngredientRepository ingredientRepository,
                            SizeRepository sizeRepository, RecipeRepository recipeRepository, ProductIngredientRepository productIngredientRepository, InventoryRepository inventoryRepository,OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.ingredientRepository = ingredientRepository;
        this.sizeRepository = sizeRepository;
        this.recipeRepository = recipeRepository;
        this.productIngredientRepository = productIngredientRepository;
        this.inventoryRepository = inventoryRepository;
        this.orderRepository = orderRepository;
    }

    public void getInventoryNotification(Model theModel) {
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

    @GetMapping("/view/{id}")
    public String viewIngredient(@PathVariable(value = "id") int theId, Model theModel, @ModelAttribute(value = "errorMsg") String message) {
        theModel.addAttribute("errorMsg", message);
        getInventoryNotification(theModel);
        Product theProduct = productRepository.findById(theId).get();
        List<Recipe> recipes = recipeRepository.findAll();
        List<Recipe> viewRecipes = new ArrayList<Recipe>();
        for (Recipe temp : recipes) {
            if (temp.getProductIngredient().getProduct().getId() == theId) {
                viewRecipes.add(temp);
            }
        }

        Map<String, Float> costBySize = new HashMap<>();
        for (ProductSize temp : theProduct.getSizes()) {
            float cost = 0;
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
        theModel.addAttribute("ingredients", ingredientRepository.findAllByInventory_Status(2));
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
    public String addIngredient(@PathVariable(value = "id") int id,
                                @RequestParam(value = "ingredientId") int ingredientId, Model theModel) {
        System.out.println("Product ID: " + id);
        Product pro = productRepository.findById(id).get();

        System.out.println("Ingredient ID: " + ingredientId);
        Ingredient ingre = ingredientRepository.findById(ingredientId).get();

        List<ProductSize> sizeList = pro.getSizes();
        Recipe recipe = null;
        if (pro != null) {
            Optional<ProductIngredient> testProIngre = productIngredientRepository.findByProduct_IdAndIngredient_Id(pro.getId(), ingre.getId());
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
            } else if (testProIngre.isPresent()) {
                List<Recipe> recipeList = testProIngre.get().getRecipes();
                if (recipeList.isEmpty()) {
                    for (ProductSize productSize : sizeList) {
                        recipe = new Recipe(testProIngre.get(), productSize.getSize(), 0);
                        recipeList.add(recipe);
                    }
                    for(int i=0;i<recipeList.size();i++){
                        recipeRepository.save(recipeList.get(i));
                    }
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
        tempRecipe.setQuantity(quantity);
        List<Recipe> theList1 = new ArrayList<>();
        for(int i = 1;i<4;i++){
            for(Recipe temp:theList){
                if(temp.getSize().getId()==i){
                    theList1.add(temp);
                }
            }
        }
        int testQuantity = 0;
        for (Recipe temp : theList1) {
            if(temp.getId()==tempRecipe.getId()){
                temp.setQuantity(tempRecipe.getQuantity());
            }
            if (temp.getQuantity()==0||temp.getQuantity() >= testQuantity) {
                testQuantity = temp.getQuantity();
            } else {
                redirectAttributes.addAttribute("errorMsg", "Quantity must be Size S < Size M < Size L");
                return "redirect:/admin/recipe/view/" + proId;
            }
        }
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
        int proId = productIngredientRepository.findById(pro_ingre_id).get().getProduct().getId();
        return "redirect:/admin/recipe/view/" + proId;
    }

}
