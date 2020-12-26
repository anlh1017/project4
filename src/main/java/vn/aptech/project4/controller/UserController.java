package vn.aptech.project4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.aptech.project4.entity.Inventory;
import vn.aptech.project4.entity.User;
import vn.aptech.project4.repository.InventoryRepository;
import vn.aptech.project4.repository.UserRepository;

import java.util.List;

@Controller
@RequestMapping(value = "/users")
public class UserController {
	private UserRepository userRepository;
	private InventoryRepository inventoryRepository;
	private int lowStock=0;
	@Autowired
	public UserController( InventoryRepository inventoryRepository,UserRepository userRepository) {
		this.userRepository = userRepository;
		this.inventoryRepository = inventoryRepository;
	}
	@GetMapping("/list")
	public String showUsers(Model theModel) {
		getInventoryNotification(theModel);
		List<User> users = userRepository.findAll();
		theModel.addAttribute("users",users);
		return "users-list";
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
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		getInventoryNotification(theModel);
		User theUser = new User();
		theModel.addAttribute("user", theUser);
		return "user-form";
	}
}
