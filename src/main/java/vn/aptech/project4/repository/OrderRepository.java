package vn.aptech.project4.repository;

import java.util.Calendar;
import java.util.Date;
import java.awt.print.Pageable;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vn.aptech.project4.entity.Order;
import vn.aptech.project4.entity.OrderDetail;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
	
//	List<Order> findByDate(Date startDate, Date endDate);

//	@Query("SELECT o FROM orders o WHERE o.status = ?1")
//	Optional<Order> findByStatus(Integer status);
//	@Query("select o from orders o where o.order_date between :x and :y")
//	Optional<Order> findAllByDateBetween(
//            Date startdate,
//            Date enddate);; 
}
