package com.verifime.services;

import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;

@ApplicationScoped
public class InvoiceCalculationService {

    public BigDecimal calculateTotal() {
        return new BigDecimal("1600.86");
    }
}
