package ifi.com.repository;

import org.springframework.data.repository.CrudRepository;

import ifi.com.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer>{

	Customer findByCustomerId(Integer id);

	
}
