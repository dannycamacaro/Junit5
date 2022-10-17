package org.camamonte.junit5.example.model;

import lombok.*;
import org.camamonte.junit5.example.exception.InsuficientMoneyAvaliable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Bank {

    private String name;
    private List<Account> accounts;

    public void addAccount(Account account){
        this.accounts.add(account);
        account.setBank(this);
    }
    public void transfer(Account accountSender,Account accountReciver, BigDecimal ammount) {
        if (Objects.isNull(ammount)
                || (ammount.compareTo(BigDecimal.ZERO) == 0)
                || (ammount.compareTo(BigDecimal.ZERO) == -1)) {
            throw new RuntimeException("Invalid Ammount");
        }
        if (accountSender.getBalance().subtract(ammount).compareTo(BigDecimal.ZERO) < 1){
            throw new InsuficientMoneyAvaliable("Insuficient money");
        }
        accountSender.setBalance(accountSender.getBalance().subtract(ammount));
        accountReciver.setBalance(accountReciver.getBalance().add(ammount));
    }
}
