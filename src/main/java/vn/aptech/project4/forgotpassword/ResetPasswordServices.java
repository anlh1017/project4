package vn.aptech.project4.forgotpassword;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.aptech.project4.entity.Customer;

@Service
@Transactional
public class ResetPasswordServices {
	
	@Autowired
	private ResetPasswordRepository resetpasswordRepo;
	
	  public void updateResetPasswordToken(String token, String email) throws CustomerNotFoundException  {
        Customer customer = resetpasswordRepo.findByEmail(email);
        if (customer != null) {
            customer.setResetPasswordToken(token);
            resetpasswordRepo.save(customer);
        } else {
            throw new CustomerNotFoundException("Could not find any customer with the email " + email);
        }
    }
     
    public Customer getByResetPasswordToken(String resetPasswordToken) {
        return resetpasswordRepo.findByResetPasswordToken(resetPasswordToken);
    }
     
    public void updatePassword(Customer customer, String newPassword) {
        
        customer.setCustomer_password(newPassword);;
         
        customer.setResetPasswordToken(null);
        resetpasswordRepo.save(customer);
    }
}
