package vn.aptech.project4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.aptech.project4.entity.*;
import vn.aptech.project4.repository.CategoryRepository;
import vn.aptech.project4.repository.ProductRepository;
import vn.aptech.project4.repository.ProductSizeRepository;
import vn.aptech.project4.repository.SizeRepository;
import vn.aptech.project4.service.ProductService;
import vn.aptech.project4.storage.StorageService;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    public ProductController(ProductRepository productRepository, ProductSizeRepository productSizeRepository,
                             StorageService storageService, CategoryRepository categoryRepository, ProductService serviceProduct, SizeRepository sizeRepository) {
        this.productRepository = productRepository;
        this.productSizeRepository = productSizeRepository;
        this.storageService = storageService;
        this.categoryRepository = categoryRepository;
        this.serviceProduct = serviceProduct;
        this.sizeRepository = sizeRepository;
    }

    @GetMapping("/list")
    public String ShowListProducts(Model theModel) {
        List<Product> products = productRepository.findAll();
        theModel.addAttribute("size", sizeRepository.findAll());
        theModel.addAttribute("products", products);
        theModel.addAttribute("activeProducts",new String("active"));
		theModel.addAttribute("content_view", new String("sales-stats-products"));
        return "index";
    }


    @GetMapping("/create")
    public String createProduct(Model theModel, @RequestParam(value = "message", required = false) String message) {
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

        //-----Start Upload Image
        String filename = file.getOriginalFilename();
        if (filename.isEmpty()) {
            theProduct.setImage("maucoffee.jpg");
        } else if (storageService.checkFileExist(file)) {
            String message = "File already exist";
            redirectAttributes.addAttribute("message", message);
            return "redirect:/admin/product/create";
        } else {
            storageService.store(file);
            theProduct.setImage("" + filename);
        }
        //-----End Upload Image
        checkAndSaveSize(theProduct,size);
        productRepository.save(theProduct);
        //-----Start check for Size

        //-----End check for Size
        return "redirect:/admin/product/list";
    }

	private void checkAndSaveSize(Product theProduct, int[] size) {
		if (size != null) {
			Size addSize = null;
			List<ProductSize> addProductSizes = new ArrayList<>();
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
			}
			theProduct.setSizes(addProductSizes);
		}
	}

	@GetMapping("/editProduct/{id}")
    public String editProduct(@PathVariable(value = "id") int id, Model theModel, HttpServletRequest request) {
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
                              @RequestParam("file") MultipartFile file, @RequestParam(value = "size") int[] size, RedirectAttributes redirectAttributes) throws IOException, FileNotFoundException {

        modelMap.addAttribute("product", product);
		checkAndSaveSize(product, size);

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

        @GetMapping("deleteProduct/{id}")
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

            theModel.addAttribute("product", productEntity);
            return "product-detail";
        }
    }
