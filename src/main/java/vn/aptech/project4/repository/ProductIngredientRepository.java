package vn.aptech.project4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.aptech.project4.entity.ProductIngredient;

import java.util.List;

@Repository
public interface ProductIngredientRepository extends JpaRepository<ProductIngredient, Integer> {
    List<ProductIngredient> findAllByProduct_Id(Integer id);

    void deleteProductIngredientsByProduct_IdAndAndIngredient_Id(Integer proId,Integer ingreId);
}
