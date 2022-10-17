package org.camamonte.junit5.example.exception;

public class InsuficientMoneyAvaliable extends RuntimeException {

    public InsuficientMoneyAvaliable(String message) {
        super(message);
    }
}
