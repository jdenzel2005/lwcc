package de.lexware.cc.customers.api;

import de.lexware.cc.customers.LwccIntegrationTest;
import de.lexware.cc.customers.TestHelpers;
import de.lexware.cc.customers.api.controller.CustomerController;
import de.lexware.cc.customers.api.controller.CustomerResponseMapper;
import de.lexware.cc.customers.api.dto.CustomerDto;
import de.lexware.cc.customers.api.dto.ListCustomerDto;
import de.lexware.cc.customers.application.usecases.EditCustomerUseCase;
import de.lexware.cc.customers.application.usecases.ListCustomersUseCase;
import de.lexware.cc.customers.domain.model.Customer;
import de.lexware.cc.customers.domain.repository.CustomerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.stream.Stream;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CustomerControllerTest extends LwccIntegrationTest {

    @Autowired
    public CustomerControllerTest(CustomerRepository customerRepository,
                                  ListCustomersUseCase listCustomersUseCase,
                                  EditCustomerUseCase editCustomerUseCase,
                                  CustomerResponseMapper customerResponseMapper) {
        super(customerRepository, listCustomersUseCase, editCustomerUseCase, customerResponseMapper);
    }

    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.mockMvc(MockMvcBuilders.standaloneSetup(new CustomerController(listCustomersUseCase,
                                                                                          editCustomerUseCase,
                                                                                          customerResponseMapper))
                                                  .build());
    }

    @Test
    public void customer_get_resource_works() {
        Customer customer = customerRepository.save(TestHelpers.newCustomer());
        RestAssuredMockMvc.given()
                          .when()
                          .get("/customers/{customerId}", customer.getId())
                          .then()
                          .statusCode(HttpStatus.OK.value())
                          .body("firstname", equalTo("Walter"));
    }

    @Test
    public void customer_creation_resource_works() {
        CustomerDto body = customerResponseMapper.toDto(TestHelpers.newCustomer());
        RestAssuredMockMvc.given()
                          .contentType(MediaType.APPLICATION_JSON_VALUE)
                          .body(body)
                          .when()
                          .post("/customers")
                          .then()
                          .statusCode(HttpStatus.CREATED.value())
                          .body("id", notNullValue());
    }

    @Test
    public void validation_on_customer_creation_resource_works() {
        CustomerDto invalidBody = customerResponseMapper.toDto(TestHelpers.withName(null, "Lastname"));
        RestAssuredMockMvc.given()
                          .contentType(MediaType.APPLICATION_JSON_VALUE)
                          .body(invalidBody)
                          .when()
                          .post("/customers")
                          .then()
                          .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void customer_update_resource_works() {
        Customer customer = customerRepository.save(TestHelpers.newCustomer());
        CustomerDto body = customerResponseMapper.toDto(customer.withName("Jesse", "Pinkman"));
        RestAssuredMockMvc.given()
                          .contentType(MediaType.APPLICATION_JSON_VALUE)
                          .body(body)
                          .when()
                          .put("/customers")
                          .then()
                          .statusCode(HttpStatus.OK.value())
                          .body("firstname", equalTo("Jesse"));
    }

    @Test
    public void customers_list_resource_works() {
        Stream.of(
            TestHelpers.withName("Firstname1", "Lastname1"),
            TestHelpers.withName("Firstname2", "Lastname2"),
            TestHelpers.withName("Firstname3", "Lastname3"),
            TestHelpers.withName("Firstname4", "Lastname4"),
            TestHelpers.withName("Firstname5", "Lastname5")
        ).forEach(this.editCustomerUseCase::saveCustomer);

        MockMvcResponse response = RestAssuredMockMvc
            .given()
            .when()
            .get("/customers/list?first=0");

        response.then().statusCode(HttpStatus.OK.value());

        ListCustomerDto listDto = response.getBody().as(ListCustomerDto.class);

        assertThat(listDto, notNullValue());
        assertThat(listDto.total(), equalTo(5L));
    }

}
