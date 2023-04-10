package com.receipt.api.controllers;

import com.receipt.api.controllers.dto.CartSummaryDTO;
import com.receipt.api.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@SuppressWarnings("LineLength")
@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/v1/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping("/receipt")
    public ResponseEntity<CartSummaryDTO> getCartSummary() {
        log.info("Grand total is computed after the coupon discounts are applied and tax is calculated");

        CartSummaryDTO cartSummaryDTO = cartService.createCartSummaryDTO();
        return ResponseEntity.ok(cartSummaryDTO);
    }
}
