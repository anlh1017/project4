	package vn.aptech.project4.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class GuestController {
	
    @GetMapping("/") public String index(Model theModel) { return
	  "redirect:/user"; }
	 
	@GetMapping("/user")
	public String Hello(Model theModel) {
		return "hello";
	}
	@GetMapping("/user/access-denied")
	public String showAccessDenied() {
		return "user-access-denied";
				
	}
	
	
	

    
 }
	


