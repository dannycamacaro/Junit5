package org.camamonte.junit5.example.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    public static final String PERSON_NAME = "Danny Camacaro";
    public static final String PERSON_NAME_TWO = "David Camacaro";
    public static final String INVALID_PERSON_NAME = "Danny Camacaro R";
    public static final BigDecimal BALANCE = new BigDecimal("100.12345");
    public static final BigDecimal INVALID_BALANCE = new BigDecimal("100.1234");

    @Test
    @DisplayName("Valid account name")
    void testAccountName() {
        Account account = Account.builder().build();
        account.setPerson(PERSON_NAME);
        Assertions.assertEquals(PERSON_NAME, account.getPerson());
    }

    @Test
    @DisplayName("Invalid account name")
    void testInvalidAccountName() {
        Account account = Account.builder().build();
        account.setPerson(INVALID_PERSON_NAME);
        Assertions.assertNotEquals(PERSON_NAME, account.getPerson());
    }

    @Test
    @DisplayName("Valid account balance")
    void testValidAccountBalance() {
        Account account = Account.builder().person(PERSON_NAME).balance(BALANCE).build();
        Assertions.assertEquals(BALANCE, account.getBalance());
    }


    @Test
    @DisplayName("Invalid account balance")
    void testInvalidAccountBalance() {
        Account account = Account.builder().person(PERSON_NAME).balance(BALANCE).build();
        // you can call the assertNotEquals method because the import of Assertions is static
        assertNotEquals(INVALID_BALANCE, account.getBalance());
    }

    @Test
    @DisplayName("Valid account balance")
    void testNegativeAccountBalance() {
        Account account = Account.builder().person(PERSON_NAME).balance(BALANCE).build();
        assertFalse(account.getBalance().compareTo(new BigDecimal("0")) < 0);
    }

    @Test
    @DisplayName("Valid account balance")
    void testPositiveAccountBalance() {
        Account account = Account.builder().person(PERSON_NAME).balance(BALANCE).build();
        assertTrue(account.getBalance().compareTo(new BigDecimal("0")) > 0);
    }

    @Test
    @DisplayName("References accounts")
    void testAccountReference() {
        Account account = Account.builder().person(PERSON_NAME).balance(BALANCE).build();
        Account accountTwo = Account.builder().person(PERSON_NAME).balance(BALANCE).build();
        assertNotEquals(account, accountTwo);
    }
}