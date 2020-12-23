package vn.aptech.project4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.aptech.project4.entity.Product;

import javax.transaction.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	@Transactional
	@Modifying
	@Query("delete From ProductIngredient p Where p.id=:id")
	void deleteRecipeAndProductIngredient(@Param("id") int id);

	
}
