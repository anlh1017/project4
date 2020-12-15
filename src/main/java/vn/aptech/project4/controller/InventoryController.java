package vn.aptech.project4.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.aptech.project4.entity.Inventory;
import vn.aptech.project4.repository.InventoryRepository;

import java.util.List;

@Controller
@RequestMapping("/admin/inventory")
public class InventoryController {
	private InventoryRepository inventoryRepository;
	
	@Autowired
	public InventoryController(InventoryRepository inventoryRepository) {
		this.inventoryRepository = inventoryRepository;
	}
	@GetMapping("")
	public String showInventory(Model theModel) {
			List<Inventory> managedInventory = inventoryRepository.findAllByStatus(2);
		List<Inventory> unmanagedInventory = inventoryRepository.findAllByStatus(1);
			theModel.addAttribute("inventory", managedInventory);
		theModel.addAttribute("unmanagedinventory", unmanagedInventory);
		return "list-inventory";
	}
	@GetMapping("/import/{id}")
	public String importInventory(Model theModel,@PathVariable(value="id")int id) {
		Inventory inventory = inventoryRepository.findById(id).get();
		theModel.addAttribute("inventory", inventory);
		return "import-inventory";
	}
	@PostMapping("/import/{id}")
	public String doImport(Model theModel,@PathVariable(value="id")int id,@RequestParam(value="quantity")int quantity) {
		Inventory inventory = inventoryRepository.findById(id).get();
		int remain = 	inventory.getQuantity() +quantity;
		inventory.setQuantity(remain);
		inventoryRepository.save(inventory);
		return "redirect:/inventory";
	}
	@GetMapping("/export/{id}")
	public String exportInventory(Model theModel,@PathVariable(value="id")int id) {
		Inventory inventory = inventoryRepository.findById(id).get();
		theModel.addAttribute("inventory", inventory);
		return "export-inventory";
	}
	@PostMapping("/export/{id}")
	public String doExport(Model theModel,@PathVariable(value="id")int id,@RequestParam(value="quantity")int quantity,@RequestParam(value="equiQuantity")int equiQuantity,RedirectAttributes redirectAttributes) {
		Inventory inventory = inventoryRepository.findById(id).get();
		int remain = 	inventory.getQuantity() - quantity;
		if(remain<0) {
			redirectAttributes.addAttribute("message","Not enough stock! Please import!");
			return "redirect:/inventory/export/"+id;
		}
		inventory.setQuantity(remain);
		inventoryRepository.save(inventory);
		return "redirect:/inventory";
	}
	@GetMapping("/edit/{id}")
	public String editInventory(Model theModel,@PathVariable(value="id")int id) {
		Inventory inventory = inventoryRepository.findById(id).get();
		theModel.addAttribute("inventory", inventory);
		return "update-inventory";
	}
	@PostMapping("/saveInventory")
	public String saveInventory(ModelMap modelMap,@ModelAttribute(value="inventory")Inventory inventory, Model theModel) {
		modelMap.addAttribute("inventory",inventory);
		inventoryRepository.save(inventory);
		theModel.addAttribute("inventory", inventory);
		return "redirect:/admin/inventory/edit/"+inventory.getId();
	}
}
