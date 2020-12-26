package vn.aptech.project4.entity;
	 
	import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
	 
	@Entity
	@Table(name = "order_detail")
	public class OrderDetail{
	 
	    @Id
	    @GeneratedValue(strategy=GenerationType.IDENTITY)
	    private int id;
	 
	    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	    @JoinColumn(name = "order_id")
	    private Order order;
	 
	    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	    @JoinColumn(name = "productId")
	    private Product productId;
	    
	    @Column(name= "quantity",nullable = false)
	    private int quantity;
	    
	    @Column(name= "price",nullable = false)
	    private int price;
	    @Column(name= "size",nullable = false)
	    private int sizeId;

		public OrderDetail() {
		}
		public OrderDetail(Order order, Product productId, int quantity, int price, int sizeId) {
			this.order = order;
			this.productId = productId;
			this.quantity = quantity;
			this.price = price;
			this.sizeId = sizeId;
		}
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public Order getOrder() {
			return order;
		}

		public void setOrder(Order order) {
			this.order = order;
		}

		public Product getProductId() {
			return productId;
		}

		public void setProductId(Product productId) {
			this.productId = productId;
		}

		public int getQuantity() {
			return quantity;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}

		public int getPrice() {
			return price;
		}

		public void setPrice(int price) {
			this.price = price;
		}

		public int getSizeId() {
			return sizeId;
		}

		public void setSizeId(int sizeId) {
			this.sizeId = sizeId;
		}
	}
