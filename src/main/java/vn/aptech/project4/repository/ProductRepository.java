package vn.aptech.project4.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.aptech.project4.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO product_ingredient (ingredient_id,product_id,quantity) VALUES (:ingredient_id,:product_id,:quantity)", nativeQuery = true)
	void saveIngredient(@Param("ingredient_id") int ingredient_id, @Param("product_id") int product_id,
			@Param("quantity") int quantity);
}
