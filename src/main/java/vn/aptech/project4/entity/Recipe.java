package vn.aptech.project4.entity;

import java.util.List;

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

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "recipe")
public class Recipe {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@JoinColumn(name="pro_ingre_id")
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private ProductIngredient productIngredient;
	@JoinColumn(name="size_id")
	@ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}, fetch = FetchType.EAGER)
	private Size size;
	@Column(name = "quantity")
	private int quantity;

	public Recipe() {
	}

	public Recipe(ProductIngredient productIngredient, Size size, int quantity) {
		this.productIngredient = productIngredient;
		this.size = size;
		this.quantity = quantity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ProductIngredient getProductIngredient() {
		return productIngredient;
	}

	public void setProductIngredient(ProductIngredient productIngredient) {
		this.productIngredient = productIngredient;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Recipe [id=" + id + ", productIngredientId=" + productIngredient.getId() + ", size=" + size + ", quantity="
				+ quantity + "]";
	}

}
