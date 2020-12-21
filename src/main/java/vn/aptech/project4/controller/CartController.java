package vn.aptech.project4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.aptech.project4.entity.*;
import vn.aptech.project4.repository.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
	public String confrimcart(Authentication authentication, Model theModel,@RequestParam(value="addressSend")String address,@RequestParam(value="time")String time) {
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
		theModel.addAttribute("time",time);
		theModel.addAttribute("shippingaddress",address);
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
		ProductSize prosizeadd = foreachprosize(cartadd);
		
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
	private String save(Authentication authentication, RedirectAttributes redirectAttributes,@RequestParam(value="shippingaddress")String shippingAddress,@RequestParam(value="time")String time) {
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
		order.setShippingaddress(shippingAddress);
		order.setTime(time);
		// bbb= new order();
		orderRepository.save(order);
		
		//update total customer.
		cusadd.setTotal_expense((int)order.getTotal()+cusadd.getTotal_expense());
		customerRepository.save(cusadd);
		
		for (Cart cart : carts) {
			// tao ordedetail
			OrderDetail orderdetail = new OrderDetail();
			orderdetail.setOrder(order);
			// aaa.setOrder(bbb);
			Product adprodcut = foreachpro(cart);
			orderdetail.setProductId(adprodcut);
			orderdetail.setQuantity(cart.getQuantity());
			ProductSize addprosize = foreachprosize(cart);
			orderdetail.setPrice(addprosize.getPrice());
			orderdetail.setSizeId(cart.getSizeId());//sai o day
			// aaa.set()...
			orderDetailsRepository.save(orderdetail);
		}

		carts = new ArrayList<>();
		return "redirect:/ShowListProducts";
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
	public ProductSize foreachprosize(Cart cart) {
		ProductSize prosize = new ProductSize();
		for (ProductSize prosizes : productSizeRepository.findAll()) {
			if(prosizes.getProduct().getId()==cart.getProductId()&&prosizes.getSize().getId()==cart.getSizeId()) {
prosize=prosizes;
			}
		}
		return prosize;
	}
	@RequestMapping(value = "/updateSize")
	private String updateSize(HttpSession session, @RequestParam(value = "id") int id, @RequestParam(value = "size") int sizeId ) {
		Size size = sizeRepository.findById(sizeId).get();
		for (Cart cart2 : carts) {
			if (cart2.getIdCart() == id) {
				cart2.setSizeId(size.getId());
				cart2.setSizeName(size.getName());
				ProductSize prosizeadd = foreachprosize(cart2);
				cart2.setPrice(prosizeadd.getPrice());
				break;
			}
		}

		return "redirect:/cart";
	}
	
}
