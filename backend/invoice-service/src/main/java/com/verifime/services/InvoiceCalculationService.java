package com.verifime.services;

import com.verifime.dto.Invoice;
import com.verifime.dto.InvoiceLine;
import com.verifime.dto.InvoiceRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import java.math.BigDecimal;
import org.jboss.logging.Logger;

@ApplicationScoped
public class InvoiceCalculationService {

    @Inject
    CurrencyConversionService currencyConversionService;

    private static final Logger LOG =
            Logger.getLogger(InvoiceCalculationService.class);

    public String calculateTotal(InvoiceRequest request) {

        validate(request);

        Invoice invoice = request.invoice;

        BigDecimal total = BigDecimal.ZERO;

        for (InvoiceLine line : invoice.lines) {

            LOG.infof(
                    "Processing line: description=%s, amount=%s, currency=%s",
                    line.description,
                    line.amount,
                    line.currency);

            BigDecimal convertedAmount =
                    currencyConversionService.convert(
                            line.amount,
                            line.currency,
                            invoice.currency,
                            invoice.date);

            total = total.add(convertedAmount);
        }

        LOG.infof("Invoice total=%s", total);
        return total.toString();
    }

    private void validate(InvoiceRequest request) {

        if (request == null ||
                request.invoice == null ||
                request.invoice.lines == null) {
            throw new BadRequestException("Invalid invoice request");
        }
    }
}
