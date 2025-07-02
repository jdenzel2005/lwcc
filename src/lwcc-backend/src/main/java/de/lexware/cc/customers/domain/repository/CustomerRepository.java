package de.lexware.cc.customers.domain.repository;

import de.lexware.cc.customers.domain.exception.CustomerException;
import de.lexware.cc.customers.domain.exception.CustomerNotFoundException;
import de.lexware.cc.customers.domain.model.Customer;
import de.lexware.cc.shared.domain.ListPage;

import org.springframework.data.domain.Sort;

import java.util.UUID;

public interface CustomerRepository {
    Customer get(UUID id) throws CustomerNotFoundException;

    Customer save(Customer customer) throws CustomerNotFoundException;

    ListPage<Customer> list(
        long offset,
        long pageSize,
        String sort,
        Sort.Direction direction
    );
}
