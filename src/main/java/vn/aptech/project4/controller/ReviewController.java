package vn.aptech.project4.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.aptech.project4.entity.Review;

import vn.aptech.project4.repository.ReviewRepository;

@Controller
@RequestMapping("/admin/review")
public class ReviewController {

  private ReviewRepository reviewRepository;
	@Autowired
	public ReviewController(ReviewRepository reviewRepository) {
		this.reviewRepository = reviewRepository;
	}

	@GetMapping("/list")
	public String showUsers(Model theModel) {
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
