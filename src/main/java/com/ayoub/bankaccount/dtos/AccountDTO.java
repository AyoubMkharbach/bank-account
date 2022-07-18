package com.ayoub.bankaccount.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for Account
 * @author mkharbach
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    private Long accountNumber;
    private double balance;
    private String clientName;
    List<AccountOperationDTO> operations;

}
