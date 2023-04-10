package com.receipt.api.controllers.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartSummaryDTO {

    private BigDecimal subTotalBeforeDiscount;
    private BigDecimal discountTotal;
    private BigDecimal subtotalAfterDiscount;
    private BigDecimal taxableSubtotalAfterDiscount;
    private BigDecimal totalTax;
    private BigDecimal grandTotal;

}

