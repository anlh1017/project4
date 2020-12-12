package vn.aptech.project4.repository;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;
import vn.aptech.project4.entity.Ingredient;

@Repository
public interface IngredientRepository extends DataTablesRepository<Ingredient, Integer> {

}
