package vn.aptech.project4.controller;


import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.aptech.project4.entity.*;
import vn.aptech.project4.report.OrderPDFExporter;
import vn.aptech.project4.repository.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/order")
public class OrderController {
	private OrderRepository orderRepository;
	private MembershipRepository membershipRepository;
	private CustomerRepository customerRepository;
	private InventoryRepository inventoryRepository;
	private int lowStock;
	private int newOrder;
	@Autowired
	public OrderController(OrderRepository orderRepository, OrderDetailsRepository orderdetailsRepository,MembershipRepository membershipRepository, CustomerRepository customerRepository,InventoryRepository inventoryRepository) {
		this.orderRepository = orderRepository;
		this.membershipRepository = membershipRepository;
		this.customerRepository = customerRepository;
		this.inventoryRepository = inventoryRepository;
	}
	public void getNotification(Model theModel){
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
	public String showOrders(Model theModel, @RequestParam(value="id", required = false) String id,@RequestParam(value="status", required = false) String status) {
		getNotification(theModel);
		theModel.addAttribute("activeOrders",new String("active"));
		theModel.addAttribute("content_view",new String("sales-stats-purchases"));
		String theId=(id==null)?"NA":id;
		if(theId=="NA"||theId.isEmpty()) {
			List<Order> orders = orderRepository.findAllByOrderByStatusAsc();
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
		getNotification(theModel);
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
			if(status==3){
				Customer theCustomer = savedOrder.getCustomer();
				if(theCustomer.getMembership().getMembership_id()!=13){
					if(theCustomer.getTotal_expense()>=4000000){
						Membership theMembership = membershipRepository.getOne(4);
						theCustomer.setMembership(theMembership);
						customerRepository.save(theCustomer);
					}else 	if(theCustomer.getTotal_expense()>=3000000){
						Membership theMembership = membershipRepository.getOne(3);
						theCustomer.setMembership(theMembership);
						customerRepository.save(theCustomer);
					}else 	if(theCustomer.getTotal_expense()>=2000000){
						Membership theMembership = membershipRepository.getOne(2);
						theCustomer.setMembership(theMembership);
						customerRepository.save(theCustomer);
					}
				}
			}
			savedOrder.setStatus(status);
			orderRepository.save(savedOrder);
			theModel.addAttribute("orderDetails", savedOrder);
			theModel.addAttribute("activeOrders",new String("active"));
			theModel.addAttribute("content_view",new String("sales-stats-purchases"));
		}	
		return "redirect:/admin/order/list";
	
	}


    @GetMapping("/export/pdf")
    public void exportToPDF(HttpServletResponse response, @RequestParam(value="getMonth", required = false) int getMonth) throws DocumentException, IOException {
        response.setContentType("application/pdf"); DateFormat dateFormatter = new
                SimpleDateFormat("yyyy-MM-dd_HH:mm:ss"); String currentDateTime =
                dateFormatter.format(new Date());

        String headerKey = "Content-Disposition"; String headerValue =
                "attachment; filename=orders_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Order> listOrders = orderRepository.findAll();
        List<Order> listAllOder = new ArrayList<>();
        for (Order order : listOrders) {
			if(order.getOrderDate().getMonth()==(getMonth-1)) {
				listAllOder.add(order);
			}
		}
        OrderPDFExporter exporter = new OrderPDFExporter(listAllOder,getMonth);
        exporter.export(response);
    }
}
