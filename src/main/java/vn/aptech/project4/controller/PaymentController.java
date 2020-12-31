package vn.aptech.project4.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.aptech.project4.config.PaypalPaymentIntent;
import vn.aptech.project4.config.PaypalPaymentMethod;
import vn.aptech.project4.entity.Cart;
import vn.aptech.project4.entity.Product;
import vn.aptech.project4.entity.ProductSize;
import vn.aptech.project4.entity.Utils;
import vn.aptech.project4.repository.*;
import vn.aptech.project4.service.PaypalService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;


@Controller
public class PaymentController {
	
	public static final String URL_PAYPAL_SUCCESS = "pay/success";
	public static final String URL_PAYPAL_CANCEL = "pay/cancel";
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private PaypalService paypalService;
	private OrderRepository orderRepository;
	private OrderDetailsRepository orderDetailsRepository;
	private CustomerRepository customerRepository;
	private ProductRepository productRepository;
	private ProductSizeRepository productSizeRepository;
	
	@PostMapping("/pay")
	public String pay(HttpServletRequest request,@RequestParam("price") double price ){
		String cancelUrl = Utils.getBaseURL(request) + "/" + URL_PAYPAL_CANCEL;
		String successUrl = Utils.getBaseURL(request) + "/" + URL_PAYPAL_SUCCESS;
		try {
			Payment payment = paypalService.createPayment(
					price, 
					"USD", 
					PaypalPaymentMethod.paypal, 
					PaypalPaymentIntent.sale,
					"payment description", 
					cancelUrl, 
					successUrl);
			for(Links links : payment.getLinks()){
				if(links.getRel().equals("approval_url")){
					return "redirect:" + links.getHref();
				}
			}
		} catch (PayPalRESTException e) {
			log.error(e.getMessage());
		}
		return "redirect:/";
	}

	@GetMapping(URL_PAYPAL_CANCEL)
	public String cancelPay(){
		return "cancel";
	}
	List<Cart> carts = CartController.carts;
	@GetMapping(URL_PAYPAL_SUCCESS)
	public String successPay(@RequestParam("paymentId") String paymentId, HttpServletRequest request, @RequestParam("PayerID") String payerId,Authentication authentication, RedirectAttributes redirectAttributes){
		
		try {
			Payment payment = paypalService.executePayment(paymentId, payerId);
			if(payment.getState().equals("approved")){

				request.getSession().setAttribute("PAYPAL", "PAYPAL");
				return "redirect:/save";
			}
			
		} catch (PayPalRESTException e) {
			log.error(e.getMessage());
		}
		return "redirect:/";
	}
	public Product getProduct(Cart cart) {
		Optional<Product> theProduct = productRepository.findById(cart.getProductId());
		if(theProduct.isPresent()){
			return theProduct.get();
		}
		return null;
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
}
