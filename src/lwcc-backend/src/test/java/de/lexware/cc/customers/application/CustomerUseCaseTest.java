package de.lexware.cc.customers.application;

import de.lexware.cc.customers.PostgresJpaTest;
import de.lexware.cc.customers.TestHelpers;
import de.lexware.cc.customers.UseCaseTestConfiguration;
import de.lexware.cc.customers.application.usecases.EditCustomerUseCase;
import de.lexware.cc.customers.application.usecases.ListCustomersUseCase;
import de.lexware.cc.customers.domain.model.Customer;
import de.lexware.cc.shared.domain.ListPage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Sort;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@PostgresJpaTest
@Import(UseCaseTestConfiguration.class)
public class CustomerUseCaseTest {

    private final EditCustomerUseCase editCustomerUseCase;
    private final ListCustomersUseCase listCustomersUseCase;

    @Autowired
    public CustomerUseCaseTest(
        EditCustomerUseCase editCustomerUseCase,
        ListCustomersUseCase listCustomersUseCase
    ) {
        this.editCustomerUseCase = editCustomerUseCase;
        this.listCustomersUseCase = listCustomersUseCase;
    }

    @Test
    public void edit_use_case_update_action_works() {
        // given
        Customer customer = this.editCustomerUseCase.saveCustomer(
            TestHelpers.withName("Jesse", "Pinkmann")
        );

        // when
        Customer savedCustomer = this.editCustomerUseCase.getCustomer(customer.getId());
        Customer updatedCustomer = this.editCustomerUseCase.saveCustomer(savedCustomer.withName("Jesse", "Pinkman"));

        // then
        assertThat(updatedCustomer.lastname(), is("Pinkman"));
    }

    @Test
    public void use_case_list_action_works() {
        // given
        Stream.of(
            TestHelpers.withName("Firstname1", "Lastname1"),
            TestHelpers.withName("Firstname2", "Lastname2"),
            TestHelpers.withName("Firstname3", "Lastname3"),
            TestHelpers.withName("Firstname4", "Lastname4"),
            TestHelpers.withName("Firstname5", "Lastname5")
        ).forEach(this.editCustomerUseCase::saveCustomer);

        // when
        ListPage<Customer> allData = this.listCustomersUseCase.listCustomers(0L, 5L, "id", Sort.Direction.ASC);
        ListPage<Customer> firstPage = this.listCustomersUseCase.listCustomers(0L, 2L, "id", Sort.Direction.ASC);
        ListPage<Customer> secondPage = this.listCustomersUseCase.listCustomers(2L, 2L, "id", Sort.Direction.ASC);

        // then
        assertThat(allData.content().size(), is(5));
        assertThat(allData.total(), is(5L));

        assertThat(firstPage.content().size(), is(2));
        assertThat(firstPage.total(), is(5L));

        assertThat(secondPage.content().size(), is(2));
        assertThat(secondPage.total(), is(5L));
    }
}
