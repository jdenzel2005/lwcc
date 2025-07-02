package de.lexware.cc.customers.domain.validation;

import de.lexware.cc.customers.domain.model.Country;

public interface VatIdValidator {

    boolean isValidVatId(String vatId, Country country);
}
