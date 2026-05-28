package com.verifime.services;

import com.verifime.dto.Invoice;
import com.verifime.dto.InvoiceLine;
import com.verifime.dto.InvoiceRequest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InvoiceCalculationServiceTest {

    @Test
    void shouldCalculateInvoiceTotal() {

        CurrencyConversionService conversionService =
                mock(CurrencyConversionService.class);

        InvoiceCalculationService service =
                new InvoiceCalculationService(conversionService);

        InvoiceLine line1 = new InvoiceLine();
        line1.amount = new BigDecimal("100.00");
        line1.currency = "USD";

        InvoiceLine line2 = new InvoiceLine();
        line2.amount = new BigDecimal("50.00");
        line2.currency = "AUD";

        Invoice invoice = new Invoice();
        invoice.currency = "NZD";
        invoice.date = "2020-07-07";
        invoice.lines = List.of(line1, line2);

        InvoiceRequest request = new InvoiceRequest();
        request.invoice = invoice;

        when(conversionService.convert(
                any(),
                any(),
                any(),
                any()))
                .thenReturn(new BigDecimal("100.00"));

        String total = service.calculateTotal(request);

        assertEquals("200.00", total);
    }
}
