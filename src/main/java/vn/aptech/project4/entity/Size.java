package vn.aptech.project4.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="size")
public class Size {
	@Id
	@Column(name = "Id", updatable = false, nullable = false)
	private int id;
	@Column(name="name")
	private String name;
	@OneToMany(mappedBy = "size")
	private List<ProductSize> products = new ArrayList<ProductSize>();
	public Size() {
	}
	public Size(int id, String name) {
		this.id = id;
		this.name = name;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ProductSize> getProducts() {
		return products;
	}

	public void setProducts(List<ProductSize> products) {
		this.products = products;
	}
	public void addProduct(ProductSize productSize){
		if(products==null){
			products = new ArrayList<>();
		}
		products.add(productSize);
	}
}
