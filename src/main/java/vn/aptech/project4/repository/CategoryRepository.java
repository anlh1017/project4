package vn.aptech.project4.repository;

import java.util.Calendar;
import java.util.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vn.aptech.project4.entity.Category;
import vn.aptech.project4.entity.Order;
import vn.aptech.project4.entity.OrderDetail;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	

	
}
