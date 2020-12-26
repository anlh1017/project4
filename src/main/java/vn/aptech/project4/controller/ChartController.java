package vn.aptech.project4.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lowagie.text.DocumentException;

import vn.aptech.project4.entity.ChartInterface;
import vn.aptech.project4.entity.ChartModel;
import vn.aptech.project4.entity.Customer;
import vn.aptech.project4.entity.Inventory;
import vn.aptech.project4.entity.Order;
import vn.aptech.project4.entity.OrderDetail;
import vn.aptech.project4.report.InventoryPDFExporter;
import vn.aptech.project4.report.ProductPDFExporter;
import vn.aptech.project4.repository.CustomerRepository;
import vn.aptech.project4.repository.OrderDetailsRepository;
import vn.aptech.project4.repository.OrderRepository;
import vn.aptech.project4.repository.ProductRepository;

@Controller
@RequestMapping("/admin/showChart")
public class ChartController {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderDetailsRepository orderDetailsRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private ProductRepository productRepository;
	
	@GetMapping("/list")
	public String showChart(Model model) {
		Map<String, Integer> surveyMap = new LinkedHashMap<>();		
		for (int i = 0; i < 12; i++) {
			int totalOrder = 0;
			for (Order orderItem : orderRepository.findAll()) {
				if(orderItem.getOrderDate().getMonth() == i) {
					totalOrder += orderItem.getTotal();
				}
			}
			surveyMap.put(i+1+"", totalOrder);
		}
		model.addAttribute("activeDashboard",new String("active"));
		model.addAttribute("content_view",new String("sales-stats-chart"));
		model.addAttribute("surveyMap", surveyMap);
		
		
		//top ten product
		Map<String, Integer> surveyMapProduct = new LinkedHashMap<>();
		for (ChartInterface chartInterface : orderDetailsRepository.getTopTenProduct()) {
			surveyMapProduct.put(""+chartInterface.getProductName(), Integer.parseInt(chartInterface.getCount()));
		}
		model.addAttribute("surveyMapProduct",surveyMapProduct);
		
		
		//send detail to top dashboard
		model.addAttribute("customers",  customerRepository.findAll().size());
		model.addAttribute("orders",  orderRepository.findAll().size());
		model.addAttribute("products",  productRepository.findAll().size());
		return "index";
	}
	@GetMapping("/export/pdf")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf"); DateFormat dateFormatter = new
                SimpleDateFormat("yyyy-MM-dd_HH:mm:ss"); String currentDateTime =
                dateFormatter.format(new Date());

        String headerKey = "Content-Disposition"; String headerValue =
                "attachment; filename=products_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        Map<String, Integer> surveyMapProduct = new LinkedHashMap<>();
        List<ChartInterface> listChart = orderDetailsRepository.getTopTenProduct();
		for (ChartInterface chartInterface : listChart) {
			surveyMapProduct.put(""+chartInterface.getProductName(), Integer.parseInt(chartInterface.getCount()));
		}
        ProductPDFExporter exporter = new ProductPDFExporter(listChart);
        exporter.export(response);

    }
}
