package ifi.com.repository;

import org.springframework.data.repository.CrudRepository;

import ifi.com.model.Card;;

public interface CardRepository extends CrudRepository<Card, Integer>{

	Card findByCardId(Integer id);
	
}
