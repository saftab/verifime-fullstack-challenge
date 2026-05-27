package com.verifime.services;

import com.verifime.client.FrankfurterClient;
import com.verifime.dto.ExchangeRateResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.jboss.logging.Logger;

@ApplicationScoped
public class CurrencyConversionService  {

    private static final Logger LOG =
            Logger.getLogger(CurrencyConversionService.class);

    @Inject
    @RestClient
    FrankfurterClient frankfurterClient;
    public BigDecimal convert(
            BigDecimal amount,
            String fromCurrency,
            String toCurrency,
            String date) {

        if (fromCurrency.equalsIgnoreCase(toCurrency)) {
            LOG.infof(
                    "Skipping conversion. Same currency=%s",
                    fromCurrency);
            return amount.setScale(2, RoundingMode.HALF_UP);
        }

        LOG.infof(
                "Converting %s from %s to %s on %s",
                amount,
                fromCurrency,
                toCurrency,
                date);

        ExchangeRateResponse response =
                frankfurterClient.getRate(
                        date,
                        fromCurrency,
                        toCurrency);



        BigDecimal rate =
                response.rates.get(toCurrency).setScale(4, RoundingMode.HALF_UP);
                response.rates.get(toCurrency)

                        .setScale(4, RoundingMode.HALF_UP);
        LOG.infof("Exchange rate=%s", rate);

        BigDecimal convertedAmount =
                amount.multiply(rate)
                        .setScale(2, RoundingMode.HALF_UP);

        return convertedAmount;
    }
}
