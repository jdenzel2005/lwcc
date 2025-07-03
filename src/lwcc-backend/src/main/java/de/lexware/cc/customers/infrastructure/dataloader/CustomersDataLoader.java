package de.lexware.cc.customers.infrastructure.dataloader;

import de.lexware.cc.customers.domain.model.Country;
import de.lexware.cc.customers.domain.model.Customer;
import de.lexware.cc.customers.infrastructure.persistence.CustomerMapper;
import de.lexware.cc.customers.infrastructure.persistence.JpaCustomerRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Component
public class CustomersDataLoader implements CommandLineRunner {

    private final JpaCustomerRepository jpaCustomerRepository;
    private final CustomerMapper customerMapper;

    public CustomersDataLoader(JpaCustomerRepository jpaCustomerRepository, CustomerMapper customerMapper) {
        this.jpaCustomerRepository = jpaCustomerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        Customer customer = new Customer("firstname1",
                                         "lastname2",
                                         "infoText",
                                         "DE136695976",
                                         "Street",
                                         "10",
                                         "80111",
                                         "Ulm",
                                         Country.DE);

        jpaCustomerRepository.deleteAll();
        IntStream.range(0, 20)
                 .forEach(i -> {
                     jpaCustomerRepository.save(customerMapper.toEntity(customer.withName("Firstname" + i,
                                                                                          "Lastname" + i)));
                 });

    }
}
