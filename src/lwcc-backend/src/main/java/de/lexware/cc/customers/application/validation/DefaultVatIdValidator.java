package de.lexware.cc.customers.application.validation;

import de.lexware.cc.customers.domain.model.Country;
import de.lexware.cc.customers.domain.validation.VatIdValidator;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DefaultVatIdValidator implements VatIdValidator {

    static private final Map<Country, String> COUNTRY_2_VAT_ID_REGEX = Map.of(Country.DE,
                                                                              "^DE[0-9]{9}$",
                                                                              Country.DK,
                                                                              "^DK[0-9]{8}$",
                                                                              Country.AT,
                                                                              "^ATU[0-9]{8}$",
                                                                              Country.GB,
                                                                              "^GB[0-9]{9}$",
                                                                              Country.FR,
                                                                              "^FR[0-9A-Z]{2}[0-9]{9}$",
                                                                              Country.NL,
                                                                              "^NL[0-9]{9}B[0-9]{2}$");

    @Override
    public boolean isValidVatId(String vatId, Country country) {
        return vatId == null || vatId.matches(COUNTRY_2_VAT_ID_REGEX.get(country));
    }
}
