package vn.aptech.project4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.aptech.project4.entity.*;
import vn.aptech.project4.repository.CategoryRepository;
import vn.aptech.project4.repository.ProductRepository;
import vn.aptech.project4.repository.ProductSizeRepository;
import vn.aptech.project4.repository.SizeRepository;
import vn.aptech.project4.service.ProductService;
import vn.aptech.project4.storage.StorageService;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
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
	private String context = "D:\\Project4\\Group1_CoffeeShop\\Group1_CoffeeShop\\src\\MyApp\\src\\main\\resources\\static\\img\\pages\\products";

	@Autowired
	public ProductController(ProductRepository productRepository, ProductSizeRepository productSizeRepository,
			StorageService storageService, CategoryRepository categoryRepository, ProductService serviceProduct,SizeRepository sizeRepository) {
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
		theModel.addAttribute("products", products);
		return "products-list";
	}



	@GetMapping("/create")
	public String createProduct(Model theModel) {
		List<Category> category = categoryRepository.findAll();
		List<Size> size = sizeRepository.findAll();
		theModel.addAttribute("size", size);
		theModel.addAttribute("category", category);
		Product theProduct = new Product();
		theProduct.setImage("maucoffee.jpg");
		theModel.addAttribute("newproduct", theProduct);
		return "product-form";
	}

	@PostMapping("/save")
	public String Addproduct(@ModelAttribute("newproduct") Product theProduct,@RequestParam(value = "size")int[] size, ModelMap modelMap,
			@RequestParam("file") MultipartFile file) throws IOException, FileNotFoundException {

		// luu y : link image la link tuyet doi o tren.
		// ---------------------------------------------------------------------------------------
		// phai mo stick khi doi qua may khac window >Preferences > General > Workspaces
		// > "Refresh using native hooks or polling"--------------------------------
		// khi upanh nhanh tay up refresh trang
		// ---------------------------------------------------------------------------------------------
		// --image

		String filename = file.getOriginalFilename();
		if (filename.isEmpty()) {
			theProduct.setImage("maucoffee.jpg");
		} else {
			storageService.store(file);
			theProduct.setImage("" + filename);
		}
		// -------------end image
		if(size!=null){
			Size addSize = null;
			for(int i = 0; i<size.length;i++){
				ProductSize productSize = null;
				if(sizeRepository.findById(size[i]).isPresent()){
					addSize = sizeRepository.findById(size[i]).get();
					productSize = new ProductSize(theProduct,addSize,0);
					theProduct.addProductSize(productSize);
				}
			}
		}

		productRepository.save(theProduct);
		return "redirect:/admin/product/list";
	}

	@GetMapping("/editProduct/{id}")
	public String editProduct(@PathVariable(value = "id") int id, Model theModel, HttpServletRequest request) {
		List<Category> category = categoryRepository.findAll();
		theModel.addAttribute("category", category);
		// request.getSession().setAttribute("categorys", category);
		// Optional<Product> product = productRepository.findById(id);
		Product theProduct = productRepository.findById(id).get();
		ProductEntity productEntity = new ProductEntity();
		productEntity.setProductId(theProduct.getId());
		productEntity.setCategoryName(theProduct.getProductName());
		productEntity.setDescription(theProduct.getDescription());
		productEntity.setCategoryId(theProduct.getCategory().getId());
		productEntity.setCategoryName(theProduct.getCategory().getName());
		productEntity.setImage(theProduct.getImage());
		productEntity.setProductName(theProduct.getProductName());

		/*
		 * List<Product> listProducts = productRepository.findAll(); for (Product
		 * product2 : listProducts) { if (product2.getId() == id) {
		 * theModel.addAttribute("imageProduct", product2.getImage());
		 * 
		 * theModel.addAttribute("product", new ProductEntity(product2.getId(),
		 * product2.getProductName(), product2.getDescription(),
		 * product2.getCategory().getName(), product2.getCategory().getId(),
		 * product2.getImage(), 1, 1, 1)); break; } }
		 */
		theModel.addAttribute("product",productEntity);
		return "product-update";

	}

	@PostMapping("/saveUpdate")
	public String editproduct(@ModelAttribute("product") ProductEntity product, ModelMap modelMap,
			@RequestParam("file") MultipartFile file) throws IOException, FileNotFoundException {

		modelMap.addAttribute("product", product);
		String filename = file.getOriginalFilename();
		Category addcategory = new Category();
		for (Category item : categoryRepository.findAll()) {
			if (item.getId() == product.getCategoryId()) {
				addcategory = item;
			}
		}
		Product editproduct = new Product(product.getProductId(), product.getProductName(), product.getDescription(),
				addcategory, product.getImage());

		if (!filename.isEmpty()) {
			byte[] bytes = file.getBytes();
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(new File(context + File.separator + filename)));
			stream.write(bytes);
			stream.flush();
			stream.close();
			editproduct.setImage(file.getOriginalFilename());
		}
		for (Products_size products_size : productSizeRepository.findAll()) {
			if(products_size.getProductsId()==product.getProductId()&&products_size.getSizeId()==1) {
				products_size.setPrice(product.getSizeS());
				productSizeRepository.save(products_size);
			}
			if(products_size.getProductsId()==product.getProductId()&&products_size.getSizeId()==2) {
				products_size.setPrice(product.getSizeM());
				productSizeRepository.save(products_size);
			}
			if(products_size.getProductsId()==product.getProductId()&&products_size.getSizeId()==3) {
				products_size.setPrice(product.getSizeL());
				productSizeRepository.save(products_size);
			}
		}
		serviceProduct.update(editproduct);
		return "redirect:/admin/product/list";

	}

	@GetMapping("deleteProduct/{id}")
	public String deleteProduct(@PathVariable(value = "id") int id) {
		this.productRepository.deleteById(id);
		return "redirect:/admin/product/list";
	}
	@GetMapping("/DetailProduct/{id}")
	public String DetailProduct(@PathVariable(value = "id") int id, Model theModel, HttpServletRequest request) {
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

		theModel.addAttribute("product",productEntity);
		return "product-detail";
}
}
