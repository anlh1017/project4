package vn.aptech.project4.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Table(name="product_size")
public class ProductSize {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "fk_product")
	private Product product;
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "fk_size")
	private Size size;
	@Column(name="price")
	@Min(0)
	private int price;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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