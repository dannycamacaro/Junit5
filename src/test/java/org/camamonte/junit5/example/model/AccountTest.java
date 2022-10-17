package org.camamonte.junit5.example.model;

import org.camamonte.junit5.example.exception.InsuficientMoneyAvaliable;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    public static final String PERSON_NAME = "Danny Camacaro";
    public static final String PERSON_NAME_TWO = "David Camacaro";
    public static final String INVALID_PERSON_NAME = "Danny Camacaro R";
    public static final BigDecimal BALANCE = new BigDecimal("1000.12345");
    public static final BigDecimal INVALID_BALANCE = new BigDecimal("100.1234");
    public static final BigDecimal TRANSFER_AMMOUNT = new BigDecimal("100");
    public static final BigDecimal CREDIT = new BigDecimal("101");
    public static final BigDecimal DEBIT = new BigDecimal("21.12345");
    public static final BigDecimal DEBIT_MAX = new BigDecimal("3221.12345");
    public static final String CHASE_BANK = "Chase Bank";

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
        assertEquals(account, accountTwo);
    }

    @Test
    void accountDebit() {
        Account account = Account.builder().person(PERSON_NAME).balance(BALANCE).build();
        account.debit(DEBIT);
        BigDecimal expectedValue = BALANCE.subtract(DEBIT);
        assertNotNull(account.getBalance());
        assertEquals(expectedValue, account.getBalance());
    }

    @Test
    void accountCreit() {
        Account account = Account.builder().person(PERSON_NAME).balance(BALANCE).build();
        account.credit(CREDIT);
        BigDecimal expectedValue = BALANCE.add(CREDIT);
        assertNotNull(account.getBalance());
        assertEquals(expectedValue, account.getBalance());
    }

    @Test
    void insuficientMoney() {
        Account account = Account.builder().person(PERSON_NAME).balance(BALANCE).build();
        assertNotNull(account.getBalance());
        assertThrows(InsuficientMoneyAvaliable.class, () -> {
            account.debit(DEBIT_MAX);
        });
    }

    @Test
    void invalidAmmount() {
        Account account = Account.builder().person(PERSON_NAME).balance(BALANCE).build();
        assertNotNull(account.getBalance());
        assertThrows(RuntimeException.class, () -> {
            account.debit(BigDecimal.ZERO);
        });
    }

    @Test
    void transferingMoney() {
        Account accountReciver = Account.builder().person(PERSON_NAME).balance(BALANCE).build();
        Account accountSender = Account.builder().person(PERSON_NAME_TWO).balance(BALANCE).build();
        BigDecimal expectedAmmountSender = accountSender.getBalance().subtract(TRANSFER_AMMOUNT);
        BigDecimal expectedAmmountReciver = accountReciver.getBalance().add(TRANSFER_AMMOUNT);

        Bank bank = Bank.builder().name("Chase").build();
        bank.transfer(accountSender, accountReciver, TRANSFER_AMMOUNT);

        assertEquals(expectedAmmountReciver, accountReciver.getBalance());
        assertEquals(expectedAmmountSender, accountSender.getBalance());
    }

    @Test
    void relationshipAccountsBanks() {
        Account accountOne = Account.builder().person(PERSON_NAME).balance(BALANCE).build();
        Account accountTwo = Account.builder().person(PERSON_NAME_TWO).balance(BALANCE).build();

        Bank bank = Bank.builder().name(CHASE_BANK).accounts(new ArrayList<>()).build();
        bank.addAccount(accountOne);
        bank.addAccount(accountTwo);

        assertEquals(2, bank.getAccounts().size());
        assertEquals(CHASE_BANK, accountOne.getBank().getName());
        assertEquals(PERSON_NAME,bank.getAccounts().stream().filter((x -> x.getPerson().equalsIgnoreCase(PERSON_NAME))).findFirst().get().getPerson());
    }

    @Test
    void relationshipAccountsBanksWithAssertAll() {
        Account accountOne = Account.builder().person(PERSON_NAME).balance(BALANCE).build();
        Account accountTwo = Account.builder().person(PERSON_NAME_TWO).balance(BALANCE).build();

        Bank bank = Bank.builder().name(CHASE_BANK).accounts(new ArrayList<>()).build();
        bank.addAccount(accountOne);
        bank.addAccount(accountTwo);

        assertAll(
                // to avoid that the message on each msg be created you can introduce a lambda expression to avoid crea the string msg every time,
                // with a lambda expression we create the string only if the test case fail.
                ()-> {assertEquals(2, bank.getAccounts().size(),"the account list is not working fine!!!");},
                ()-> {assertEquals(CHASE_BANK, accountOne.getBank().getName(),()-> "this is the best way for a message");},
                ()-> {assertEquals(PERSON_NAME,bank.getAccounts().stream().filter((x -> x.getPerson().equalsIgnoreCase(PERSON_NAME))).findFirst().get().getPerson());}
        );
    }

    @Test
    @Disabled // this annotation disabled the test case
    @DisplayName("Disabled Test")
    void testDisabled() {
        Account accountOne = Account.builder().person(PERSON_NAME).balance(BALANCE).build();
        Account accountTwo = Account.builder().person(PERSON_NAME_TWO).balance(BALANCE).build();
    }

    @Test
    @DisplayName("Disabled Test")
    @Disabled
    void testFailExample() {
        fail();// this method make the test fail -> fail()
    }

    @BeforeAll // this method will be executed at the begining of everything
    public static void init(){
        System.out.println("first method executed");
    }

    @AfterAll // this method will be executed at the begining of everything
    public static void finalExecution(){
        System.out.println("last method to be executed");
    }

    @BeforeEach // this method will be executed at the begining of each method
    public void initEachExecution(){
        //you can initialize each account here before each test case.
        System.out.println("first execution before each method");
    }

    @AfterEach // this method will be executed at the final of each method
    public void afterEachExecution(){
        System.out.println("last method after each execution");
    }
}