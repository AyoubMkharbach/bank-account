package com.ayoub.bankaccount.repositories;

import com.ayoub.bankaccount.entites.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Operation JPA repository
 * @author mkharbach
 * @since 1.0.0
 */
public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {
}
