package vn.aptech.project4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import vn.aptech.project4.entity.*;
import vn.aptech.project4.repository.*;
import vn.aptech.project4.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

	ProductRepository productRepository;
	CategoryRepository categoryRepository;
	ProductSizeRepository productSizeRepository;
	ProductService serviceProduct;
	OrderRepository orderRepository;
	OrderDetailsRepository orderDetailsRepository;

	@Autowired
	private ReviewRepository reviewRepository;
	private CustomerRepository customerRepository;
	private MembershipRepository membershipRepository;
	@Autowired
	public HomeController(CustomerRepository customerRepository, MembershipRepository membershipRepository, ProductRepository productRepository, CategoryRepository categoryRepository,
			ProductSizeRepository productSizeRepository, ProductService serviceProduct,OrderRepository orderRepository,OrderDetailsRepository orderDetailsRepository,  ReviewRepository reviewRepository) {
		this.customerRepository = customerRepository;
		this.membershipRepository = membershipRepository;
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
		this.productSizeRepository = productSizeRepository;
		this.serviceProduct = serviceProduct;
		this.orderDetailsRepository = orderDetailsRepository;
		this.orderRepository= orderRepository;
	}
	//create a mapping for "/hello"
		@GetMapping("/")
		public String index(Model theModel) {
			List<Review> review = reviewRepository.findAll();
			theModel.addAttribute("review",review);
			theModel.addAttribute("avgRating",getAvgRating());
			return "guest/index";
		}

	@GetMapping("/ShowListProducts")
	public String ShowListProducts(Model theModel) {
		List<Product> products = productRepository.findAll();
		List<ProductEntity> productEntitys = new ArrayList<>();
		for (Product product : products) {
			ProductEntity addProduct = new ProductEntity();
			addProduct.setProductId(product.getId());

			addProduct.setDescription(product.getDescription());
			addProduct.setCategoryId(product.getCategory().getId());
			addProduct.setCategoryName(product.getCategory().getName());
			addProduct.setImage(product.getImage());
			addProduct.setProductName(product.getProductName());
			productEntitys.add(addProduct);
		}

		theModel.addAttribute("products", productEntitys);
		theModel.addAttribute("category", categoryRepository.findAll());
		return "guest/product";
	}

	@GetMapping("/showByCategory")
	public String ShowListProductsByCategory(ModelMap theModel, @RequestParam(value = "id") Integer id) {
		List<Product> products = productRepository.findAll();
		List<ProductEntity> productEntitys = new ArrayList<>();
		for (Product product : products) {
			if (id != null&&id!=0) {
				if (product.getCategory().getId() == id) {
					ProductEntity addProduct = new ProductEntity();
					addProduct.setProductId(product.getId());

					addProduct.setDescription(product.getDescription());
					addProduct.setCategoryId(product.getCategory().getId());
					addProduct.setCategoryName(product.getCategory().getName());
					addProduct.setImage(product.getImage());
					addProduct.setProductName(product.getProductName());
					productEntitys.add(addProduct);
				}
			} else {
				ProductEntity addProduct = new ProductEntity();
				addProduct.setProductId(product.getId());

				addProduct.setDescription(product.getDescription());
				addProduct.setCategoryId(product.getCategory().getId());
				addProduct.setCategoryName(product.getCategory().getName());
				addProduct.setImage(product.getImage());
				addProduct.setProductName(product.getProductName());
				productEntitys.add(addProduct);
			}
		}

		theModel.addAttribute("products", productEntitys);
		theModel.addAttribute("category", categoryRepository.findAll());
		return "guest/product::#listProduct";
	}
	/*
	 * public String listCategory(Model theModel,@PathVariable(value = "id") int id)
	 * { List<Product> products = productRepository.findAll(); List<ProductEntity>
	 * productEntitys = new ArrayList<>(); for (Product product : products) {
	 * if(product.getCategory().getId()==id) { newproducts.add(product); } }
	 * theModel.addAttribute("products",newproducts); return "product-category"; }
	 */


