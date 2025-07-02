package de.lexware.cc.customers.domain;

import de.lexware.cc.customers.ClearDatabase;
import de.lexware.cc.customers.InfrastructureTestConfiguration;
import de.lexware.cc.customers.PostgresJpaTest;
import de.lexware.cc.customers.TestHelpers;
import de.lexware.cc.customers.domain.model.Country;
import de.lexware.cc.customers.domain.model.Customer;
import de.lexware.cc.customers.domain.repository.CustomerRepository;
import de.lexware.cc.shared.domain.ListPage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Sort;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@PostgresJpaTest
@Import(InfrastructureTestConfiguration.class)
@ClearDatabase
public class CustomerRepositoryTest {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerRepositoryTest(
        CustomerRepository customerRepository
    ) {
        this.customerRepository = customerRepository;
            }

    @Test
    public void save_customer_works() {
        // given
        Customer customer = TestHelpers.newCustomer();

        Customer savedCustomer = this.customerRepository.save(customer);

        // when
        Customer loadedCustomer = this.customerRepository.get(savedCustomer.getId());

        // then
        assertThat(loadedCustomer.isPersisted(), equalTo(true));
        assertThat(loadedCustomer.firstname(), equalTo("Walter"));
        assertThat(loadedCustomer.lastname(), equalTo("White"));
        assertThat(loadedCustomer.infoText(), equalTo("Drug Dealer"));
        assertThat(loadedCustomer.vatId(), equalTo("GB999999973"));
        assertThat(loadedCustomer.street(), equalTo("Main Street"));
        assertThat(loadedCustomer.houseNumber(), equalTo("1"));
        assertThat(loadedCustomer.city(), equalTo("City"));
        assertThat(loadedCustomer.zip(), equalTo("88811"));
        assertThat(loadedCustomer.country(), equalTo(Country.GB));
    }

    @Test
    public void loading_paged_customers_works() {
        // given
        Stream.of(
            TestHelpers.withName("Firstname1", "Lastname1"),
            TestHelpers.withName("Firstname2", "Lastname2"),
            TestHelpers.withName("Firstname3", "Lastname3"),
            TestHelpers.withName("Firstname4", "Lastname4"),
            TestHelpers.withName("Firstname5", "Lastname5")
        ).forEach(this.customerRepository::save);

        // when
        ListPage<Customer> allData = this.customerRepository.list(0L, 5L, "id", Sort.Direction.ASC);
        ListPage<Customer> firstPage = this.customerRepository.list(0L, 2L, "id", Sort.Direction.ASC);
        ListPage<Customer> secondPage = this.customerRepository.list(2L, 2L, "id", Sort.Direction.ASC);

        // then
        assertThat(allData.content().size(), is(5));
        assertThat(allData.total(), is(5L));

        assertThat(firstPage.content().size(), is(2));
        assertThat(firstPage.total(), is(5L));

        assertThat(secondPage.content().size(), is(2));
        assertThat(secondPage.total(), is(5L));
    }
}
