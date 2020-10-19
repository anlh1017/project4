package vn.aptech.project4.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="product_size")
public class ProductSize {
	@Embeddable
	public static class ProductSizeId implements Serializable{
		@Column(name = "fk_product")
		protected Integer productId;

		@Column(name = "fk_size")
		protected Integer sizeId;

		public ProductSizeId() {

		}

		public ProductSizeId(int productId, int sizeId) {
			this.productId = productId;
			this.sizeId = sizeId;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((productId == null) ? 0 : productId.hashCode());
			result = prime * result
					+ ((sizeId == null) ? 0 : sizeId.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;

			ProductSizeId other = (ProductSizeId) obj;

			if (productId == null) {
				if (other.productId != null)
					return false;
			} else if (!productId.equals(other.productId))
				return false;

			if (sizeId == null) {
				if (other.sizeId != null)
					return false;
			} else if (!sizeId.equals(other.sizeId))
				return false;
			return true;
		}
	}
	@EmbeddedId
	private ProductSizeId id;
	@ManyToOne
	@MapsId("productId")
	@JoinColumn(name = "fk_product", insertable = false, updatable = false)
	private Product product;
	@ManyToOne
	@MapsId("sizeId")
	@JoinColumn(name = "fk_size", insertable = false, updatable = false)
	private Size size;
	@Column(name="price")
	private int price;

	public ProductSize() {
	}
	public ProductSize(Product product, Size size, int price) {
		this.id = new ProductSizeId(product.getId(),size.getId());
		this.product = product;
		this.size = size;
		this.price = price;
		product.addSize(this);
		size.addProduct(this);
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
