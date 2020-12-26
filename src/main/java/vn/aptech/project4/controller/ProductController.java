package vn.aptech.project4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.aptech.project4.entity.*;
import vn.aptech.project4.repository.*;
import vn.aptech.project4.service.ProductService;
import vn.aptech.project4.storage.StorageService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/admin/product")
public class ProductController {
	@Value("${upload.path}")
    private String path;
    private ProductRepository productRepository;
    private ProductSizeRepository productSizeRepository;
    private StorageService storageService;
    private CategoryRepository categoryRepository;
    private ProductService serviceProduct;
    private SizeRepository sizeRepository;
    private RecipeRepository recipeRepository;
    private ProductIngredientRepository productIngredientRepository;
    private InventoryRepository inventoryRepository;
    private int lowStock=0;

    @Autowired
    public ProductController(ProductRepository productRepository, ProductSizeRepository productSizeRepository,
                             StorageService storageService, CategoryRepository categoryRepository, ProductService serviceProduct, SizeRepository sizeRepository, RecipeRepository recipeRepository, ProductIngredientRepository productIngredientRepository,InventoryRepository inventoryRepository) {
        this.productRepository = productRepository;
        this.productSizeRepository = productSizeRepository;
        this.storageService = storageService;
        this.categoryRepository = categoryRepository;
        this.serviceProduct = serviceProduct;
        this.sizeRepository = sizeRepository;
        this.recipeRepository = recipeRepository;
        this.productIngredientRepository = productIngredientRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @GetMapping("/list")
    public String ShowListProducts(Model theModel) {
        getInventoryNotification(theModel);
        List<Product> products = productRepository.findAll();
        theModel.addAttribute("size", sizeRepository.findAll());
        theModel.addAttribute("products", products);
        theModel.addAttribute("activeProducts",new String("active"));
		theModel.addAttribute("content_view", new String("sales-stats-products"));
        return "index";
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

    @GetMapping("/create")
    public String createProduct(Model theModel, @RequestParam(value = "message", required = false) String message) {
        getInventoryNotification(theModel);
        List<Category> category = categoryRepository.findAll();
        List<Size> size = sizeRepository.findAll();
        theModel.addAttribute("size", size);
        theModel.addAttribute("category", category);
        theModel.addAttribute("message", message);
        Product theProduct = new Product();
        theProduct.setImage("maucoffee.jpg");
        theModel.addAttribute("newproduct", theProduct);
        return "product-form";
    }

    @PostMapping("/save")
    public String Addproduct(@ModelAttribute("newproduct") Product theProduct, @RequestParam(value = "size") int[] size, ModelMap modelMap,
                             @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException, FileNotFoundException {

    	for (Product productItem : productRepository.findAll()) {
			if (productItem.getProductName().equals(theProduct.getProductName())) {
		         redirectAttributes.addFlashAttribute("messageTest", "true");
		         return "redirect:/admin/product/create";
			}
		}
        //-----Start Upload Image
        String filename = file.getOriginalFilename();
        if (filename.isEmpty()) {
            theProduct.setImage("maucoffee.jpg");
        } else if (storageService.checkFileExist(file)) {
            String message = "File already exist";
            redirectAttributes.addFlashAttribute("messageExist", "true");
            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/admin/product/create";
        } else {
            storageService.store(file);
            theProduct.setImage("" + filename);
        }
        //-----End Upload Image


        productRepository.save(theProduct);
        //-----Start check for Size
        checkAndSaveSize(theProduct,size);
        //-----End check for Size
        return "redirect:/admin/recipe/view/"+theProduct.getId();
    }

	private void checkAndSaveSize(Product theProduct, int[] size) {
		if (size != null) {
			Size addSize = null;
			List<ProductSize> curSize = productSizeRepository.findAllByProduct_Id(theProduct.getId());
            if(curSize.size()==0){
               for(int i=0;i<size.length;i++){
                   addSize = sizeRepository.findById(size[i]).get();
                   ProductSize addProductSize = new ProductSize(theProduct,addSize,0);
                   productSizeRepository.save(addProductSize);
               }
            }else{
                boolean sizeS = false;
                boolean sizeM = false;
                boolean sizeL = false;
                for(int i = 0;i<size.length;i++){
                    if(size[i]==1){
                        sizeS = true;
                    }else if(size[i]==2){
                        sizeM = true;
                    }else if(size[i]==3){
                        sizeL = true;
                    }
                }
                boolean curSizeS = false;
                boolean curSizeM = false;
                boolean curSizeL = false;
                for(ProductSize temp: theProduct.getSizes()){
                    if(temp.getSize().getId()==1){
                        curSizeS = true;
                    }else if(temp.getSize().getId()==2){
                        curSizeM = true;
                    }else if(temp.getSize().getId()==3){
                        curSizeL = true;
                    }
                }
                if(sizeS){
                    if(!curSizeS){
                        addSize = sizeRepository.findById(1).get();
                        ProductSize theSize = new ProductSize(theProduct,addSize,0);
                        productSizeRepository.save(theSize);
                    }
                }else{
                    if(!curSizeS){
                        ProductSize theSize = productSizeRepository.findByProduct_IdAndSize_Id(theProduct.getId(),1);
                        productSizeRepository.delete(theSize);
                    }
                }
                if(sizeM){
                    if(!curSizeM){
                        addSize = sizeRepository.findById(1).get();
                        ProductSize theSize = new ProductSize(theProduct,addSize,0);
                        productSizeRepository.save(theSize);
                    }
                }else{
                    if(!curSizeM){
                        ProductSize theSize = productSizeRepository.findByProduct_IdAndSize_Id(theProduct.getId(),1);
                        productSizeRepository.delete(theSize);
                    }
                }
                if(sizeL){
                    if(!curSizeL){
                        addSize = sizeRepository.findById(1).get();
                        ProductSize theSize = new ProductSize(theProduct,addSize,0);
                        productSizeRepository.save(theSize);
                    }
                }else{
                    if(!curSizeL){
                        ProductSize theSize = productSizeRepository.findByProduct_IdAndSize_Id(theProduct.getId(),1);
                        productSizeRepository.delete(theSize);
                    }
                }
            }
/*			List<ProductSize> addProductSizes = new ArrayList<>();
			for (int i = 0; i < size.length; i++) {
				ProductSize productSize = null;
				if (sizeRepository.findById(size[i]).isPresent()) {
					addSize = sizeRepository.findById(size[i]).get();
					if(theProduct.hasSize(addSize)){
                        addProductSizes = theProduct.getSizes();
                    }else {
                        productSize = new ProductSize(theProduct, addSize, 0);
                        addProductSizes.add(productSize);
                    }
				}
			}*/
		}
	}
	@GetMapping("/editProduct/{id}")
    public String editProduct(@PathVariable(value = "id") int id, Model theModel, HttpServletRequest request) {
        getInventoryNotification(theModel);
        Product theProduct = productRepository.findById(id).get();
        List<Category> category = categoryRepository.findAll();
        theModel.addAttribute("category", category);
        List<Size> size = sizeRepository.findAll();
        theModel.addAttribute("size", size);
        theModel.addAttribute("product", theProduct);
        return "product-update";

    }

    @PostMapping("/saveUpdate")
    public String editproduct(@ModelAttribute("product") Product product, ModelMap modelMap,
                              @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException, FileNotFoundException {

        modelMap.addAttribute("product", product);

		//-----Start Upload Image
        String filename = file.getOriginalFilename();
        if (!filename.isEmpty()) {
			if (storageService.checkFileExist(file)) {
				String message = "File already exist";
				redirectAttributes.addAttribute("message", message);
				return "redirect:/admin/product/editProduct/" + product.getId();
			} else {
				storageService.store(file);
				product.setImage("" + filename);
			}
		}
            //-----End Upload Image

            productRepository.save(product);

            return "redirect:/admin/product/list";

        }

        @GetMapping("/deleteProduct/{id}")
        public String deleteProduct ( @PathVariable(value = "id") int id){
            this.productRepository.deleteById(id);
            return "redirect:/admin/product/list";
        }
        @GetMapping("/DetailProduct/{id}")
        public String DetailProduct ( @PathVariable(value = "id") int id, Model theModel, HttpServletRequest request){
            List<Category> category = categoryRepository.findAll();
            theModel.addAttribute("category", category);
            Product theProduct = productRepository.findById(id).get();
            ProductEntity productEntity = new ProductEntity();
            productEntity.setProductId(theProduct.getId());
            productEntity.setCategoryName(theProduct.getProductName());
            productEntity.setDescription(theProduct.getDescription());
            productEntity.setCategoryId(theProduct.getCategory().getId());
            productEntity.setCategoryName(theProduct.getCategory().getName());
            productEntity.setImage(theProduct.getImage());
            productEntity.setProductName(theProduct.getProductName());
            productEntity.setSizeS(theProduct.getPrice(1));
            productEntity.setSizeM(theProduct.getPrice(2));
            productEntity.setSizeL(theProduct.getPrice(3));
            theModel.addAttribute("product", productEntity);
            
            List<Recipe> listRecipe = recipeRepository.getRecipeByProductId(theProduct.getId());
            
            theModel.addAttribute("listRecipe", listRecipe);
            return "product-detail";
        }
        @GetMapping("/updatePrice/{id}")
     public String updatePrice(@PathVariable(value = "id") int theId, Model theModel, @ModelAttribute(value = "error")String message){
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
            Map<String,Float> costBySize = new HashMap<>();
            for(ProductSize temp:theProduct.getSizes()) {
                for (Recipe recipe : viewRecipes) {
                    if (recipe.getSize().getId() == temp.getSize().getId()) {
                        cost += recipe.getQuantity() * recipe.getProductIngredient().getIngredient().getCost();
                    }
                }
                costBySize.put(temp.getSize().getName(), cost);
            }
            theModel.addAttribute("product", theProduct);
            theModel.addAttribute("errorMsg",message);
            theModel.addAttribute("costBySize",costBySize);
            return "add-product-price";
        }
        @PostMapping("/updatePrice")
        public String updatePrice(@Valid  @ModelAttribute Product product, BindingResult bindingResult, Model theModel, RedirectAttributes redirectAttributes){

            Product theProduct = productRepository.findById(product.getId()).get();
            if(bindingResult.hasErrors()){
                return "redirect:/admin/product/updatePrice/"+theProduct.getId();
            }
            List<Recipe> recipes = recipeRepository.findAll();
            List<Recipe> viewRecipes = new ArrayList<Recipe>();
            for (Recipe temp : recipes) {
                if (temp.getProductIngredient().getProduct().getId() == product.getId()) {
                    viewRecipes.add(temp);
                }
            }
            float cost = 0;
            Map<String,Float> costBySize = new HashMap<>();
            for(ProductSize temp:theProduct.getSizes()) {
                for (Recipe recipe : viewRecipes) {
                    if (recipe.getSize().getId() == temp.getSize().getId()) {
                        cost += recipe.getQuantity() * recipe.getProductIngredient().getIngredient().getCost();
                    }
                }
                costBySize.put(temp.getSize().getName(), cost);
            }

            for(ProductSize temp:theProduct.getSizes()){
                for(ProductSize temp2:product.getSizes()){
                    if(temp.getSize().getId()==temp2.getSize().getId()){
                        temp.setPrice(temp2.getPrice());
                    }
                }
                if(costBySize.get(temp.getSize().getName())>temp.getPrice()){
                    redirectAttributes.addFlashAttribute("error","Price must be greater than Cost!");
                    return "redirect:/admin/product/updatePrice/"+theProduct.getId();
                }
            }
            for(int i = 0;i<theProduct.getSizes().size()-1;i++){
                for(int j = i+1;j<theProduct.getSizes().size();j++) {
                    if (theProduct.getSizes().get(i).getPrice() > theProduct.getSizes().get(j).getPrice()) {
                        redirectAttributes.addFlashAttribute("error", "Must be Size S < Size M < Size L");
                        return "redirect:/admin/product/updatePrice/" + theProduct.getId();
                    }
                }
            }
            for(ProductSize temp: theProduct.getSizes()){
               productSizeRepository.save(temp);
           }
            return "redirect:/admin/product/updatePrice/"+theProduct.getId();
        }
    }