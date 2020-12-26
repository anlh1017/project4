package vn.aptech.project4.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.aptech.project4.entity.ChartInterface;
import vn.aptech.project4.entity.OrderDetail;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetail, Integer> {
	
	@Query(value = "select count(o.price) as count, p.productId, p.productName from order_detail o inner join products p on o.productId = p.productId group by productId order by count(price) desc limit 10" , nativeQuery = true)
	List<ChartInterface> getTopTenProduct();



}
