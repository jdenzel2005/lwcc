package de.lexware.cc.customers.application.usecases;

import de.lexware.cc.customers.domain.exception.InvalidCustomerException;
import de.lexware.cc.customers.domain.model.Customer;

import java.util.UUID;

public interface EditCustomerUseCase {

    Customer newCustomer();

    Customer getCustomer(UUID id);

    Customer saveCustomer(Customer customer) throws InvalidCustomerException;
}
