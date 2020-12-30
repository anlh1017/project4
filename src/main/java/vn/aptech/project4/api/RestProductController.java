package vn.aptech.project4.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import vn.aptech.project4.repository.ProductRepository;

@RestController
public class RestProductController {
    private ProductRepository productRepository;

    public RestProductController() {
    }

    @Autowired
    public RestProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
}
