package com.verifime.services;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CurrencyConversionServiceTest {

    @Test
    void shouldReturnSameAmountForSameCurrency() {

        CurrencyConversionService service =
                new CurrencyConversionService();

        BigDecimal result =
                service.convert(
                        new BigDecimal("100.00"),
                        "NZD",
                        "NZD",
                        "2020-07-07");

        assertEquals(
                new BigDecimal("100.00"),
                result);
    }
}
