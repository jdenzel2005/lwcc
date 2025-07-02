package de.lexware.cc.customers.application;

import de.lexware.cc.customers.application.usecases.EditCustomerUseCase;
import de.lexware.cc.customers.application.usecases.ListCustomersUseCase;
import de.lexware.cc.customers.application.usecases.impl.EditCustomerUseCaseImpl;
import de.lexware.cc.customers.application.usecases.impl.ListConditionUseCaseImpl;
import de.lexware.cc.customers.domain.repository.CustomerRepository;
import de.lexware.cc.customers.domain.validation.VatIdValidator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationUseCasesConfiguration {

    @Bean
    public EditCustomerUseCase editCustomerUseCase(
        CustomerRepository customerRepository,
        VatIdValidator vatIdValidator
    ) {
        return new EditCustomerUseCaseImpl(customerRepository, vatIdValidator);
    }

    @Bean
    public ListCustomersUseCase listCustomersUseCase(CustomerRepository customerRepository) {
        return new ListConditionUseCaseImpl(customerRepository);
    }
}
