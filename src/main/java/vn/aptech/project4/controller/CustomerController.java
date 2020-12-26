package vn.aptech.project4.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.lowagie.text.DocumentException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import vn.aptech.project4.entity.Customer;
import vn.aptech.project4.report.CustomerPDFExporter;
import vn.aptech.project4.repository.CustomerRepository;
import vn.aptech.project4.repository.MembershipRepository;
import vn.aptech.project4.service.CustomerService1;



@Controller
@RequestMapping("/admin/customer")
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
