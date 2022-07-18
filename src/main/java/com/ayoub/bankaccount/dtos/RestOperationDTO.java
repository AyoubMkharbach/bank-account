package com.ayoub.bankaccount.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO used in REST API deposit and withdrawal
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestOperationDTO {
    private long accountNumber;
    private double amount;
    private String description;
}
