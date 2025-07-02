package de.lexware.cc.customers.application;

import de.lexware.cc.customers.PostgresJpaTest;
import de.lexware.cc.customers.UseCaseTestConfiguration;
import de.lexware.cc.customers.domain.model.Country;
import de.lexware.cc.customers.domain.validation.VatIdValidator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@PostgresJpaTest
@Import(UseCaseTestConfiguration.class)
public class VatIdValidationTest {

    private final VatIdValidator vatIdValidator;

    @Autowired
    public VatIdValidationTest(
        VatIdValidator vatIdValidator
    ) {
        this.vatIdValidator = vatIdValidator;
    }

    @Test
    public void de_vat_id_validation_works() {
        // given
        String validId = "DE136695976";
        String invalidId = "DE13669597";

        // when
        boolean validTestResult = vatIdValidator.isValidVatId(validId, Country.DE);
        boolean invalidTestResult = vatIdValidator.isValidVatId(invalidId, Country.DE);

        // then
        assertThat(validTestResult, is(true));
        assertThat(invalidTestResult, is(false));
    }

    @Test
    public void at_vat_id_validation_works() {
        // given
        String validId = "ATU99999999";
        String invalidId = "ATU9999999";

        // when
        boolean validTestResult = vatIdValidator.isValidVatId(validId, Country.AT);
        boolean invalidTestResult = vatIdValidator.isValidVatId(invalidId, Country.AT);

        // then
        assertThat(validTestResult, is(true));
        assertThat(invalidTestResult, is(false));
    }

    @Test
    public void fr_vat_id_validation_works() {
        // given
        String validId = "FR40303265045";
        String invalidId = "FR4030326504";

        // when
        boolean validTestResult = vatIdValidator.isValidVatId(validId, Country.FR);
        boolean invalidTestResult = vatIdValidator.isValidVatId(invalidId, Country.FR);

        // then
        assertThat(validTestResult, is(true));
        assertThat(invalidTestResult, is(false));
    }

    @Test
    public void gb_vat_id_validation_works() {
        // given
        String validId = "GB999999973";
        String invalidId = "GBY9999999711";

        // when
        boolean validTestResult = vatIdValidator.isValidVatId(validId, Country.GB);
        boolean invalidTestResult = vatIdValidator.isValidVatId(invalidId, Country.GB);

        // then
        assertThat(validTestResult, is(true));
        assertThat(invalidTestResult, is(false));
    }

    @Test
    public void dk_vat_id_validation_works() {
        // given
        String validId = "DK13585628";
        String invalidId = "DKX13585628";

        // when
        boolean validTestResult = vatIdValidator.isValidVatId(validId, Country.DK);
        boolean invalidTestResult = vatIdValidator.isValidVatId(invalidId, Country.DK);

        // then
        assertThat(validTestResult, is(true));
        assertThat(invalidTestResult, is(false));
    }

    @Test
    public void nl_vat_id_validation_works() {
        // given
        String validId = "NL123456789B01";
        String invalidId = "N123456789B0";

        // when
        boolean validTestResult = vatIdValidator.isValidVatId(validId, Country.NL);
        boolean invalidTestResult = vatIdValidator.isValidVatId(invalidId, Country.NL);

        // then
        assertThat(validTestResult, is(true));
        assertThat(invalidTestResult, is(false));
    }
}
