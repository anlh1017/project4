package vn.aptech.project4.forgotpassword;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import vn.aptech.project4.entity.Customer;

public interface ResetPasswordRepository extends CrudRepository<Customer, Integer> {

	@Query("SELECT c FROM Customer c WHERE c.customerEmail = ?1")
	public Customer findByEmail(String email);
	
	public Customer findByResetPasswordToken(String token);
}
