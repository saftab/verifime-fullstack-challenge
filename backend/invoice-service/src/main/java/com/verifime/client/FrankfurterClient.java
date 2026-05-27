package com.verifime.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import com.verifime.dto.ExchangeRateResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;


@Path("/")
@RegisterRestClient(configKey = "frankfurter-api")
public interface FrankfurterClient {
    @GET
    @Path("/{date}")
    ExchangeRateResponse getRate(
            @PathParam("date") String date,
            @QueryParam("from") String from,
            @QueryParam("to") String to
    );
}
