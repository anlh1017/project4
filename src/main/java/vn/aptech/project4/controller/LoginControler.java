package vn.aptech.project4.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginControler {
	@GetMapping("/showMyLoginPage")
	public String showMyLoginPage() {
		return "utility-login";
	}
	//add request mapping for /access-denied
	@GetMapping("/access-denied")
	public String showAccessDenied() {
		return "utility-access-denied";	
	}
	@GetMapping("/loginErrorAdmin")
	  public String loginError(Model model) {
	    model.addAttribute("loginErrorAdmin", true);
	    return "utility-login";
	  }
	@PostMapping("/logoutAdmin")
	public String LogoutAdmin(Model model,HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
		    new SecurityContextLogoutHandler().logout(request, response, auth);
		}
	    model.addAttribute("loginErrorAdmin", false);
	    model.addAttribute("message", "Logout Successfully!!!");
	    return "redirect:/showMyLoginPage";
	}
}
