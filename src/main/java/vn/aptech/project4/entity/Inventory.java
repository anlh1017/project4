package vn.aptech.project4.entity;

import javax.persistence.*;
import java.util.Date;

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
	@Column(name="UOM")
	private String unit;
	@Column(name="quantity")
	private int quantity;
	@Column(name="price")
	private Integer price;
	@Column(name="ratio")
	private Float ratio;
	@Column(name="status")
	private int status;
	@Column(name="safety_stock")
	private int safetyStock;
	@Column(name = "date", columnDefinition = "date")
	private Date inventoryDate;

	public Date getInventoryDate() {
		return inventoryDate;
	}

	public void setInventoryDate(Date inventoryDate) {
		this.inventoryDate = inventoryDate;
	}

	public int getSafetyStock() {
		return safetyStock;
	}

	public void setSafetyStock(int safetyStock) {
		this.safetyStock = safetyStock;
	}
	public Inventory() {
	}
	public Inventory(Ingredient ingredient, String vendorName, String unit, int price, float ratio, int status, Date inventoryDate,int safetyStock) {
		this.ingredient = ingredient;
		this.vendorName = vendorName;
		this.unit = unit;
		this.price = price;
		this.ratio = ratio;
		this.status = status;
		this.inventoryDate = inventoryDate;
		this.safetyStock = safetyStock;
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

	public void setPrice(Integer price) {
		this.price = price;
	}

	public void setRatio(Float ratio) {
		this.ratio = ratio;
	}

	public int getStatus() {
		return status;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public float getRatio() {
		return ratio;
	}

	public void setRatio(float ratio) {
		this.ratio = ratio;
	}

}
