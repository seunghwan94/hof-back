package com.lshwan.hof.controller.main;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lshwan.hof.domain.entity.payment.Refund;
import com.lshwan.hof.service.pay.RefundService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("main/refund")
@AllArgsConstructor
public class RefundController {
  private final RefundService refundService;

    @PostMapping("/refund")
    public ResponseEntity<Refund> processRefund(
            @RequestParam Long payNo,
            @RequestParam String reason,
            @RequestParam Refund.RefundMethod method,
            @RequestParam int refundPrice) {
        Refund refund = refundService.processRefund(payNo, reason, method, refundPrice);
        return ResponseEntity.ok(refund);
    }
}
