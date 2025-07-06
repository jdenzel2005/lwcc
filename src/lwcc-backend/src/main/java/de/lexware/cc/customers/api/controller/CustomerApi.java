package de.lexware.cc.customers.api.controller;

import de.lexware.cc.customers.api.dto.CustomerDto;
import de.lexware.cc.customers.api.dto.ListCustomerDto;
import de.lexware.cc.shared.api.LwccApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/customers")
public interface CustomerApi extends LwccApi {
    @Operation(summary = "Get customer by technical id")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Get customer by technical id",
            content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CustomerDto.class)
                )
            }
        )
    })
    @GetMapping(
        path = "/{id}",
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    ResponseEntity<CustomerDto> getCustomerById(
        @PathVariable String id
    );

    @Operation(summary = "Get empty new customer")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returned a new customer instance",
            content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CustomerDto.class)
                )
            }
        )
    })
    @GetMapping
    ResponseEntity<CustomerDto> newCustomer();

    @Operation(summary = "Create a new customer")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Created a new customer",
            content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CustomerDto.class)
                )
            }
        )
    })
    @PostMapping(
        consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    ResponseEntity<CustomerDto> createCustomer(CustomerDto customer);

    @Operation(summary = "Update a customer")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Updated a customer",
            content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CustomerDto.class)
                )
            }
        )
    })
    @PutMapping(
        consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    ResponseEntity<CustomerDto> updateCustomer(CustomerDto customer);

    @Operation(summary = "List customers")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Retrieved a list of customers",
            content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ListCustomerDto.class)
                )
            }
        )
    })
    @GetMapping(
        path = "/list",
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    ResponseEntity<ListCustomerDto> listCustomers(
        @RequestParam(name = "first", required = false, defaultValue = "0")
        Integer first,
        @RequestParam(name = "rows", required = false, defaultValue = "10")
        Integer rows,
        @RequestParam(name = "sort", required = false, defaultValue = "id")
        String sort,
        @RequestParam(name = "direction", required = false, defaultValue = "ASC")
        Sort.Direction direction
    );
}
