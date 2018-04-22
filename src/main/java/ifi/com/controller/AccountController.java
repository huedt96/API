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
import ifi.com.model.Account;
import ifi.com.model.AccountId;
import ifi.com.model.Card;
import ifi.com.model.Customer;
import ifi.com.repository.AccountRepository;
import ifi.com.repository.CardRepository;
import ifi.com.repository.CustomerRepository;

@RestController
@RequestMapping(path="/api/account")
public class AccountController {

	public static final Logger logger = LoggerFactory.getLogger(AccountController.class);
	
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private CardRepository cardRepository;
	
	@PostMapping(path="/add")
	public @ResponseBody ResponseEntity<?> addAccount(
			@RequestParam int accid,
			@RequestParam int cusid,
			@RequestParam int cardid,
			@RequestParam String name,
			@RequestParam float balance,
			@RequestParam String detail
			) {
		Account acc = new Account();
		acc.setAccountName(name);
		acc.setCurrentBlance(balance);
		acc.setOtherDetail(detail);
		
		AccountId accID = new AccountId();
		accID.setAccountId(accid);
		accID.setCustomerId(cusid);
		accID.setCardId(cardid);
		
		Customer cus = customerRepository.findByCustomerId(cusid);
		if(cus == null) {
			logger.error("Customer with id {} not found.", cusid);
			return new ResponseEntity<>(new ErrorType("Customer with id " + cusid + " not found"), HttpStatus.NOT_FOUND);
				}
		cus.setCustomerId(cusid);
		Card card = cardRepository.findByCardId(cardid);
		if(card == null) {
			logger.error("Card with id {} not found", cardid);
			return new ResponseEntity<>(new ErrorType("Card with id " + cardid + " not found"), HttpStatus.NOT_FOUND);
				}
		card.setCardId(cardid);
		acc.setCustomer(cus);
		acc.setCard(card);
		acc.setId(accID);
		
		accountRepository.save(acc);
		return new ResponseEntity<Account>(acc, HttpStatus.OK);
	}
	@GetMapping(path = "/get/all")
	public @ResponseBody Iterable<Account> getAll() {
		return accountRepository.findAll();
	}
	@GetMapping(path = "/get/{accid}/{cusid}/{cardid}")
	public @ResponseBody ResponseEntity<?> getAccountId(
			@PathVariable int accid, 
			@PathVariable int cusid, 
			@PathVariable int cardid) {
		AccountId accID = new AccountId();
		accID.setAccountId(accid);
		accID.setCustomerId(cusid);
		accID.setCardId(cardid);
		Account acc = accountRepository.findById(accID);
		if (acc == null) {
			logger.error("Account with id {} not found.", acc);
			return new ResponseEntity<Object>(new ErrorType("Account with id " + acc + " not found"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Account>(acc, HttpStatus.OK);

	}
	@DeleteMapping(path = "/delete/{accid}/{cusid}/{cardid}")
	public @ResponseBody ResponseEntity<?> deleteId(
			@PathVariable int accid, 
			@PathVariable int cusid, 
			@PathVariable int cardid) {
		AccountId accID = new AccountId();
		accID.setAccountId(accid);
		accID.setCustomerId(cusid);
		accID.setCardId(cardid);
		Account acc = accountRepository.findById(accID);
		if (acc == null) {
			logger.error("Account with id {} not found.", acc);
			return new ResponseEntity<Object>(new ErrorType("Account with id " + acc + " not found"),
					HttpStatus.NOT_FOUND);
		}
		accountRepository.delete(acc);
		return new ResponseEntity<Account>(HttpStatus.OK);
	}
	@PutMapping(path = "/update/{accid}/{cusid}/{cardid}")
	public @ResponseBody ResponseEntity<?> updateAccount(
			@PathVariable int accid,
			@PathVariable int cusid,
			@PathVariable int cardid,
			@RequestParam String name,
			@RequestParam float balance,
			@RequestParam String detail
			) {
		AccountId accID = new AccountId();
		accID.setAccountId(accid);
		accID.setCustomerId(cusid);
		accID.setCardId(cardid);
		Account acc = accountRepository.findById(accID);
		if (acc == null) {
			logger.error("Account with id {} not found.", acc);
			return new ResponseEntity<Object>(new ErrorType("Account with id " + acc + " not found"),
					HttpStatus.NOT_FOUND);
		}
		Customer cus = new Customer();
		cus.setCustomerId(cusid);
		Card card = new Card();
		card.setCardId(cardid);
		acc.setCustomer(cus);
		acc.setCard(card);
		acc.setId(accID);
		acc.setAccountName(name);
		acc.setCurrentBlance(balance);
		acc.setOtherDetail(detail);
		accountRepository.save(acc);
		
		return new ResponseEntity<Account>(acc, HttpStatus.OK);
	}
}
