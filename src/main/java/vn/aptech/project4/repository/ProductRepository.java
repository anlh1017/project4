package vn.aptech.project4.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.aptech.project4.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
