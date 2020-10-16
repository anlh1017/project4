package vn.aptech.project4.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="products")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="productId")
	private int Id;

	public Product() {
	}
	public Product(int id, String productName, String description, Category category, String image) {
		this.Id = id;
		this.productName = productName;
		this.description = description;
		this.category = category;
		this.image = image;
	}
	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<ProductIngredient> getProductIngredients() {
		return productIngredients;
	}

	public void setProductIngredients(List<ProductIngredient> productIngredients) {
		this.productIngredients = productIngredients;
	}



	@Column(name="productName")
	private String productName;

	@Column(name="description")
	private String description;
	
	@ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name="category_id")
	private Category category;
	
	@Column(name="image")
	private String image;
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
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<ProductIngredient> productIngredients;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product")
	private List<ProductSize> productSizes;
	public List<ProductSize> getProductSizes() {
		return productSizes;
	}

	public void setProductSizes(List<ProductSize> productSizes) {
		this.productSizes = productSizes;
	}
	public boolean hasIngredient(Ingredient ingredient) {
		for (ProductIngredient productIngredient: getProductIngredients()) {
			if (productIngredient.getIngredient().getId() == ingredient.getId()) {
				return true;
			}
		}
		return false;
	}
	public void addIngredient(Ingredient ingredient) {
		if(getProductIngredients()==null) {
			productIngredients = new ArrayList<>();
		}
		productIngredients.add(new ProductIngredient(ingredient,this));
	}
	@Override
	public String toString() {
		return "Product [id=" + Id + ", productName=" + productName + ", description=" + description + ", category="
				+ category + "]";
	}
	public int getPrice(int sizeId){
		for (ProductSize temp: productSizes) {
			if(temp.getSize().getId()==sizeId) {
				return temp.getPrice();
			}
		}
		return 0;
	}
	public void addProductSize(ProductSize productSize){
		if(productSizes==null){
			productSizes = new ArrayList<>();
		}
		productSizes.add(productSize);
	}
	public boolean hasSize(Size size){
		for (ProductSize productSize: productSizes) {
			if (productSize.getSize().getId() == size.getId()) {
				return true;
			}
		}
		return false;
	}
	
}
