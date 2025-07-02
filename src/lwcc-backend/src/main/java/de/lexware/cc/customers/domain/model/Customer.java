package de.lexware.cc.customers.domain.model;

import de.lexware.cc.shared.domain.DomainModel;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public record Customer(UUID id, LocalDateTime createdDate, LocalDateTime lastModifiedDate, String firstname,
                       String lastname, String infoText, String vatId, String street, String houseNumber, String zip,
                       String city, Country country) implements DomainModel, Serializable {
    public boolean isPersisted() {
        return this.id != null;
    }

    public Customer() {
        this(null, LocalDateTime.now(), LocalDateTime.now(), "", "", "", "", "", "", "", "", Country.DE);
    }

    public Customer(String firstname,
                    String lastname,
                    String infoText,
                    String vatId,
                    String street,
                    String houseNumber,
                    String zip,
                    String city,
                    Country country) {
        this(null,
             LocalDateTime.now(),
             LocalDateTime.now(),
             firstname,
             lastname,
             infoText,
             vatId,
             street,
             houseNumber,
             zip,
             city,
             country);
    }

    public Customer withName(String firstname, String lastname) {
        return new Customer(this.id,
                            this.createdDate,
                            this.lastModifiedDate,
                            firstname,
                            lastname,
                            this.infoText,
                            this.vatId,
                            this.street,
                            this.houseNumber,
                            this.zip,
                            this.city,
                            this.country);
    }

    @Override
    public UUID getId() {
        return id;
    }
}
