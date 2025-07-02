package de.lexware.cc.customers.application.usecases;

import de.lexware.cc.customers.domain.model.Customer;
import de.lexware.cc.shared.domain.ListPage;

import org.springframework.data.domain.Sort;

public interface ListCustomersUseCase {

    ListPage<Customer> listCustomers(
        long first,
        long rows,
        String sort,
        Sort.Direction direction
    );
}
