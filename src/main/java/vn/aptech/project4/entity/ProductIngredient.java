package vn.aptech.project4.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="product_ingredient")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductIngredient implements Serializable {
	@Id
	@JoinColumn(name="ingredient_id")
	@ManyToOne(cascade = CascadeType.ALL)
	private Ingredient ingredient;
	@Id
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="product_id")
	private Product product;
	@Column(name="quantity")
	private int quantity;
	@Override
	public boolean equals(Object obj) {
	      if (this == obj) return true;
	        if (obj == null || getClass() != obj.getClass()) return false;
	        ProductIngredient productIngredient = (ProductIngredient) obj;
	        return (ingredient.getIngredientName().equals(productIngredient.getIngredient().getIngredientName()) &&
	        		(product.getProductName().equals(productIngredient.getProduct().getProductName())));
	}
	@Override
	public int hashCode() {
		return Objects.hash(product.getId(),ingredient.getId());
	}
}
