package vn.aptech.project4.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.aptech.project4.entity.Size;

@Repository
public interface SizeRepository extends JpaRepository<Size, Integer> {


}
