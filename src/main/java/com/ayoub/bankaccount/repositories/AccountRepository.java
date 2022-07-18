package com.ayoub.bankaccount.repositories;

import com.ayoub.bankaccount.entites.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Account JPA repository
 * @author mkharbach
 * @since 1.0.0
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findAccountByClientName(String clientName);
}
