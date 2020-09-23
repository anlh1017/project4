package vn.aptech.project4.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="ingredient")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="ingredient_name")
	private String ingredientName;
	@Column(name="vendor_name")
	private String vendorName;
	/*
	 * @ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE,
	 * CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	 * 
	 * @JoinTable(name="product_ingredient", joinColumns
	 * = @JoinColumn(name="ingredient_id"),inverseJoinColumns
	 * = @JoinColumn(name="product_id")) private List<Product> products;
	 */
	@OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL)
	private List<ProductIngredient> productIngredients = new ArrayList<>();
	@Override
	public String toString() {
		return "Ingredient [id=" + id + ", ingredientName=" + ingredientName + ", vendorName=" + vendorName
				+ "]";
	}
	
}
