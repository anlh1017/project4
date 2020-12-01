package vn.aptech.project4.controller;


import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.aptech.project4.entity.Customer;
import vn.aptech.project4.entity.Order;
import vn.aptech.project4.entity.OrderDetail;
import vn.aptech.project4.entity.OrderPDFExporter;
import vn.aptech.project4.repository.OrderDetailsRepository;
import vn.aptech.project4.repository.OrderRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/order")
public class OrderController {
	@Autowired
	private OrderRepository orderRepository;
	private OrderDetailsRepository orderdetailsRepository;	

	public OrderController(OrderRepository orderRepository, OrderDetailsRepository orderdetailsRepository) {
		this.orderRepository = orderRepository;
		this.orderdetailsRepository = orderdetailsRepository;
	}
	@GetMapping("/list")
	public String showOrders(Model theModel, @RequestParam(value="id", required = false) String id,@RequestParam(value="status", required = false) String status) {
		
		theModel.addAttribute("activeOrders",new String("active"));
		theModel.addAttribute("content_view",new String("sales-stats-purchases"));
		String theId=(id==null)?"NA":id;
		if(theId=="NA"||theId.isEmpty()) {
			List<Order> orders = orderRepository.findAll();
			theModel.addAttribute("orders",orders);
		}else {
			int searchId = Integer.parseInt(id);
			Optional<Order> theOrder=  orderRepository.findById(searchId);
			theModel.addAttribute("orders", theOrder.get());			
		}		
		return "index";
	}
	@GetMapping("/viewDetail/{id}")
	public String viewOrderDetails(@PathVariable (value = "id") int orderId, Model theModel) {
		Optional<Order> theOrder=  orderRepository.findById(orderId);
		
		if(theOrder.isPresent()) {
			List<OrderDetail> theOrderDetail = theOrder.get().getOrderDetails();
			int tong=0;
			for (OrderDetail cart : theOrderDetail) {
				tong+= cart.getPrice()*cart.getQuantity();
			}
			theModel.addAttribute("tong", tong);
			Customer theCustomer = theOrder.get().getCustomer();
			theModel.addAttribute("customer", theCustomer);
			theModel.addAttribute("orderDetails", theOrderDetail);
			theModel.addAttribute("theOrder", theOrder.get());
		}
	
		return "view-order-detail";
	
	}
	@GetMapping("/updateStatus")
	public String updateStatus(@RequestParam (value = "id") int orderId,@RequestParam(value="status")int status, Model theModel) {
		Optional<Order> theOrder=  orderRepository.findById(orderId);
		if(theOrder.isPresent()) {
			Order savedOrder = theOrder.get();
			savedOrder.setStatus(status);
			orderRepository.save(savedOrder);
			theModel.addAttribute("orderDetails", savedOrder);
			theModel.addAttribute("activeOrders",new String("active"));
			theModel.addAttribute("content_view",new String("sales-stats-purchases"));
		}	
		return "redirect:/admin/order/list";
	
	}
	@GetMapping("/searchById")
	public String searchById(@RequestParam(value = "id", required = false) int  orderId, Model theModel) {
		Optional<Order> theOrder=  orderRepository.findById(orderId);
		if(theOrder.isPresent()) {
			theModel.addAttribute("orders", theOrder.get());
			theModel.addAttribute("activeOrders",new String("active"));
			theModel.addAttribute("content_view",new String("sales-stats-purchases"));
		}
		return "index";
	
	}
	@GetMapping("/invoice/{id}")
	public String invoice(@PathVariable (value = "id") int orderId, Model theModel) {
		Optional<Order> theOrder=  orderRepository.findById(orderId);
		if(theOrder.isPresent()) {
			List<OrderDetail> theOrderDetail = theOrder.get().getOrderDetails();
			Customer theCustomer = theOrder.get().getCustomer();
			
			theModel.addAttribute("customer", theCustomer);
			theModel.addAttribute("orderDetails", theOrderDetail);
		}
		return "invoice";
	
	}
    @GetMapping("/export/pdf")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf"); DateFormat dateFormatter = new
                SimpleDateFormat("yyyy-MM-dd_HH:mm:ss"); String currentDateTime =
                dateFormatter.format(new Date());

        String headerKey = "Content-Disposition"; String headerValue =
                "attachment; filename=orders_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Order> listOrders = orderRepository.findAll();

        OrderPDFExporter exporter = new OrderPDFExporter(listOrders);
        exporter.export(response);

    }


//	@GetMapping("/findByDate")
//	  public String findByDateBetween(@RequestParam("startdate") Date startdate,@RequestParam("enddate") Date enddate,Model theModel) {
//		Optional<Order> theOrder= orderRepository.findAllByDateBetween(startdate, enddate);
//			theModel.addAttribute("orders", theOrder );
//			theModel.addAttribute("activeOrders",new String("active"));
//			theModel.addAttribute("content_view",new String("sales-stats-purchases"));
//		return "index";	  
//		}
//	@GetMapping("/findByStatus")
//	public String filterByStatus(@RequestParam(value="status", required = false)int status, Model theModel) {
//		Optional<Order> theOrder=  orderRepository.findByStatus(status);
//		if(theOrder.isPresent()) {
//			theModel.addAttribute("orders", theOrder.get());
//			theModel.addAttribute("activeOrders",new String("active"));
//			theModel.addAttribute("content_view",new String("sales-stats-purchases"));
//		}
//		return "index";
//	
//	}

	
	
}
