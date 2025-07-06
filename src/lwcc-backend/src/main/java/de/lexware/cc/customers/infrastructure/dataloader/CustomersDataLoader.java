package de.lexware.cc.customers.infrastructure.dataloader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.lexware.cc.customers.domain.model.Customer;
import de.lexware.cc.customers.infrastructure.persistence.CustomerMapper;
import de.lexware.cc.customers.infrastructure.persistence.JpaCustomerRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class CustomersDataLoader implements CommandLineRunner {

    private final JpaCustomerRepository jpaCustomerRepository;
    private final CustomerMapper customerMapper;
    private final ObjectMapper objectMapper;

    public CustomersDataLoader(JpaCustomerRepository jpaCustomerRepository,
                               CustomerMapper customerMapper,
                               ObjectMapper objectMapper) {
        this.jpaCustomerRepository = jpaCustomerRepository;
        this.customerMapper = customerMapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        jpaCustomerRepository.deleteAll();
        loadCustomersFromClasspath().forEach(c -> jpaCustomerRepository.save(customerMapper.toEntity(c)));
    }

    private List<Customer> loadCustomersFromClasspath() throws IOException {
        try (InputStream is = getClass().getClassLoader()
                                        .getResourceAsStream("mockdata/customers.json")) {
            if (is == null) {
                throw new IOException("File not found: customers.json");
            }
            return objectMapper.readValue(is, new TypeReference<>() {
            });
        }
    }
}
