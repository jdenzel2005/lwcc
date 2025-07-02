package de.lexware.cc.customers;

import de.lexware.cc.customers.api.controller.CustomerResponseMapper;
import de.lexware.cc.customers.application.usecases.EditCustomerUseCase;
import de.lexware.cc.customers.application.usecases.ListCustomersUseCase;
import de.lexware.cc.customers.domain.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(DatabaseTestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ClearDatabase
public class LwccIntegrationTest {

    protected CustomerRepository customerRepository;
    protected ListCustomersUseCase listCustomersUseCase;
    protected EditCustomerUseCase editCustomerUseCase;
    protected CustomerResponseMapper customerResponseMapper;

    @Autowired
    public LwccIntegrationTest(
        CustomerRepository customerRepository,
        ListCustomersUseCase listCustomersUseCase,
        EditCustomerUseCase editCustomerUseCase,
        CustomerResponseMapper customerResponseMapper
    ) {
        this.customerRepository = customerRepository;
        this.listCustomersUseCase = listCustomersUseCase;
        this.editCustomerUseCase = editCustomerUseCase;
        this.customerResponseMapper = customerResponseMapper;
    }
}
