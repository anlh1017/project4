package vn.aptech.project4.service;

import java.util.List;

import vn.aptech.project4.entity.Product;

public interface ProductService {
	List<Product> getAllProduct();
	void saveProduct(Product product);
	Product getProductById(int id);
	void deleteProductById(int id);
	void update(Product product);
}
