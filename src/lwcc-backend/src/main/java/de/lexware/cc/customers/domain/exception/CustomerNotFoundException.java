package de.lexware.cc.customers.domain.exception;

public class CustomerNotFoundException extends CustomerException {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
