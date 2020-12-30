package vn.aptech.project4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.aptech.project4.entity.ProductIngredient;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductIngredientRepository extends JpaRepository<ProductIngredient, Integer> {
    List<ProductIngredient> findAllByProduct_Id(Integer id);

    Optional<ProductIngredient> findByProduct_IdAndIngredient_Id(Integer proId, Integer ingreId);

    void deleteProductIngredientsByProduct_IdAndAndIngredient_Id(Integer proId, Integer ingreId);
}
