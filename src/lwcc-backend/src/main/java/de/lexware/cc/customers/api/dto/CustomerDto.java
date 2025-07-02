package de.lexware.cc.customers.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import de.lexware.cc.customers.domain.model.Country;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record CustomerDto(
    UUID id,
    LocalDateTime createdDate,
    LocalDateTime lastModifiedDate,
    @NotEmpty
    @Size(max = 255)
    String firstname,
    @NotEmpty
    @Size(max = 255)
    String lastname,
    @Size(max = 100)
    String infoText,
    String vatId,
    String street,
    String houseNumber,
    String zip,
    String city,
    Country country
) {
}
