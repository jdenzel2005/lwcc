package de.lexware.cc.customers.infrastructure;

import de.lexware.cc.customers.InfrastructureTestConfiguration;
import de.lexware.cc.customers.PostgresJpaTest;
import de.lexware.cc.customers.TestHelpers;
import de.lexware.cc.customers.domain.model.Customer;
import de.lexware.cc.customers.infrastructure.persistence.CustomerEntity;
import de.lexware.cc.customers.infrastructure.persistence.CustomerMapper;
import de.lexware.cc.customers.infrastructure.persistence.JpaCustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@PostgresJpaTest
@Import(InfrastructureTestConfiguration.class)
public class JpaCustomerRepositoryTest {

    private final JpaCustomerRepository jpaCustomerRepository;
    private final CustomerMapper customerMapper;

    @Autowired
    public JpaCustomerRepositoryTest(
        JpaCustomerRepository jpaCustomerRepository,
        CustomerMapper customerMapper
    ) {
        this.jpaCustomerRepository = jpaCustomerRepository;
        this.customerMapper = customerMapper;
    }

    @Test
    public void customer_persistence_is_working() {
        Customer customer = TestHelpers.newCustomer();
        CustomerEntity customerEntity = jpaCustomerRepository.save(customerMapper.toEntity(customer));

        assertThat(
            customerEntity,
            allOf(
                hasProperty("id", notNullValue()),
                hasProperty("firstname", notNullValue())
            )
        );
    }
}
