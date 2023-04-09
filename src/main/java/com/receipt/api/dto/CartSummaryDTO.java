package com.receipt.api.dto;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class CartSummaryDTO {
    private Map<String, String> receipt;

    public CartSummaryDTO() {
        receipt = new LinkedHashMap<>();
    }

    public Map<String, String> getReceipt() {
        return receipt;
    }

    public void setReceipt(Map<String, String> receipt) {
        this.receipt = receipt;
    }

    public void put(String label, BigDecimal value) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        currencyFormat.setMaximumFractionDigits(2);
        currencyFormat.setMinimumFractionDigits(2);
        String formattedValue = currencyFormat.format(value);

        receipt.put(label, formattedValue);
    }

    public String get(String label) {
        return receipt.get(label);
    }
}

