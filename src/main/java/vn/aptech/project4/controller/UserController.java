package vn.aptech.project4.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.aptech.project4.entity.User;
import vn.aptech.project4.repository.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {
	private UserRepository userRepository;
	@Autowired
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	@GetMapping("/list")
	public String showUsers(Model theModel) {
		List<User> users = userRepository.findAll();
		theModel.addAttribute("users",users);
		return "users-list";
	}
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		User theUser = new User();
		theModel.addAttribute("user", theUser);
		return "user-form";
	}
}
