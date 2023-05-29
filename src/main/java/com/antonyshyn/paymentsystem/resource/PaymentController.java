package com.antonyshyn.paymentsystem.resource;

import com.antonyshyn.paymentsystem.dto.request.PaymentRequestDTO;
import com.antonyshyn.paymentsystem.dto.response.PaymentResponseDTO;
import com.antonyshyn.paymentsystem.entity.Payment;
import com.antonyshyn.paymentsystem.payload.response.MessageResponse;
import com.antonyshyn.paymentsystem.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<?> makePayment(@RequestBody PaymentRequestDTO transactionRequestDTO) {
        try {
            Payment payment = paymentService.processPayment(transactionRequestDTO);
            return new ResponseEntity<>(payment, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/{accountId}")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentsByAccount(@PathVariable String accountId) {
        List<PaymentResponseDTO> paymentResponseDTO = paymentService.getPaymentsByAccountId(Long.valueOf(accountId));
        return new ResponseEntity<>(paymentResponseDTO, HttpStatus.OK);
    }
}
