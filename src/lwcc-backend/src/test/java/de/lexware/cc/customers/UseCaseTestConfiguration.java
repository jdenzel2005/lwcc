package de.lexware.cc.customers;

import de.lexware.cc.customers.application.ApplicationUseCasesConfiguration;
import de.lexware.cc.customers.application.validation.DefaultVatIdValidator;
import de.lexware.cc.customers.domain.validation.VatIdValidator;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import({InfrastructureTestConfiguration.class, ApplicationUseCasesConfiguration.class})
public class UseCaseTestConfiguration {

    @Bean
    public VatIdValidator vatIdValidator() {
        return new DefaultVatIdValidator();
    }
}
