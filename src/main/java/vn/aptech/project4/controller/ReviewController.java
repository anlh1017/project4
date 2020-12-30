package vn.aptech.project4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.aptech.project4.entity.Inventory;
import vn.aptech.project4.entity.Order;
import vn.aptech.project4.entity.Review;
import vn.aptech.project4.repository.InventoryRepository;
import vn.aptech.project4.repository.OrderRepository;
import vn.aptech.project4.repository.ReviewRepository;

import java.util.List;

@Controller
@RequestMapping("/admin/review")
public class ReviewController {

  private ReviewRepository reviewRepository;
	private InventoryRepository inventoryRepository;
	private OrderRepository orderRepository;
	private int lowStock;
	private int newOrder;
	@Autowired
	public ReviewController(ReviewRepository reviewRepository,InventoryRepository inventoryRepository,OrderRepository orderRepository) {
		this.inventoryRepository = inventoryRepository;
		this.reviewRepository = reviewRepository;
		this.orderRepository = orderRepository;
	}
	public void getInventoryNotification(Model theModel){
		newOrder = 0;
		List<Order> orders = orderRepository.findAllByStatus(1);
		for (int i = 0; i < orders.size(); i++) {
			newOrder++;
		}
		if (newOrder == 1) {
			theModel.addAttribute("newOrderMsg", newOrder + " New Order");
		} else if (newOrder > 1) {
			theModel.addAttribute("newOrderMsg", newOrder + " New Orders");
		} else {
			theModel.addAttribute("newOrderMsg", null);
		}
		theModel.addAttribute("newOrder", newOrder);
		lowStock = 0;
		List<Inventory> theList = inventoryRepository.findAll();
		for (Inventory temp : theList) {
			if (temp.getQuantity() < temp.getSafetyStock()) {
				lowStock += 1;
			}
		}
		if (lowStock == 1) {
			theModel.addAttribute("lowStockMsg", lowStock + " Item Inventory Low");
		} else if (lowStock > 1) {
			theModel.addAttribute("lowStockMsg", lowStock + " Items Inventory Low");
		} else {
			theModel.addAttribute("lowStockMsg", null);
		}
		theModel.addAttribute("lowInventory", lowStock);
	}
	@GetMapping("/list")
	public String showUsers(Model theModel) {
		getInventoryNotification(theModel);
		List<Review> review = reviewRepository.findAll();
		theModel.addAttribute("review",review);
		return "review-list";
	}
  @GetMapping("/deleteReview/{id}")
	public String deleteReview(@PathVariable (value = "id") int id) {
		this.reviewRepository.deleteById(id);
		return"redirect:/admin/review/list";
	}
}
