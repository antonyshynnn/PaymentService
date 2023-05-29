package com.antonyshyn.paymentsystem.dto.request;

import java.math.BigDecimal;

public class ProcessPaymentRequestDTO {
    public Long senderAccountId;
    public Long recipientAccountId;
    public BigDecimal amount;

    public ProcessPaymentRequestDTO(Long senderAccountId, Long recipientAccountId, BigDecimal paymentAmount) {
        this.senderAccountId = senderAccountId;
        this.recipientAccountId = recipientAccountId;
        this.amount = paymentAmount;
    }
}
