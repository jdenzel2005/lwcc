package de.lexware.cc.customers.api.controller;

import de.lexware.cc.customers.api.dto.CustomerDto;
import de.lexware.cc.customers.api.dto.ListCustomerDto;
import de.lexware.cc.customers.application.usecases.EditCustomerUseCase;
import de.lexware.cc.customers.application.usecases.ListCustomersUseCase;
import de.lexware.cc.customers.domain.model.Customer;
import de.lexware.cc.shared.domain.ListPage;
import jakarta.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class CustomerController implements CustomerApi {

    private final ListCustomersUseCase listCustomersUseCase;
    private final EditCustomerUseCase editCustomerUseCase;
    private final CustomerResponseMapper customerResponseMapper;

    public CustomerController(
        ListCustomersUseCase listCustomersUseCase,
        EditCustomerUseCase editCustomerUseCase,
        CustomerResponseMapper customerResponseMapper) {
        this.listCustomersUseCase = listCustomersUseCase;
        this.editCustomerUseCase = editCustomerUseCase;
        this.customerResponseMapper = customerResponseMapper;
    }

    @Override
    public ResponseEntity<CustomerDto> getCustomerById(String id) {
        CustomerDto response =
            customerResponseMapper.toDto(editCustomerUseCase.getCustomer(UUID.fromString(id)));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<CustomerDto> newCustomer() {
        CustomerDto response =
            customerResponseMapper.toDto(editCustomerUseCase.newCustomer());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<CustomerDto> createCustomer(
        @Valid @RequestBody CustomerDto customer
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(saveOrUpdate(customer));
    }

    @Override
    public ResponseEntity<CustomerDto> updateCustomer(
        @Valid @RequestBody CustomerDto customer
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(saveOrUpdate(customer));
    }

    @Override
    public ResponseEntity<ListCustomerDto> listCustomers(
        Integer first,
        Integer rows,
        String sort,
        Sort.Direction direction
    ) {
        PageRequest pageRequest = PageRequest.of(first, rows != null ? rows : 10);
        ListPage<Customer> list =
            listCustomersUseCase.listCustomers(
                pageRequest.getOffset(),
                pageRequest.getPageSize(),
                sort,
                direction
            );
        ListCustomerDto response = new ListCustomerDto(
            list.content().stream().map(customerResponseMapper::toDto).collect(Collectors.toList()),
            pageRequest.getPageNumber(),
            pageRequest.getPageSize(),
            list.total()
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    private CustomerDto saveOrUpdate(CustomerDto customer) {
        return customerResponseMapper.toDto(
            editCustomerUseCase.saveCustomer(
                customerResponseMapper.toDomain(customer)
            ));
    }
}
