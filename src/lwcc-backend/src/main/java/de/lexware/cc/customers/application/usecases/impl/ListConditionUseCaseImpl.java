package de.lexware.cc.customers.application.usecases.impl;

import de.lexware.cc.customers.application.usecases.ListCustomersUseCase;
import de.lexware.cc.customers.domain.model.Customer;
import de.lexware.cc.customers.domain.repository.CustomerRepository;
import de.lexware.cc.shared.domain.ListPage;
import de.lexware.cc.shared.domain.UseCase;

import org.springframework.data.domain.Sort;

@UseCase
public class ListConditionUseCaseImpl implements ListCustomersUseCase {

    private final CustomerRepository customerRepository;

    public ListConditionUseCaseImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public ListPage<Customer> listCustomers(long first, long rows, String sort, Sort.Direction direction) {
        return this.customerRepository.list(first, rows, sort, direction);
    }
}
