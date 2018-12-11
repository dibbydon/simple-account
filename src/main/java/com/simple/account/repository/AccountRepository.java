package com.simple.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.simple.account.model.Account;


public interface AccountRepository extends JpaRepository<Account, Long> {

}
