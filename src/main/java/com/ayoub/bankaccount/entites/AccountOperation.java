package com.ayoub.bankaccount.entites;

import com.ayoub.bankaccount.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

/**
 * Account operation bean
 * @author mkharbach
 * @since 1.0.0
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long operationId;
    private double amount;
    @Enumerated(EnumType.STRING)
    private OperationType operationType;
    private String description;
    private LocalDateTime operationDate;
    @ManyToOne
    private Account account;

}
