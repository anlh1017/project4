package vn.aptech.project4.entity;

import java.util.ArrayList;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="product_ingredient")
public class ProductIngredient {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@JoinColumn(name="ingredient_id")
	@ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	private Ingredient ingredient;
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="product_id")
	private Product product;
	@OneToMany(mappedBy = "productIngredient", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Recipe> recipes;

	public ProductIngredient() {
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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}

	public ProductIngredient(Ingredient ingredient, Product product) {
		this.ingredient = ingredient;
		this.product = product;
	}
	public void addRecipe(Recipe recipe) {
		if(recipes==null) {
			recipes= new ArrayList<>();
		}
		recipes.add(recipe);
	}
	public void removeRecipe(Recipe recipe) {
		if(recipes.contains(recipe)) {
			recipes.remove(recipe);
		}
	}
	@Override
	public String toString() {
		return "ProductIngredient [id=" + id + ", ingredientId=" + ingredient.getId() + ", productId=" + product.getId() + ", recipes="
				+ recipes + "]";
	}
	
	

}
