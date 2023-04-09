package com.receipt.api.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import java.math.BigDecimal;


@EqualsAndHashCode(callSuper = false)
@Data
@Entity
public class Item extends AbstractEntity {

    private String itemName;

    private Integer sku;

    private boolean isTaxable;

    private boolean ownBrand;

    private BigDecimal price;
}