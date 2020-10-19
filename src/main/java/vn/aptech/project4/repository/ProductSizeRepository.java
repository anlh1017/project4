package vn.aptech.project4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.aptech.project4.entity.ProductSize;
@Repository

public interface ProductSizeRepository extends JpaRepository<ProductSize, ProductSize.ProductSizeId> {

}
