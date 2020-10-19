package vn.aptech.project4.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import vn.aptech.project4.entity.Customer;
import vn.aptech.project4.entity.User;
import vn.aptech.project4.repository.CustomerRepository;

@Service
public class CustomerService1 implements UserDetailsService {
	@Autowired
	private CustomerRepository customerRepository;
	
	public List<Customer> listAll(String search){
		if(search != null) {
			return customerRepository.findBySearch(search);
		}
		return customerRepository.findAll();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	     Customer user = customerRepository.findByCustomerEmail(username)
	    	       .orElseThrow(() -> new UsernameNotFoundException("Email:  " + username + " not found"));
	    	         return new org.springframework.security.core.userdetails.User(user.getCustomerEmail(), user.getCustomer_password(),getAuthorities(user));
	}
    private static Collection<? extends GrantedAuthority> getAuthorities(Customer user) {
        String userRoles = user.getAuthority();
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
        return authorities;
    }
}
