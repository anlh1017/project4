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

	public Inventory() {
	}
	public Inventory(Ingredient ingredient, String vendorName, String unit, int price, float ratio, int status) {
		this.ingredient = ingredient;
		this.vendorName = vendorName;
		this.unit = unit;
		this.price = price;
		this.ratio = ratio;
		this.status = status;
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
