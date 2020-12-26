package vn.aptech.project4.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.aptech.project4.entity.Product;
import vn.aptech.project4.repository.ProductRepository;

import java.util.List;

@RestController
public class RestProductController {
    private ProductRepository productRepository;

    public RestProductController() {
    }
    @Autowired
    public RestProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @GetMapping("/api/products")
    public List<Product> getProducts(){
        return productRepository.findAll();
    }
}
