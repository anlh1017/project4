package vn.aptech.project4.entity;

import org.springframework.ui.Model;
import vn.aptech.project4.repository.InventoryRepository;
import vn.aptech.project4.repository.OrderRepository;

import java.util.List;

public class Notification {
    private int lowStock;
    private int newOrder;
    private OrderRepository orderRepository;
    private InventoryRepository inventoryRepository;

    public Notification() {
    }

    public Notification(int lowStock, int newOrder, OrderRepository orderRepository, InventoryRepository inventoryRepository) {
        this.lowStock = lowStock;
        this.newOrder = newOrder;
        this.orderRepository = orderRepository;
        this.inventoryRepository = inventoryRepository;
    }

    public void getNewOrderNotification(Model theModel){
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
    }
    public void getInventoryNotification(Model theModel){
        getNewOrderNotification(theModel);
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
}
