package com.ayoub.bankaccount.dtos;

import com.ayoub.bankaccount.enums.OperationType;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO for account operation
 * @author mkharbach
 * @since 1.0.0
 */
@Data
public class AccountOperationDTO {

    private Long operationId;
    private double amount;
    private OperationType operationType;
    private String description;
    private LocalDateTime operationDate;
}
