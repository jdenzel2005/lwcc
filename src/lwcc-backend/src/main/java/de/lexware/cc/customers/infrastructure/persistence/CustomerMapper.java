package de.lexware.cc.customers.infrastructure.persistence;

import de.lexware.cc.customers.domain.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer toDomain(CustomerEntity customerEntity) {
        return new Customer(
            customerEntity.getId(),
            customerEntity.getCreatedDate(),
            customerEntity.getLastModifiedDate(),
            customerEntity.getFirstname(),
            customerEntity.getLastname(),
            customerEntity.getInfoText(),
            customerEntity.getVatId(),
            customerEntity.getStreet(),
            customerEntity.getHouseNumber(),
            customerEntity.getZip(),
            customerEntity.getCity(),
            customerEntity.getCountry()
        );
    }

    public CustomerEntity toEntity(Customer customer) {
        return new CustomerEntity(
            customer.firstname(),
            customer.lastname(),
            customer.infoText(),
            customer.vatId(),
            customer.street(),
            customer.houseNumber(),
            customer.zip(),
            customer.city(),
            customer.country()
        );
    }

    public CustomerEntity updateEntity(Customer source, CustomerEntity target) {
        target.setFirstname(source.firstname());
        target.setLastname(source.lastname());
        target.setInfoText(source.infoText());
        target.setVatId(source.vatId());
        target.setStreet(source.street());
        target.setHouseNumber(source.houseNumber());
        target.setZip(source.zip());
        target.setCity(source.city());
        target.setCountry(source.country());
        return target;
    }
}
