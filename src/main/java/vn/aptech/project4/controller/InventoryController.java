package vn.aptech.project4.controller;


import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.aptech.project4.entity.Ingredient;
import vn.aptech.project4.entity.Inventory;
import vn.aptech.project4.report.InventoryPDFExporter;
import vn.aptech.project4.repository.IngredientRepository;
import vn.aptech.project4.repository.InventoryRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/inventory")
public class InventoryController {
	private InventoryRepository inventoryRepository;
	private IngredientRepository ingredientRepository;
	private int lowStock=0;
	@Autowired
	public InventoryController(InventoryRepository inventoryRepository, IngredientRepository ingredientRepository) {
		this.inventoryRepository = inventoryRepository;
		this.ingredientRepository = ingredientRepository;
	}
	@GetMapping("")
	public String showInventory(Model theModel) {
		getInventoryNotification(theModel);
			List<Inventory> managedInventory = inventoryRepository.findAllByStatus(2);
		List<Inventory> unmanagedInventory = inventoryRepository.findAllByStatus(1);
			theModel.addAttribute("inventory", managedInventory);
		theModel.addAttribute("unmanagedinventory", unmanagedInventory);
		return "list-inventory";
	}
	@GetMapping("/import/{id}")
	public String importInventory(Model theModel,@PathVariable(value="id")int id) {
		getInventoryNotification(theModel);
		Inventory inventory = inventoryRepository.findById(id).get();
		theModel.addAttribute("inventory", inventory);
		return "import-inventory";
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
	@PostMapping("/import/{id}")
	public String doImport(Model theModel,@PathVariable(value="id")int id,@RequestParam(value="quantity")int quantity) {
		Inventory inventory = inventoryRepository.findById(id).get();
		int remain = 	inventory.getQuantity() +quantity;
		inventory.setQuantity(remain);
		inventoryRepository.save(inventory);
		return "redirect:/admin/inventory";
	}
	@GetMapping("/export/{id}")
	public String exportInventory(Model theModel,@PathVariable(value="id")int id, @ModelAttribute(value = "errorMsg")String message) {
		getInventoryNotification(theModel);
		Inventory inventory = inventoryRepository.findById(id).get();
		theModel.addAttribute("inventory", inventory);
		theModel.addAttribute("errorMsg", message);
		return "export-inventory";
	}
	@PostMapping("/export/{id}")
	public String doExport(Model theModel,@PathVariable(value="id")int id,@RequestParam(value="quantity")int quantity,RedirectAttributes redirectAttributes) {
		Inventory inventory = inventoryRepository.findById(id).get();
		int remain = 	inventory.getQuantity() - quantity;
		if(remain<0) {
			redirectAttributes.addFlashAttribute("errorMsg","Not enough stock! Please import!");
			return "redirect:/admin/inventory/export/"+id;
		}
		inventory.setQuantity(remain);
		float available = quantity*inventory.getRatio();
		Ingredient ingredient = ingredientRepository.findById(inventory.getIngredient().getId()).get();
		float curAvailable = ingredient.getAvailable();
		curAvailable += available;
		ingredient.setAvailable(curAvailable);
		ingredientRepository.save(ingredient);
		inventoryRepository.save(inventory);
		return "redirect:/admin/inventory";
	}
	@GetMapping("/edit/{id}")
	public String editInventory(Model theModel,@PathVariable(value="id")int id) {
		getInventoryNotification(theModel);
		Inventory inventory = inventoryRepository.findById(id).get();
		theModel.addAttribute("inventory", inventory);
		return "update-inventory";
	}
	@PostMapping("/saveInventory")
	public String saveInventory(ModelMap modelMap,@ModelAttribute(value="inventory")Inventory inventory, Model theModel) {
		modelMap.addAttribute("inventory",inventory);
		inventory.setStatus(2);
		inventoryRepository.save(inventory);
		theModel.addAttribute("inventory", inventory);
		return "redirect:/admin/inventory/edit/"+inventory.getId();
	}
	@GetMapping("/export/pdf")
    public void exportToPDF(HttpServletResponse response, @RequestParam(value="getMonth", required = false) int getMonth) throws DocumentException, IOException {
        response.setContentType("application/pdf"); DateFormat dateFormatter = new
                SimpleDateFormat("yyyy-MM-dd_HH:mm:ss"); String currentDateTime =
                dateFormatter.format(new Date());

        String headerKey = "Content-Disposition"; String headerValue =
                "attachment; filename=inventory_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Inventory> listInventory = inventoryRepository.findAll();
        List<Inventory> listAllInventory = new ArrayList<>();
        for (Inventory inventory : listInventory) {
			if(inventory.getInventoryDate().getMonth()==(getMonth-1)) {
				listAllInventory.add(inventory);
			}
		}
        InventoryPDFExporter exporter = new InventoryPDFExporter(listAllInventory,getMonth);
        exporter.export(response);

    }
}
