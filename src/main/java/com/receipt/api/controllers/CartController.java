package com.receipt.api.controllers;

import com.receipt.api.dto.CartSummaryDTO;
import com.receipt.api.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;


@SuppressWarnings("LineLength")
@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/v1/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping("/receipt")
    public ResponseEntity<CartSummaryDTO> getCartSummary() {
        CartSummaryDTO cartSummaryDTO = new CartSummaryDTO();

        cartSummaryDTO.put("Subtotal before discounts", cartService.findSubTotalBeforeDiscount());
        cartSummaryDTO.put("Discount total", cartService.findTotalDiscounts());
        cartSummaryDTO.put("Subtotal after discounts", cartService.findSubTotalAfterDiscount());
        cartSummaryDTO.put("Taxable subtotal after discounts", cartService.findTaxableSubTotalAfterDiscounts());
        cartSummaryDTO.put("Total tax", cartService.findTotalTax());
        cartSummaryDTO.put("Grand total", cartService.findGrandTotal());

        log.info("Calculated cart summary");

        return ResponseEntity.ok(cartSummaryDTO);
    }
}
