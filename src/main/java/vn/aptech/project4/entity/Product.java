package vn.aptech.project4.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="products")
@Data
@NoArgsConstructor

public class Product {
	@Id
	@Column(name="productId")
	private int id;
	@Column(name="productName")
	private String productName;
	@Column(name="description")
	private String description;
	@Column(name="category")
	private int category;
	/*
	 * @ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE,
	 * CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	 * 
	 * @JoinTable(name="product_ingredient", joinColumns
	 * = @JoinColumn(name="product_id"),inverseJoinColumns
	 * = @JoinColumn(name="ingredient_id")) private List<Ingredient> ingredients;
	 * 
	 * public void addIngredient(Ingredient ingredient) { if(ingredients==null) {
	 * ingredients = new ArrayList<Ingredient>(); } ingredients.add(ingredient); }
	 */
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<ProductIngredient> productIngredients = new ArrayList<>();
	
	public boolean hasIngredient(Ingredient ingredient) {
		for (ProductIngredient productIngredient: getProductIngredients()) {
			if (productIngredient.getIngredient().getId() == ingredient.getId()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", productName=" + productName + ", description=" + description + ", category="
				+ category + "]";
	}	
	
}
