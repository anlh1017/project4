package vn.aptech.project4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.aptech.project4.entity.Recipe;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
	@Transactional
	@Modifying
	@Query("delete From Recipe r where r.id =:id ")
	void deleteRecipe(@Param("id") int id);

	List<Recipe> findAllByProductIngredient_Id(Integer id);
	void deleteRecipesByProductIngredient_Id(Integer id);
	
	
	@Query(value = "Select r.* from recipe r inner join product_ingredient pi on r.pro_ingre_id = pi.id inner join "
			+ "products p on p.productId = pi.product_id where p.productId = :id group by r.pro_ingre_id",
			nativeQuery = true)	
	List<Recipe> getRecipeByProductId(@Param("id") int id);
}
