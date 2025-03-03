package com.lshwan.hof.controller.main;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lshwan.hof.domain.dto.pay.AdminRepundDto;
import com.lshwan.hof.domain.entity.payment.Refund;
import com.lshwan.hof.service.pay.RefundService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("main/refund")
@AllArgsConstructor
@Log4j2
public class RefundController {
  private final RefundService refundService;

  /**
   * 환불 요청 API
   * POST /api/v1/refund/request
   */
  @PostMapping("/request")
  public ResponseEntity<?> requestRefund(@RequestParam(name = "payNo") Long payNo, @RequestParam(name = "reason") String reason) {
    log.info("payNo : " + payNo);
    log.info("reason : " + reason);
    try {
      Refund refund = refundService.processRefund(payNo, reason);
      return ResponseEntity.ok(refund);
    } catch (IllegalArgumentException | IllegalStateException e) {
      log.error("환불 요청 실패: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (Exception e) {
      log.error("환불 처리 중 오류 발생", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("환불 처리에 실패했습니다.");
    }
  }

  /**
   * 환불 정보 조회 API
   * GET /api/v1/refund/{pno}
   */
  @GetMapping("/{payNo}")
  public ResponseEntity<?> getRefundByPayId(@PathVariable Long payNo) {
    try {
      Refund refund = refundService.getRefundByPayId(payNo);
      if (refund == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("환불 정보가 없습니다.");
      }
      return ResponseEntity.ok(refund);
    } catch (Exception e) {
      log.error("환불 정보 조회 실패", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("환불 정보 조회에 실패했습니다.");
    }
  }
  @GetMapping("/adminlist")
    public ResponseEntity<List<AdminRepundDto>> getAllRefunds() {
        List<AdminRepundDto> refunds = refundService.getAllRefundsByAdmin();
        return ResponseEntity.ok(refunds);
    }
}
