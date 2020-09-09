package vn.aptech.project4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {
	//create a mapping for "/hello"
	@GetMapping("/")
	public String sayHello(Model theModel) {
		theModel.addAttribute("activeOverview",new String("active"));
		return "index";
	}
	@GetMapping("/sales")
	public String showSaleStats(Model theModel) {
		theModel.addAttribute("activeSales",new String("active"));
		return "sales-stats-products";
	}
	@GetMapping("/orders")
	public String showOrders(Model theModel) {
		theModel.addAttribute("activeOrders",new String("active"));
		return "sales-stats-purchases";
	}
	@GetMapping("/clients")
	public String showClients(Model theModel) {
		theModel.addAttribute("activeClients",new String("active"));
		return "sales-stats-clients";
	}
	@GetMapping("/setting")
	public String showSetting(Model theModel) {
		theModel.addAttribute("activeSetting",new String("active"));
		return "sales-stats-general-settings";
	}
}
