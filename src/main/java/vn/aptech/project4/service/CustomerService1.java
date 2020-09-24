package vn.aptech.project4.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.aptech.project4.entity.Customer;
import vn.aptech.project4.repository.CustomerRepository;

@Service
public class CustomerService1 {
	@Autowired
	private CustomerRepository customerRepository;
	
	public List<Customer> listAll(String search){
		if(search != null) {
			return customerRepository.findBySearch(search);
		}
		return customerRepository.findAll();
	}

}
