package org.camamonte.junit5.example.model;

import lombok.*;

import java.math.BigDecimal;

//@Data    --> if you Put @Data, then your unit test going to fail because the @Data implement Equals and Hashcode.
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Account {
    private String person;
    private BigDecimal balance;

}

