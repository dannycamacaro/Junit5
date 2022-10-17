package org.camamonte.junit5.example.model;

import lombok.*;
import org.camamonte.junit5.example.exception.InsuficientMoneyAvaliable;

import java.math.BigDecimal;
import java.util.Objects;

//@Data    --> if you Put @Data, then your unit test going to fail because the @Data implement Equals and Hashcode.
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Account {
    private String person;
    private BigDecimal balance;
    private Bank bank;

    public void debit(BigDecimal ammount) {
        if (Objects.isNull(ammount) || (ammount.compareTo(BigDecimal.ZERO) == 0) || (ammount.compareTo(BigDecimal.ZERO) == -1)) {
            throw new RuntimeException("Invalid Ammount");
        }
        if (this.balance.subtract(ammount).compareTo(BigDecimal.ZERO) == -1) {
            throw new InsuficientMoneyAvaliable("Do not have enough money");
        }
        this.balance = this.balance.subtract(ammount);
    }

    public void credit(BigDecimal ammount) {
        if (Objects.isNull(ammount)
                || (ammount.compareTo(BigDecimal.ZERO) == 0)
                || (ammount.compareTo(BigDecimal.ZERO) == -1)) {
            throw new RuntimeException("Invalid Ammount");
        }
        this.balance = this.balance.add(ammount);
    }


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Account) || (this.person == null || this.balance == null)) {
            return false;
        }
        Account account = (Account) obj;
        return this.person.equalsIgnoreCase(account.getPerson()) && this.balance.equals(account.getBalance());
    }
}

