package de.lexware.cc.customers.application.usecases.impl;

import de.lexware.cc.customers.application.usecases.EditCustomerUseCase;
import de.lexware.cc.customers.domain.exception.InvalidCustomerException;
import de.lexware.cc.customers.domain.model.Customer;
import de.lexware.cc.customers.domain.repository.CustomerRepository;
import de.lexware.cc.customers.domain.validation.VatIdValidator;
import de.lexware.cc.shared.domain.UseCase;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
public class EditCustomerUseCaseImpl implements EditCustomerUseCase {

    private final CustomerRepository customerRepository;
    private final VatIdValidator vatIdValidator;

    public EditCustomerUseCaseImpl(
        CustomerRepository customerRepository,
        VatIdValidator vatIdValidator
    ) {
        this.customerRepository = customerRepository;
        this.vatIdValidator = vatIdValidator;
    }

    @Override
    public Customer newCustomer() {
        return new Customer();
    }

    @Override
    public Customer getCustomer(UUID id) {
        return this.customerRepository.get(id);
    }

    @Override
    @Transactional
    public Customer saveCustomer(Customer customer) {
        if (!vatIdValidator.isValidVatId(customer.vatId(), customer.country())) {
            throw new InvalidCustomerException("VAT ID is invalid");
        }

        return this.customerRepository.save(customer);
    }
}
