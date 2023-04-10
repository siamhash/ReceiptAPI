package com.receipt.api.utils;

import java.math.BigDecimal;

public class NumberOperations {

    public static BigDecimal formatData(BigDecimal value){
        return value.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }
}
