package vn.aptech.project4.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import vn.aptech.project4.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
	@Query(value="SELECT * FROM Customer o WHERE o.customer_name LIKE %:search% OR o.customer_email LIKE %:search% OR CONCAT (o.total_expense, '') LIKE %:search%", nativeQuery = true)
	public List<Customer> findBySearch(String search);

}
