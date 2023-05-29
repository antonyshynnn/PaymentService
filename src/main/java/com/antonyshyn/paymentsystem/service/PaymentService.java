package com.antonyshyn.paymentsystem.service;

import com.antonyshyn.paymentsystem.dto.request.PaymentRequestDTO;
import com.antonyshyn.paymentsystem.dto.request.ProcessPaymentRequestDTO;
import com.antonyshyn.paymentsystem.dto.response.PaymentResponseDTO;
import com.antonyshyn.paymentsystem.entity.Account;
import com.antonyshyn.paymentsystem.entity.Payment;
import com.antonyshyn.paymentsystem.entity.types.Status;
import com.antonyshyn.paymentsystem.exception.AccountBlockedException;
import com.antonyshyn.paymentsystem.exception.FailedProcessingPaymentException;
import com.antonyshyn.paymentsystem.repo.AccountRepository;
import com.antonyshyn.paymentsystem.repo.PaymentRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class PaymentService {
    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    AccountService accountService; // should not be injected here

    public Payment processPayment(PaymentRequestDTO paymentRequestDTO) {
        Payment payment = new Payment(paymentRequestDTO.paymentServiceProvider, paymentRequestDTO.paymentAmount, new Date());
        payment.setSenderAccount(accountService.getAccountById(paymentRequestDTO.senderAccountId));
        payment.setRecipientAccount(accountService.getAccountById(paymentRequestDTO.recipientAccountId));

        accountService.makePayment(new ProcessPaymentRequestDTO(paymentRequestDTO.senderAccountId, paymentRequestDTO.recipientAccountId, paymentRequestDTO.paymentAmount));

        return savePayment(payment);
    }

    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public List<PaymentResponseDTO> getPaymentsByAccountId(Long accountId) {
        List<Payment> payments = paymentRepository.findPaymentsBySenderAccount_AccountIdOrRecipientAccount_AccountId(accountId, accountId).orElseThrow();
        return payments.stream().map(payment -> new PaymentResponseDTO(payment.getPaymentId(), payment.getPaymentServiceProvider(), payment.getPaymentAmount(), payment.getSenderAccount().getAccountId(), payment.getRecipientAccount().getAccountId(), payment.getPaymentDate()))
                .collect(Collectors.toList());
    }

}
