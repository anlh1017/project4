package vn.aptech.project4.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="category")
public class Category {
	@Id
	@Column(name="category_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="category_name")
	private String name;
	
	@OneToMany(mappedBy = "category",
				cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH },
				fetch = FetchType.LAZY)
	private List<Product> products;

	public Category() {
	}
	public Category(int id, String name) {
		this.id = id;
		this.name = name;
	}
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
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
}
