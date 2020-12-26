package vn.aptech.project4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.aptech.project4.entity.Inventory;
import vn.aptech.project4.entity.Review;
import vn.aptech.project4.repository.InventoryRepository;
import vn.aptech.project4.repository.ReviewRepository;

import java.util.List;

@Controller
@RequestMapping("/admin/review")
public class ReviewController {

  private ReviewRepository reviewRepository;
	private InventoryRepository inventoryRepository;
	private int lowStock=0;
	@Autowired
	public ReviewController(ReviewRepository reviewRepository,InventoryRepository inventoryRepository) {
		this.inventoryRepository = inventoryRepository;
		this.reviewRepository = reviewRepository;
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
