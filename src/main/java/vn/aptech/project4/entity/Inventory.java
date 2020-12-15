package vn.aptech.project4.entity;

import javax.persistence.*;

@Entity
@Table(name="inventory")
public class Inventory {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name="ingredient_id")
	private Ingredient ingredient;
	@Column(name="vendor_name")
	private String vendorName;
	@Column(name="UMO")
	private String uMO;
	@Column(name="quantity")
	private int quantity;
	@Column(name="available")
	private int available;
	@Column(name="status")
	private int status;


	public Inventory() {
	}
	public Inventory(Ingredient ingredient,String vendorName, String uMO, int quantity,int available) {
		this.ingredient = ingredient;
		this.vendorName = vendorName;
		this.uMO = uMO;
		this.quantity = quantity;
		this.available = available;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getuMO() {
		return uMO;
	}

	public void setuMO(String uMO) {
		this.uMO = uMO;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getAvailable() {
		return available;
	}

	public void setAvailable(int available) {
		this.available = available;
	}
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
