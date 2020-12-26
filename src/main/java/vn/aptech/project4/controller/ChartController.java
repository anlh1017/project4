package vn.aptech.project4.controller;

import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.aptech.project4.entity.ChartInterface;
import vn.aptech.project4.entity.Inventory;
import vn.aptech.project4.entity.Order;
import vn.aptech.project4.report.ProductPDFExporter;
import vn.aptech.project4.repository.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/showChart")
public class ChartController {
	private OrderRepository orderRepository;
	private OrderDetailsRepository orderDetailsRepository;
	private CustomerRepository customerRepository;
	private ProductRepository productRepository;
	private InventoryRepository inventoryRepository;
	private int lowStock=0;
	@Autowired
	public ChartController( OrderRepository orderRepository,OrderDetailsRepository orderDetailsRepository,CustomerRepository customerRepository,ProductRepository productRepository,InventoryRepository inventoryRepository) {
		this.orderRepository = orderRepository;
		this.orderDetailsRepository = orderDetailsRepository;
		this.customerRepository = customerRepository;
		this.productRepository = productRepository;
		this.inventoryRepository = inventoryRepository;
	}
	public void getInventoryNotification(Model theModel){
		List<Inventory> theList = inventoryRepository.findAll();
		for(Inventory temp:theList){
			if(temp.getQuantity()<temp.getSafetyStock()){
				lowStock+=1;
			}
		}
		theModel.addAttribute("lowInventory", lowStock);
	}
	@GetMapping("/list")
	public String showChart(Model model) {
		getInventoryNotification(model);
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
