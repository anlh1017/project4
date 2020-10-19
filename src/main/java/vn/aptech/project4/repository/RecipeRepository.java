package vn.aptech.project4.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.aptech.project4.entity.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
	@Transactional
	@Modifying
	@Query("delete From Recipe r where r.id =:id ")
	void deleteRecipe(@Param("id") int id);

}
