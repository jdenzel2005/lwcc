package de.lexware.cc.customers.api.controller;

import de.lexware.cc.customers.api.dto.CustomerDto;
import de.lexware.cc.customers.domain.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerResponseMapper {

    public Customer toDomain(CustomerDto source) {
        return new Customer(
            source.id(),
            source.createdDate(),
            source.lastModifiedDate(),
            source.firstname(),
            source.lastname(),
            source.infoText(),
            source.vatId(),
            source.street(),
            source.houseNumber(),
            source.zip(),
            source.city(),
            source.country()
        );
    }

    public CustomerDto toDto(Customer source) {
        return new CustomerDto(
            source.id(),
            source.createdDate(),
            source.lastModifiedDate(),
            source.firstname(),
            source.lastname(),
            source.infoText(),
            source.vatId(),
            source.street(),
            source.houseNumber(),
            source.zip(),
            source.city(),
            source.country()
        );
    }
}