//	@GetMapping("/")
//	public String SHowProduct(Model theModel) {
//		return "guest/product";
//	}
	@GetMapping("/ProductDetail/{id}")
	public String ProductDetail(@PathVariable(value = "id") int id, Model theModel, HttpServletRequest request) {
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

		return "guest/productDetail";
	}

	@GetMapping("/user")
	public String Hello(Model theModel) {
		return "guest/index";
	}
	@GetMapping("/loginCustomer")
	public String showLogin(Model theModel) {
		return "guest/login-customer";
	}
	@GetMapping("/user/access-denied")
	public String showAccessDenied() {
		return "user-access-denied";
				
	}
	@GetMapping("/regiter")
	public String showRegiter( Model theModel) {
		Customer customer = new Customer();
		theModel.addAttribute("customer", customer);
		return "guest/register";
	}
	@PostMapping("/saveCustomerClient")
	public String saveCustomerClient(@ModelAttribute("customer") Customer customer,Model theModel,  @RequestParam(value="repeat-pass")String rpw) {
		String pw = customer.getCustomer_password();
		pw="{noop}"+pw;
		rpw="{noop}"+rpw;
		if(!pw.equals(rpw)) {
			theModel.addAttribute("message", "Password and Confirm Password not match !!");
			theModel.addAttribute("customer", customer);
			return "guest/register";
		}
		Membership member = membershipRepository.findById(customer.getMembership().getMembership_id()).get();
		customer.setMembership(member);
		customer.setCustomer_password(pw);
		customer.setAuthority("ROLE_CUSTOMER");
		customerRepository.save(customer);
		
		return"guest/index";
	}
	@GetMapping("/loginError")
	  public String loginError(Model model) {
	    model.addAttribute("loginError", true);
	    return "guest/login-customer";
	  }
	@PostMapping("/logoutCustomer")
	public String LogoutAdmin(Model model,HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
		    new SecurityContextLogoutHandler().logout(request, response, auth);
		}
	    model.addAttribute("loginErrorCustomer", false);
	    model.addAttribute("message", "Logout Successfully!!!");
	    return "guest/index";
	}
	@GetMapping("/{name}/detailCustomer")
	public String detailCustomer(@PathVariable (value = "name") String name, Model theModel) {
		Optional<Customer> customer= customerRepository.findByCustomerEmail(name);
		theModel.addAttribute("membership", membershipRepository.findAll());
		theModel.addAttribute("customer", customer.get());
		return "guest/detail-Customer";
	
	}
	@GetMapping("/{name}/changeCustomer")
	public String ChangeCustomer(@PathVariable (value = "name") String name, Model theModel) {
		Optional<Customer> customer= customerRepository.findByCustomerEmail(name);
		theModel.addAttribute("membership", membershipRepository.findAll());
		theModel.addAttribute("customer", customer.get());
		return "guest/change-Customer";
	}
	@PostMapping("/updateCustomer/{id}")
	public String updateCustomer(@PathVariable(value="id")int id, Model theModel, @RequestParam(value="password")String pw, @RequestParam(value="password2")String pw2, @RequestParam(value="oldpassword")String oldpw) {
		pw= "{noop}"+pw;
		pw2= "{noop}"+pw2;
		oldpw = "{noop}"+oldpw;
		Customer customer= customerRepository.findById(id).get();
		if(!customer.getCustomer_password().equals(oldpw)) {
			theModel.addAttribute("membership", membershipRepository.findAll());
			theModel.addAttribute("message", "Password is incorrect!");
			theModel.addAttribute("customer", customer);
			return "guest/change-Customer";
		}else if (!pw.equals(pw2)) {
			theModel.addAttribute("message", "Password and Confirm Password not match !!");
			theModel.addAttribute("customer", customer);
			return "guest/change-Customer";
		}else {
			customer.setCustomer_password(pw);
			customerRepository.save(customer);
			return "guest/index";
		}
	}
	
	
	@GetMapping("/{name}/historyOrder")
	public String viewHistoryOrder(@PathVariable (value = "name") String name, Model theModel) {
		Customer customer= customerRepository.findByCustomerEmail(name).get();
		List<Order> orders = orderRepository.findAll();
		List<Order> viewOrders = new ArrayList<>();
		for(Order order:orders) {
			if(order.getCustomer().getCustomer_id()==customer.getCustomer_id()) {
				viewOrders.add(order);
			}
		}
		theModel.addAttribute("orders", viewOrders);
		theModel.addAttribute("membership", membershipRepository.findAll());
		theModel.addAttribute("customer", customer);
		return "guest/historyOrder";
	
	}
	@GetMapping("/viewDetail/{id}")
	public String viewOrderDetails(@PathVariable (value = "id") int orderId, Model theModel) {
		Optional<Order> theOrder=  orderRepository.findById(orderId);
		
		if(theOrder.isPresent()) {
			List<OrderDetail> theOrderDetail = theOrder.get().getOrderDetails();
			int tong=0;
			for (OrderDetail cart : theOrderDetail) {
				tong+= cart.getPrice()*cart.getQuantity();
			}
			theModel.addAttribute("tong", tong);
			Customer theCustomer = theOrder.get().getCustomer();
			theModel.addAttribute("customer", theCustomer);
			theModel.addAttribute("orderDetails", theOrderDetail);
		}
	
		return "guest/viewHistoryOrderDetail";
	
	}
	
	@RequestMapping("/saveReview")
	public String saveReview(@ModelAttribute("review") Review review,Model theModel,Authentication authentication) {
		String cusEmail = authentication.getName();
		Customer customer = customerRepository.findByCustomerEmail(cusEmail).get();
		Date date = new Date();
		/*
		 * theModel.addAttribute("date", date); theModel.add
		 * theModel.addAttribute("review", review);
		 */
		review.setCustomer(customer);
		reviewRepository.save(review); 	
		return "guest/index";
	}
	
	public float getAvgRating() {
		List<Review> reviews = new ArrayList<>();
		int count;
		reviews = reviewRepository.findAll();
		count = reviews.size();
		if(count==0) {
			return 0;
		}
		float sum=0;
		for(Review review: reviews) {
			sum+=review.getRating();
		}
		float result = sum/count;
		float roundup= (float)Math.round(result*10)/10;
		return roundup;
	}
	@GetMapping("/reviewAll")
	public String reviewAll(Model theModel) {
		List<Review> review = reviewRepository.findAll();
		theModel.addAttribute("review",review);
		theModel.addAttribute("avgRating",getAvgRating());
		return "guest/review-all";
	}
	
}

