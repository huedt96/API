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
import ifi.com.model.Card;
import ifi.com.repository.CardRepository;

@RestController
@RequestMapping(path="/api/card")
public class CardController {

	public static final Logger logger = LoggerFactory.getLogger(CardController.class);
	@Autowired
	private CardRepository cardRepository;
	
	@PostMapping(path ="/add")
	public @ResponseBody String addCard (
			@RequestParam int id,
			@RequestParam String name, 
			@RequestParam String date_vaild_from, 
			@RequestParam String date_vaild_to,
			@RequestParam String detail) {
		Card card = new Card();
		card.setCardId(id);
		card.setCardName(name);
		card.setDateVaildFrom(date_vaild_from);
		card.setDateVaildTo(date_vaild_to);
		card.setOtherCardDetails(detail);
		
		cardRepository.save(card);
		return "Add successfully";
	}
	
	@GetMapping(path="/get/all")
	public @ResponseBody Iterable<Card> getAllCard() {
		return cardRepository.findAll();
	}
	
	@GetMapping(path ="/get/{id}")
	public @ResponseBody ResponseEntity<?> getByCardId (@PathVariable int id){
		Card card = cardRepository.findByCardId(id);
		if(card == null) {
			logger.error("Card with id {} not found.", id);
			return new ResponseEntity<>(new ErrorType("Card with id " + id + " not found"), HttpStatus.NOT_FOUND);
				}
		return new ResponseEntity<Card>(card, HttpStatus.OK);		
	}
	
	@DeleteMapping(path="/delete/{id}")
	public @ResponseBody ResponseEntity<?> deleteCard (@PathVariable int id) {
		Card card = cardRepository.findByCardId(id);
		if(card == null) {
			logger.error("Card with id {} not found.", id);
			return new ResponseEntity<>(new ErrorType("Card with id " + id + " not found"), HttpStatus.NOT_FOUND);
				}
		cardRepository.delete(id);
		return new ResponseEntity<Card>(HttpStatus.NO_CONTENT);
		
	}
	
	@PutMapping(path ="/update/{id}")
	public @ResponseBody ResponseEntity<?> updateCard (
			@PathVariable int id,
			@RequestParam String name, 
			@RequestParam String date_vaild_from, 
			@RequestParam String date_vaild_to,
			@RequestParam String detail) {
		Card currentCard = cardRepository.findByCardId(id);
		if(currentCard == null) {
			logger.error("Card with id {} not found.", id);
			return new ResponseEntity<Object>(new ErrorType("Card with id " + id + " not found"), HttpStatus.NOT_FOUND);
				}
		currentCard.setCardName(name);
		currentCard.setDateVaildFrom(date_vaild_from);
		currentCard.setDateVaildTo(date_vaild_to);
		currentCard.setOtherCardDetails(detail);
		
		cardRepository.save(currentCard);
		return new ResponseEntity<Card>(currentCard, HttpStatus.OK);
		
	}

}
