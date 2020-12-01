package vn.aptech.project4.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.aptech.project4.entity.Cart;
import vn.aptech.project4.entity.Customer;
import vn.aptech.project4.entity.Order;
import vn.aptech.project4.entity.OrderDetail;
import vn.aptech.project4.entity.Product;
import vn.aptech.project4.entity.Products_size;
import vn.aptech.project4.entity.Size;
import vn.aptech.project4.repository.CategoryRepository;
import vn.aptech.project4.repository.CustomerRepository;
import vn.aptech.project4.repository.OrderDetailsRepository;
import vn.aptech.project4.repository.OrderRepository;
import vn.aptech.project4.repository.ProductRepository;
import vn.aptech.project4.repository.ProductSizeRepository;
import vn.aptech.project4.repository.SizeRepository;
@Controller
public class CartController {
	private ProductRepository productRepository;
	private CategoryRepository categoryRepository;
	private OrderRepository orderRepository;
	private OrderDetailsRepository orderDetailsRepository;
	private SizeRepository sizeRepository;
	private ProductSizeRepository productSizeRepository;
	private CustomerRepository customerRepository;
	
	@Autowired
	public CartController(ProductRepository productRepository, CategoryRepository categoryRepository,
			SizeRepository sizeRepository, ProductSizeRepository productSizeRepository, OrderRepository orderRepository,
			OrderDetailsRepository orderDetailsRepository,CustomerRepository customerRepository) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
		this.sizeRepository = sizeRepository;
		this.productSizeRepository = productSizeRepository;
		this.orderRepository = orderRepository;
		this.orderDetailsRepository = orderDetailsRepository;
		this.customerRepository=customerRepository;
	}

	List<Cart> carts = new ArrayList<>();

	@RequestMapping(value = "/cart")
	private String showCart(Model theModel, HttpSession session,Authentication authentication) {
		String cusEmail = authentication.getName();
		Customer customer = customerRepository.findByCustomerEmail(cusEmail).get();
		theModel.addAttribute("customer", customer);
		theModel.addAttribute("carts", carts);
		int tong = 0;
		for (Cart cart : carts) {
			int subtotal = cart.getPrice()*cart.getQuantity();
			tong+= subtotal;
		}
		theModel.addAttribute("size",sizeRepository.findAll());
		theModel.addAttribute("tong", tong);
		return "guest/cart";
	}
	@GetMapping(value ="/confirmcart")
	public String confrimcart(Authentication authentication, Model theModel) {
		String cusEmail = authentication.getName();
		Customer customer = customerRepository.findByCustomerEmail(cusEmail).get();
		theModel.addAttribute("customer", customer);
		theModel.addAttribute("carts", carts);
		Date date = new Date();
		int tong = 0;
		for (Cart cart : carts) {
			int subtotal = cart.getPrice()*cart.getQuantity();
			tong+= subtotal;
		}
		theModel.addAttribute("date", date);
		theModel.addAttribute("size",sizeRepository.findAll());
		theModel.addAttribute("tong", tong);
		return "guest/confirmcart";
	
	}
	@RequestMapping(value = "/user/addcartnew/{id}")
	private String addnewcart(Model theModel, @PathVariable(value = "id") int id, RedirectAttributes redirectAttributes) {
		Product product = new Product();
		for (Product productlist : productRepository.findAll()) {
			if (productlist.getId() == id) {
				product = productlist;
				break;
			}
		}
		theModel.addAttribute("addproduct", product);
		Cart addnew = new Cart();
		addnew.setProductId(product.getId());
		addnew.setProductName(product.getProductName());
		addnew.setSizeId(1);
		addnew.setQuantity(1);
		
		redirectAttributes.addFlashAttribute("cartadd",addnew);
		return "redirect:/add";
	}

	@RequestMapping(value = "/add")
