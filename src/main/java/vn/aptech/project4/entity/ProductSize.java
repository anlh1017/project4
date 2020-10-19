package vn.aptech.project4.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="products_size")
public class ProductSize implements Serializable {
	@Id
	@JoinColumn(name="ProductsId")
	@ManyToOne(cascade = CascadeType.ALL)
	private Product product;
	@Id
	@JoinColumn(name="SizeId")
	@ManyToOne(cascade = CascadeType.ALL)
	private Size size;
	@Column(name="Price")
	private int price;
	@Override
	public boolean equals(Object obj) {
	      if (this == obj) return true;
	        if (obj == null || getClass() != obj.getClass()) return false;
	        ProductSize productSize = (ProductSize) obj;
	        return (size.getName().equals(productSize.getSize().getName()) &&
	        		(product.getProductName().equals(productSize.getProduct().getProductName())));
	}
	@Override
	public int hashCode() {
		return Objects.hash(product.getId(),size.getId());
	}

	public ProductSize() {
	}
	public ProductSize(Product product, Size size, int price) {
		this.product = product;
		this.size = size;
		this.price = price;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}
