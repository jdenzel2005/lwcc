package de.lexware.cc.customers;

import de.lexware.cc.customers.domain.model.Country;
import de.lexware.cc.customers.domain.model.Customer;

public class TestHelpers {

    private TestHelpers() {
    }

    public static Customer newCustomer() {
        return withName("Walter", "White");
    }

    public static Customer withName(String firstname, String lastname) {
        return new Customer(
            firstname,
            lastname,
            "Drug Dealer",
            "GB999999973",
            "Main Street",
            "1",
            "88811",
            "City",
            Country.GB
        );
    }
}
