package de.lexware.cc.customers.infrastructure.persistence;

import de.lexware.cc.customers.domain.model.Country;
import de.lexware.cc.shared.infrastructure.persistence.DomainEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(
    name = "lwcc_customer"
)

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerEntity extends DomainEntity {
    @Column
    private String firstname;
    @Column
    private String lastname;
    @Column(length = 100)
    private String infoText;
    @Column
    private String vatId;
    @Column
    private String street;
    @Column
    private String houseNumber;
    @Column
    private String zip;
    @Column
    private String city;
    @Column
    @Enumerated(EnumType.STRING)
    private Country country;
}
