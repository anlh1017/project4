package vn.aptech.project4.oauth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import vn.aptech.project4.entity.AuthenticationProvider;
import vn.aptech.project4.entity.Customer;
import vn.aptech.project4.service.CustomerService1;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Autowired
	private CustomerService1 customerService;
	
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
		String name = oAuth2User.getEmail();
		String email = oAuth2User.getName();
		Customer customer = customerService.getCustomerByEmail(email);
		
		if(customer == null) {
			//register new customer
			customerService.createNewCustomerAfterOAuthLoginSuccess(email, name, AuthenticationProvider.GOOGLE);
		}else{
			//update customer
			customerService.updateCustomerAfterOAuthLoginSuccess(customer, name, AuthenticationProvider.GOOGLE);
		}
		
		System.out.println("Customer email :" + email);
		
		super.onAuthenticationSuccess(request, response, authentication);
	}

}
