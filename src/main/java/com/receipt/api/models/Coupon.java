package com.receipt.api.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import java.math.BigDecimal;


@EqualsAndHashCode(callSuper = false)
@Data
@Entity
public class Coupon extends AbstractEntity {

    private String couponName;

    private Integer appliedSku;

    private BigDecimal discountPrice;
}