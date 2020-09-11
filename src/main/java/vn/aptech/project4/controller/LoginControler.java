package vn.aptech.project4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
