package ifi.com.repository;

import org.springframework.data.repository.CrudRepository;

import ifi.com.model.Account;
import ifi.com.model.AccountId;

public interface AccountRepository extends CrudRepository<Account, AccountId>{

	Account findById(AccountId accID);

}
