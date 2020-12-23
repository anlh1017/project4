package vn.aptech.project4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.aptech.project4.entity.ProductSize;

import java.util.*;

@Repository
public interface ProductSizeRepository extends JpaRepository<ProductSize, Integer> {
    List<ProductSize> findAllByProduct_Id(Integer id);
    ProductSize findByProduct_IdAndSize_Id(Integer proId,Integer sizeId);
}
