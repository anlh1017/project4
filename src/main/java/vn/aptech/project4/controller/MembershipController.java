package vn.aptech.project4.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.aptech.project4.entity.Customer;
import vn.aptech.project4.entity.Membership;
import vn.aptech.project4.repository.MembershipRepository;
import vn.aptech.project4.service.MembershipService;

@Controller
@RequestMapping("/membership")
public class MembershipController {
	private MembershipRepository membershipRepository;
	private MembershipService membershipService;
	@Autowired
	public MembershipController(MembershipRepository membershipRepository) {
		this.membershipRepository = membershipRepository;
	}
	@GetMapping("/list")
	public String ShowMembership(Model theModel) {
		List<Membership> membership = membershipRepository.findAll();
		theModel.addAttribute("membership", membership);
		return "membership-list";
	}
	@GetMapping("/createMembership")
	public String createMembership(Model theModel) {
		Membership membership = new Membership();
		theModel.addAttribute("membership", membership);
		return "membership-create";
	}
	@PostMapping("/saveMembership")
	public String saveMembership(@ModelAttribute("membership") Membership membership, ModelMap theModelMap) {
		theModelMap.addAttribute("membership", membership);
		membershipRepository.save(membership); 	
		return"redirect:/membership/list";
	}
	@GetMapping("/editMembership/{id}")
	public String editMembership(@PathVariable (value = "id") int id, Model theModel) {
		Optional<Membership> membership= membershipRepository.findById(id);
		theModel.addAttribute("membership",membership);
		return "membership-edit";
	
	}
	@GetMapping("/deleteMembership/{id}")
	public String deleteMembership(@PathVariable (value = "id") int id) {
		this.membershipRepository.deleteById(id);
		return"redirect:/membership/list";
	}

}
