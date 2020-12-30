package vn.aptech.project4.controller;

import com.lowagie.text.DocumentException;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import vn.aptech.project4.entity.Customer;
import vn.aptech.project4.entity.Inventory;
import vn.aptech.project4.entity.Order;
import vn.aptech.project4.report.CustomerPDFExporter;
import vn.aptech.project4.repository.CustomerRepository;
import vn.aptech.project4.repository.InventoryRepository;
import vn.aptech.project4.repository.MembershipRepository;
import vn.aptech.project4.repository.OrderRepository;
import vn.aptech.project4.service.CustomerService1;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;



@Controller
@RequestMapping("/admin/customer")
public class CustomerController {
	private CustomerRepository customerRepository;
	private CustomerService1 service;
	private MembershipRepository membershipRepository;
	private InventoryRepository inventoryRepository;
	private OrderRepository orderRepository;
	private int lowStock;
	private  int newOrder;

	public CustomerController(CustomerRepository customerRepository, CustomerService1 service,MembershipRepository membershipRepository, InventoryRepository inventoryRepository, OrderRepository orderRepository) {
	 this.customerRepository = customerRepository;
	 this.membershipRepository = membershipRepository;
	 this.service = service;
	 this.inventoryRepository = inventoryRepository;
	 this.orderRepository = orderRepository;
	}
	public void getNewOrderNotification(Model theModel){
		newOrder = 0;
		List<Order> orders = orderRepository.findAllByStatus(1);
		for(int i = 0; i<orders.size();i++){
			newOrder++;
		}
		if(newOrder==1){
			theModel.addAttribute("newOrderMsg",newOrder+" New Order");
		}else if (newOrder>1){
			theModel.addAttribute("newOrderMsg",newOrder+" New Orders");
		}else{
			theModel.addAttribute("newOrderMsg",null);
		}
		theModel.addAttribute("newOrder",newOrder);
	}
	public void getInventoryNotification(Model theModel){
		getNewOrderNotification(theModel);
		lowStock=0;
		List<Inventory> theList = inventoryRepository.findAll();
		for(Inventory temp:theList){
			if(temp.getQuantity()<temp.getSafetyStock()){
				lowStock+=1;
			}
		}
		if(lowStock==1){
			theModel.addAttribute("lowStockMsg",lowStock+" Item Inventory Low");
		}else if (lowStock>1){
			theModel.addAttribute("lowStockMsg",lowStock+" Items Inventory Low");
		}else{
			theModel.addAttribute("lowStockMsg",null);
		}
		theModel.addAttribute("lowInventory",lowStock);
	}
	@GetMapping("/list")
	public String showCustomer(Model theModel, @Param("search") String search) {
		getInventoryNotification(theModel);
		List<Customer> customer = service.listAll(search);
		theModel.addAttribute("customer",customer);
		theModel.addAttribute("activeClients",new String("active"));
		theModel.addAttribute("content_view", new String("sales-stats-clients"));
			return"index";
		
	}
	
	@GetMapping("/createCustomer")
	public String createCustomer(Model theModel) {
		getInventoryNotification(theModel);
		Customer customer = new Customer();
		theModel.addAttribute("customer", customer);
		return"create-customer"; 
	}
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer customer) {
		Customer theCustomer = customerRepository.findById(customer.getCustomer_id()).get();
		customer.getMembership();
		Date date = new Date();
		customer.setCustomerDate(date);
		customer.setMembership(theCustomer.getMembership());
		customerRepository.save(customer);
		return"redirect:/admin/customer/list";
	}
	
	@GetMapping("/editCustomer/{id}")
	public String editCustomer(@PathVariable (value = "id") int id, Model theModel) {
		getInventoryNotification(theModel);
		Optional<Customer> customer= customerRepository.findById(id);
		theModel.addAttribute("membership", membershipRepository.findAll());
		theModel.addAttribute("customer", customer.get());
		return "edit-customer";
	
	}
	@GetMapping("/deleteCustomer/{id}")
	public String deleteCustomer(@PathVariable (value = "id") int id) {

		this.customerRepository.deleteById(id);
		return"redirect:/admin/customer/list";
	}
	
	@GetMapping("/reportCustomer")
	public String exportReport(Model theModel) throws FileNotFoundException, JRException {
		
        String path = "upload-dir/report";
		
        List<Customer> customer = customerRepository.findAll();
		 		/*
		 * Customer customer = customerRepository.findById(1).get(); List<Customer> test
		 * = new ArrayList<>(); test.add(customer);
		 */
        //load file and compile it	
        File file = ResourceUtils.getFile("classpath:customer.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(customer);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "N Coffee");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
      
       
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\customer.pdf");
        
            theModel.addAttribute("customer",customer);
            return"redirect:/admin/customer/list";
    
	}
	 @GetMapping("/export/pdf")
	    public void exportToPDF(HttpServletResponse response, @RequestParam(value="getMonth", required = false) int getMonth) throws DocumentException, IOException {
	        response.setContentType("application/pdf"); DateFormat dateFormatter = new
	                SimpleDateFormat("yyyy-MM-dd_HH:mm:ss"); String currentDateTime =
	                dateFormatter.format(new Date());

	        String headerKey = "Content-Disposition"; String headerValue =
	                "attachment; filename=customers_" + currentDateTime + ".pdf";
	        response.setHeader(headerKey, headerValue);

	        List<Customer> listCustomers = customerRepository.findAll();
	        List<Customer> listAllCustomer = new ArrayList<>();
	        for (Customer customer : listCustomers) {
				if(customer.getCustomerDate().getMonth()==(getMonth-1)) {
					listAllCustomer.add(customer);
				}
			}
	        CustomerPDFExporter exporter = new CustomerPDFExporter(listAllCustomer,getMonth);
	        exporter.export(response);

	    }

	
}
