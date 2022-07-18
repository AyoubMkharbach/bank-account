package com.ayoub.bankaccount.mappers;

import com.ayoub.bankaccount.dtos.AccountDTO;
import com.ayoub.bankaccount.dtos.AccountOperationDTO;
import com.ayoub.bankaccount.entites.Account;
import com.ayoub.bankaccount.entites.AccountOperation;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper between entities and DTO
 * @author mkharbach
 * @since 1.0.0
 */
public class Mapper {

    /**
     * Map Account DTO to Account
     * @param accountDTO dto to map
     * @return account object
     */
    public static Account fromAccountDTO(AccountDTO accountDTO) {
        Account account = new Account();
        BeanUtils.copyProperties(accountDTO, account);
        return account;
    }

    /**
     * Map Account with all operations to Account DTO
     * @param account account to map
     * @return account dto object
     */
    public static AccountDTO fromAccount(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        BeanUtils.copyProperties(account, accountDTO);

        List<AccountOperationDTO> operationsDTO = account.getAccountOperations()
                .stream()
                .map(Mapper::fromAccountOperation)
                .collect(Collectors.toList());

        accountDTO.setOperations(operationsDTO);

        return accountDTO;
    }

    /**
     * Map operation to operation DTO
     * @param accountOperation operation to map
     * @return operation dto object
     */
    private static AccountOperationDTO fromAccountOperation(AccountOperation accountOperation){
        AccountOperationDTO AccountOperationDTO = new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation, AccountOperationDTO);
        return AccountOperationDTO;
    }
}