private String addProductToCart(@ModelAttribute("cartadd") Cart cartadd) {
		// Kiem tra ton tai cua gio hang
		Product product = foreachpro(cartadd);
		Size sizeadd = foreachsize(cartadd);
		Products_size prosizeadd = foreachprosize(cartadd);
		
		if (carts.size() > 0) {
			int cartId = carts.size();
			// Kiem tra ton tai cua san pham trong gio hang
			int testcount = 0;
			Cart carttest = new Cart();
			for (Cart cart2 : carts) {
				if (cart2.getProductId() == cartadd.getProductId() && cart2.getSizeId() == cartadd.getSizeId()) {
					testcount = 1;
					carttest = cart2;
				}
			}
			if (testcount == 0) { // Not exist
				cartadd.setSizeName(sizeadd.getName());
				cartadd.setPrice(prosizeadd.getPrice());
				cartadd.setIdCart(cartId+1);
				carts.add(cartadd);
			} else {
				carttest.setQuantity(carttest.getQuantity() + cartadd.getQuantity());
				carttest.setPrice(carttest.getPrice());
			}

		} else {	
			Cart addnew = new Cart();			
			cartadd.setSizeName(sizeadd.getName());
			cartadd.setPrice(prosizeadd.getPrice());		
			cartadd.setIdCart(1);
			carts.add(cartadd);

		}

		return "redirect:/ShowListProducts";
	}

	@RequestMapping(value = "/save")
	private String save(Authentication authentication, RedirectAttributes redirectAttributes) {
		if(carts.size()==0) {
			redirectAttributes.addAttribute("message","Cart is empty!");
			return "redirect:/confirmcart";
		}
		String cusEmail = authentication.getName();
		Customer cusadd = customerRepository.findByCustomerEmail(cusEmail).get();
		int countTotal = 0;
		for (Cart cart : carts) {
			int subtotal = cart.getPrice()*cart.getQuantity();
			countTotal+= subtotal;
		}
		
		Date date = new Date();
		// tao order o day
		Order order = new Order();
		order.setOrderDate(date);
		order.setCustomer(cusadd);
		order.setTotal(countTotal);
		order.setStatus(1);
		// bbb= new order();
		orderRepository.save(order);
		
		for (Cart cart : carts) {
			// tao ordedetail
			OrderDetail orderdetail = new OrderDetail();
			orderdetail.setOrder(order);
			// aaa.setOrder(bbb);
			Product adprodcut = foreachpro(cart);
			orderdetail.setProductId(adprodcut);
			orderdetail.setQuantity(cart.getQuantity());
			Products_size addprosize = foreachprosize(cart);
			orderdetail.setPrice(addprosize.getPrice());
			orderdetail.setSizeId(addprosize.getSizeId());
			// aaa.set()...
			orderDetailsRepository.save(orderdetail);
		}

		carts = new ArrayList<>();
		return "redirect:/user";
	}
	public Product foreachpro(Cart cart) {
		Product adprodcut = new Product();
		for (Product pro : productRepository.findAll()) {
			if (cart.getProductId() == pro.getId() ) {
				adprodcut = pro;
			}
		}
		return adprodcut;
	}
	public Size foreachsize(Cart cart) {
		Size sizeadd = new Size();
		for (Size size : sizeRepository.findAll()) {
			if(cart.getSizeId()==size.getId()) {
				sizeadd=size;
			}
		}
		return sizeadd;
	}
	public Products_size foreachprosize(Cart cart) {
		Products_size prosize = new Products_size();
		for (Products_size prosizes : productSizeRepository.findAll()) {
			if(prosizes.getProductsId()==cart.getProductId()&&prosizes.getSizeId()==cart.getSizeId()) {
prosize=prosizes;
			}
		}
		return prosize;
	}
	@RequestMapping(value = "/remove/{id}")
	private String remove( @PathVariable(value = "id") int id) {

		for (Cart cart2 : carts) {
			if (cart2.getIdCart() == id) {
				carts.remove(cart2);
				break;
			}
		}

		return "redirect:/cart";
	}

	@RequestMapping(value = "/updateQuantity")
	private String updateQuantity(HttpSession session, @RequestParam(value = "id") int id, @RequestParam(value = "quantity") int quantity ) {

		for (Cart cart2 : carts) {
			if (cart2.getIdCart() == id) {
				cart2.setQuantity(quantity);
				break;
			}
		}

		return "redirect:/cart";
	}
	@RequestMapping(value = "/updateSize")
	private String updateSize(HttpSession session, @RequestParam(value = "id") int id, @RequestParam(value = "size") int sizeId ) {
		Size size = sizeRepository.findById(sizeId).get();
		for (Cart cart2 : carts) {
			if (cart2.getIdCart() == id) {
				cart2.setSizeId(size.getId());
				cart2.setSizeName(size.getName());
				Products_size prosizeadd = foreachprosize(cart2);
				cart2.setPrice(prosizeadd.getPrice());
				break;
			}
		}

		return "redirect:/cart";
	}
	
}