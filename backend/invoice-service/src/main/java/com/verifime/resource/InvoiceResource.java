package com.verifime.resource;

import com.verifime.services.InvoiceCalculationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/invoice")
public class InvoiceResource {

    @Inject
    InvoiceCalculationService invoiceCalculationService;


    @POST
    @Path("/total")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String calculateTotal() {
        return invoiceCalculationService.calculateTotal().toString();
    }
}
