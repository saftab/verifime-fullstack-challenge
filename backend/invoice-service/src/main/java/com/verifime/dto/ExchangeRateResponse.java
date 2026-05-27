package com.verifime.dto;

import java.math.BigDecimal;
import java.util.Map;

public class ExchangeRateResponse {

    public BigDecimal amount;
    public String base;
    public String date;
    public Map<String, BigDecimal> rates;
}
