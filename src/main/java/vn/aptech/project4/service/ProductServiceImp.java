package vn.aptech.project4.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.aptech.project4.entity.Product;
import vn.aptech.project4.repository.ProductRepository;
@Service
public class ProductServiceImp implements ProductService {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	public ProductServiceImp(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	@Override
	public List<Product> getAllProduct() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveProduct(Product product) {
		// TODO Auto-generated method stub

	}

	@Override
	public Product getProductById(int id) {
		
			Optional<Product> optional =productRepository.findById(id);
			Product product = null;
			if(optional.isPresent()) {
				product = optional.get();
			}else {
				throw new RuntimeException("Customer not found for id :: " +id);
			}
			return product;
	}

	@Override
	public void deleteProductById(int id) {
		// TODO Auto-generated method stub

	}
	@Override
	public void update(Product product) {
		productRepository.save(product);
	}

}
