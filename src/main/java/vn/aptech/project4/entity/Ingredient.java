package vn.aptech.project4.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="ingredient")
public class Ingredient {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="ingredient_name")
	private String ingredientName;
	@Column(name="UOM")
	private String unit;
	@Column(name = "available")
	private float available;
	@Transient
	private float cost;
	@Column(name="safety_stock")
	private int safetyStock;

	public int getSafetyStock() {
		return safetyStock;
	}

	public void setSafetyStock(int safetyStock) {
		this.safetyStock = safetyStock;
	}

	@OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL)
	@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
	private List<ProductIngredient> productIngredients = new ArrayList<>();
	@OneToOne
	@JoinColumn(name="id")
	private Inventory inventory;

	@Override
	public String toString() {
		return "Ingredient [id=" + id + ", ingredientName=" + ingredientName + ", UMO=" + unit
				+ "]";
	}

	public Ingredient() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIngredientName() {
		return ingredientName;
	}

	public void setIngredientName(String ingredientName) {
		this.ingredientName = ingredientName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String uMO) {
		this.unit = uMO;
	}

	public List<ProductIngredient> getProductIngredients() {
		return productIngredients;
	}

	public void setProductIngredients(List<ProductIngredient> productIngredients) {
		this.productIngredients = productIngredients;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public float getAvailable() {
		return available;
	}

	public void setAvailable(float available) {
		this.available = available;
	}

	public float getCost() {
		return cost = this.inventory.getPrice()/this.getInventory().getRatio();
	}

	public void setCost(float cost) {
		this.cost = cost;
	}
}
