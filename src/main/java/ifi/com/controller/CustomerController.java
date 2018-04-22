package ifi.com.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ifi.com.ErrorType;
import ifi.com.model.Customer;
import ifi.com.repository.CustomerRepository;

@RestController
@RequestMapping(path="/api/customer")
public class CustomerController {

	public static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	private CustomerRepository customerRepository;
	
	
	@PostMapping(path ="/add")
	public @ResponseBody String addCustomer (
			@RequestParam int id, 
			@RequestParam String name, 
			@RequestParam String address,
			@RequestParam String phone,
			@RequestParam String detail) {
		
		Customer cus = new Customer();
		cus.setCustomerId(id);
		cus.setCustomerName(name);
		cus.setCustomerAddress(address);
		cus.setPhone(phone);
		cus.setOtherDetails(detail);
		customerRepository.save(cus);
		return "Add successfully";
	}
	
	@GetMapping(path="/get/all")
	public @ResponseBody Iterable<Customer> getAllCustomer() {
		return customerRepository.findAll();
	}
	
	@GetMapping(path ="/get/{id}")
	public @ResponseBody ResponseEntity<?> getByCustomerId (@PathVariable int id){
		Customer cus = customerRepository.findByCustomerId(id);
		if(cus == null) {
			logger.error("Customer with id {} not found.", id);
			return new ResponseEntity<>(new ErrorType("Customer with id " + id + " not found"), HttpStatus.NOT_FOUND);
				}
		return new ResponseEntity<Customer>(cus, HttpStatus.OK);		
	}
	
	@DeleteMapping(path="/delete/{id}")
	public @ResponseBody ResponseEntity<?> deleteCustomer (@PathVariable int id) {
		Customer cus = customerRepository.findByCustomerId(id);
		if(cus == null) {
			logger.error("Customer with id {} not found.", id);
			return new ResponseEntity<>(new ErrorType("Customer with id " + id + " not found"), HttpStatus.NOT_FOUND);
				}
		customerRepository.delete(id);
		return new ResponseEntity<Customer>(HttpStatus.NO_CONTENT);
	}
	@PutMapping(path ="/update/{id}")
	public @ResponseBody ResponseEntity<?> updateCustomer (
			@PathVariable int id, 
			@RequestParam String name,
			@RequestParam String address,
			@RequestParam String phone,
			@RequestParam String detail) {
		Customer currentCus = customerRepository.findByCustomerId(id);
		if(currentCus == null) {
			logger.error("Customer with id {} not found.", id);
			return new ResponseEntity<Object>(new ErrorType("Customer with id " + id + " not found"), HttpStatus.NOT_FOUND);
				}
		currentCus.setCustomerName(name);
		currentCus.setCustomerAddress(address);
		currentCus.setPhone(phone);
		currentCus.setOtherDetails(detail);
		
		customerRepository.save(currentCus);
		return new ResponseEntity<Customer>(currentCus, HttpStatus.OK);
		
	}
	

}
