package vn.aptech.project4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {
	//create a mapping for "/hello"
	@GetMapping("/")
	public String index(Model theModel) {
		theModel.addAttribute("activeOverview",new String("active"));
		theModel.addAttribute("content_view", new String("content_overview"));
		return "index";
	}
	@GetMapping("/sales")
	public String showSaleStats(Model theModel) {
		theModel.addAttribute("activeSales",new String("active"));
		theModel.addAttribute("content_view", new String("sales-stats-products"));
		return "index";
	}
	@GetMapping("/orders")
	public String showOrders(Model theModel) {
		theModel.addAttribute("activeOrders",new String("active"));
		theModel.addAttribute("content_view", new String("sales-stats-purchases"));
		return "index";
	}
	@GetMapping("/clients")
	public String showClients(Model theModel) {
		theModel.addAttribute("activeClients",new String("active"));
		theModel.addAttribute("content_view", new String("sales-stats-clients"));
		return "index";
	}
	@GetMapping("/setting")
	public String showSetting(Model theModel) {
		theModel.addAttribute("activeSetting",new String("active"));
		theModel.addAttribute("content_view", new String("sales-stats-general-settings"));
		return "index";
	}
}
