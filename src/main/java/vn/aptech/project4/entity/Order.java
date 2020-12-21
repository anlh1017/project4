package vn.aptech.project4.entity;


import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
	public class Order {
		 
	    @Id
	    @Column(name="order_id")
	    @GeneratedValue(strategy=GenerationType.IDENTITY)
	    private int id;
	 
	    @Column(name = "order_date", columnDefinition = "date")
	    private Date orderDate;

	    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	    @JoinColumn(name = "customer_id")
	    private Customer customer;
	    
	    @Column(name = "total",nullable = false)
	    private long total;
	    
	    @Column(name= "status",nullable = false)
	    private int status;
		
	    @Column(name= "address",nullable = false)
	    private String shippingaddress;	    
	    
	    @Column(name= "time",nullable = false)
	    private String time;	

	    
	    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	    @JoinColumn(name = "order_id")
	    private List<OrderDetail> orderDetails;

	public Order() {
	}
	public Order(Date orderDate,Customer customer, Long total, int status, String shippingaddress,String time) {
		this.orderDate = orderDate;
		this.customer = customer;
		this.total = total;
		this.status = status;
		this.shippingaddress= shippingaddress;
		this.time = time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getShippingaddress() {
		return shippingaddress;
	}
	public void setShippingaddress(String shippingaddress) {
		this.shippingaddress = shippingaddress;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}	

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}
}
