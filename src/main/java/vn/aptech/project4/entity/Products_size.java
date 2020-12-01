package vn.aptech.project4.entity;

import javax.persistence.*;

@Entity
@Table(name="products_size")

public class Products_size {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ProductSizeId")
	private int ProductSizeId;
	
	@Column(name="ProductsId")
	private int ProductsId;
	
	@Column(name="SizeId")
	private int SizeId;  

	@Column(name="Price")
	private int Price;

	public Products_size() {
	}

	public int getProductSizeId() {
		return ProductSizeId;
	}

	public void setProductSizeId(int productSizeId) {
		ProductSizeId = productSizeId;
	}

	public int getProductsId() {
		return ProductsId;
	}

	public void setProductsId(int productsId) {
		ProductsId = productsId;
	}

	public int getSizeId() {
		return SizeId;
	}

	public void setSizeId(int sizeId) {
		SizeId = sizeId;
	}

	public int getPrice() {
		return Price;
	}

	public void setPrice(int price) {
		Price = price;
	}
}
