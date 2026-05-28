package com.verifime.resource;

import com.verifime.dto.InvoiceRequest;
import com.verifime.services.InvoiceCalculationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.jboss.logging.Logger;

@Path("/invoice")
public class InvoiceResource {

    private static final Logger LOG =
            Logger.getLogger(InvoiceResource.class);

    private final InvoiceCalculationService invoiceCalculationService;

    @Inject
    public InvoiceResource(
            InvoiceCalculationService invoiceCalculationService) {
        this.invoiceCalculationService = invoiceCalculationService;
    }


    @POST
    @Path("/total")
     @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String calculateTotal(InvoiceRequest invoiceRequest) {
        if (invoiceRequest != null &&
                invoiceRequest.invoice != null &&
                invoiceRequest.invoice.lines != null) {

            LOG.infof(
                    "Received invoice request. Currency=%s, Date=%s, Lines=%d",
                    invoiceRequest.invoice.currency,
                    invoiceRequest.invoice.date,
                    invoiceRequest.invoice.lines.size());
        }
        return invoiceCalculationService.calculateTotal(invoiceRequest) + "\n";
    }
}
