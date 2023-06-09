package com.receipt.api.service;


import com.receipt.api.controllers.dto.CartSummaryDTO;
import com.receipt.api.data.operations.CartDB;
import com.receipt.api.data.operations.CouponDB;
import com.receipt.api.models.Coupon;
import com.receipt.api.models.Item;
import com.receipt.api.utils.NumberOperations;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;


@Service
public class CartService {

    @Autowired
    private CartDB cartDB;
    @Autowired
    private CouponDB couponDB;

    @Value("${tax.rate}")
    private BigDecimal taxRate;


    private static List<Item> items;
    private static List<Coupon> coupons;


    @PostConstruct
    private void postConstruct() throws IOException {
        items = cartDB.getItemsFromJSONData();
        coupons = couponDB.getCouponsFromJSONData();
    }




    public BigDecimal findSubTotalBeforeDiscount() {

        BigDecimal cartSubTotal = BigDecimal.ZERO;
        for (Item item : items) {
            cartSubTotal = cartSubTotal.add(item.getPrice());
        }

        return cartSubTotal;
    }

    public BigDecimal findTotalDiscounts() {
        BigDecimal totalDiscounts = BigDecimal.ZERO;

        for (Coupon coupon : coupons) {
            Integer appliedSku = coupon.getAppliedSku();
            BigDecimal discount = isCouponAppliedSkuInCart(appliedSku)? coupon.getDiscountPrice(): BigDecimal.ZERO;
            totalDiscounts = totalDiscounts.add(discount);
        }
        return totalDiscounts;
    }

    public BigDecimal findSubTotalAfterDiscount() {
        return findSubTotalBeforeDiscount().subtract(findTotalDiscounts());
    }


    public BigDecimal findTaxableSubTotalAfterDiscounts() {

        BigDecimal totalTaxableSubTotalAfterDiscounts = BigDecimal.ZERO;

        for (Item item : items) {
            if (!item.isTaxable()) {
                continue;
            }
            totalTaxableSubTotalAfterDiscounts = totalTaxableSubTotalAfterDiscounts.add(getDiscountedItemPrice(item));
        }

        return totalTaxableSubTotalAfterDiscounts;
    }

    public BigDecimal findTotalTax() {
        return findTaxableSubTotalAfterDiscounts().multiply(taxRate);
    }

    public BigDecimal findGrandTotal() {
        return findSubTotalAfterDiscount().add(findTotalTax());
    }


    /*
     * The final price of an item cannot be negative after the discount is applied
     * */
    private BigDecimal getDiscountedItemPrice(Item item) {
        BigDecimal discountedPrice = item.getPrice().subtract(getDiscountPriceForSku(item.getSku()));
        if (discountedPrice.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        }

        return discountedPrice;
    }

    private BigDecimal getDiscountPriceForSku(int sku) {
        Coupon matchingCoupon = coupons.stream()
            .filter(coupon -> coupon.getAppliedSku() == sku)
            .findFirst()
            .orElse(null);

        if (matchingCoupon != null) {
            return matchingCoupon.getDiscountPrice();
        }
        return BigDecimal.ZERO;
    }

    private boolean isCouponAppliedSkuInCart(int appliedSku){

        Item foundItem = items.stream()
                .filter(item -> item.getSku() == appliedSku)
                .findFirst()
                .orElse(null);

        return foundItem == null? false: true;
    }

    public CartSummaryDTO createCartSummaryDTO(){

        CartSummaryDTO cartSummaryDTO = new CartSummaryDTO();

        cartSummaryDTO.setSubTotalBeforeDiscount(NumberOperations.formatData(findSubTotalBeforeDiscount()));
        cartSummaryDTO.setDiscountTotal(NumberOperations.formatData(findTotalDiscounts()));
        cartSummaryDTO.setSubtotalAfterDiscount(NumberOperations.formatData(findSubTotalAfterDiscount()));
        cartSummaryDTO.setTaxableSubtotalAfterDiscount(NumberOperations.formatData(findTaxableSubTotalAfterDiscounts()));
        cartSummaryDTO.setTotalTax(NumberOperations.formatData(findTotalTax()));
        cartSummaryDTO.setGrandTotal(NumberOperations.formatData(findGrandTotal()));

        return cartSummaryDTO;
    }
}
