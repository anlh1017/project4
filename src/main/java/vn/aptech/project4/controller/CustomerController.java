package vn.aptech.project4.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.aptech.project4.entity.Customer;
import vn.aptech.project4.repository.CustomerRepository;
import vn.aptech.project4.repository.MembershipRepository;
import vn.aptech.project4.service.CustomerService1;



@Controller
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private CustomerService1 service;
	@Autowired
	private MembershipRepository membershipRepository;
	
	@GetMapping("/list")
	public String showCustomer(Model theModel, @Param("search") String search) {
		List<Customer> customer = service.listAll(search);
		theModel.addAttribute("customer",customer);
		theModel.addAttribute("activeClients",new String("active"));
		theModel.addAttribute("content_view", new String("sales-stats-clients"));
			return"index";
		
	}
	
	@GetMapping("/createCustomer")
	public String createCustomer(Model theModel) {
		Customer customer = new Customer();
		theModel.addAttribute("customer", customer);
		return"create-customer"; 
	}
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer customer) {
		customer.getMembership();
		customerRepository.save(customer);
		return"redirect:/customer/list";
	}
	
	@GetMapping("/editCustomer/{id}")
	public String editCustomer(@PathVariable (value = "id") int id, Model theModel) {
		Optional<Customer> customer= customerRepository.findById(id);
		theModel.addAttribute("membership", membershipRepository.findAll());
		theModel.addAttribute("customer", customer.get());
		return "edit-customer";
	
	}
	@GetMapping("/deleteCustomer/{id}")
	public String deleteCustomer(@PathVariable (value = "id") int id) {
		this.customerRepository.deleteById(id);
		return"redirect:/customer/list";
	}
	

}
