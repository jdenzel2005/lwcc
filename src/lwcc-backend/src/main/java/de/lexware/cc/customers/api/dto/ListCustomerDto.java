package de.lexware.cc.customers.api.dto;

import java.util.List;

public record ListCustomerDto(
    List<CustomerDto> content,
    long page,
    long size,
    long total
) {
}
