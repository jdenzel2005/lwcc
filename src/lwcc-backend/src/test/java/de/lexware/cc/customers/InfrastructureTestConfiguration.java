package de.lexware.cc.customers;

import de.lexware.cc.config.jackson.JacksonConfiguration;
import de.lexware.cc.customers.domain.repository.CustomerRepository;
import de.lexware.cc.customers.infrastructure.persistence.CustomerMapper;
import de.lexware.cc.customers.infrastructure.persistence.CustomerRepositoryService;
import de.lexware.cc.customers.infrastructure.persistence.JpaCustomerRepository;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import jakarta.persistence.EntityManager;

@TestConfiguration
@Import(JacksonConfiguration.class)
public class InfrastructureTestConfiguration {

    private final JpaCustomerRepository jpaCustomerRepository;
    private final EntityManager em;

    public InfrastructureTestConfiguration(
        JpaCustomerRepository jpaCustomerRepository,
        EntityManager em
    ) {
        this.jpaCustomerRepository = jpaCustomerRepository;
        this.em = em;
    }

    @Bean
    public CustomerMapper customerMapper() {
        return new CustomerMapper();
    }

    @Bean
    public CustomerRepository customerRepository() {
        return new CustomerRepositoryService(jpaCustomerRepository, em, customerMapper());
    }
}
