package com.verifime.dto;

import java.util.List;

public class Invoice {
    public String currency;
    public String date;
    public List<InvoiceLine> lines;
}
