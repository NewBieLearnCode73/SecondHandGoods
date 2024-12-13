package com.dinhchieu.demo.handle;

public class AccountStateNotFoundException extends RuntimeException {
    public AccountStateNotFoundException(String message) {
        super(message);
    }
}
