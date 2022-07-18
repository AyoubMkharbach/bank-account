package com.ayoub.bankaccount.entites;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Account Bean
 * @author mkharbach
 * @since 1.0.0
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountNumber;
    private double balance;
    private String clientName;
    @OneToMany(mappedBy = "account")
    private List<AccountOperation> accountOperations = new ArrayList<>();

    public void addAccountOperation(AccountOperation accountOperation){
        accountOperations.add(accountOperation);
        accountOperation.setAccount(this);
    }
}
