package com.lshwan.hof.controller.main;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lshwan.hof.service.pay.IamportService;
import com.lshwan.hof.service.pay.OrderService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("main/pay")
public class PayController {

  private final IamportService iamportService;
  private final OrderService orderService;

  @PostMapping("/validate")
  public ResponseEntity<?> validatePayment(@RequestBody Map<String, String> payload) {
      String impUid = payload.get("imp_uid");
      String merchantUid = payload.get("merchant_uid");

      Map<String, Object> paymentData = iamportService.validatePayment(impUid);

      if (paymentData != null && paymentData.get("status").equals("paid")) {
          int paidAmount = (int) paymentData.get("amount");
          if (orderService.verifyOrderAmount(merchantUid, paidAmount)) {
              orderService.completeOrder(merchantUid);
              return ResponseEntity.ok(Map.of("success", true));
          }
      }
      return ResponseEntity.badRequest().body(Map.of("success", false, "message", "결제 검증 실패"));
  }
}
