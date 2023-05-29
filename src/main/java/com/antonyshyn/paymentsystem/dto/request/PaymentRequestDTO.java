package com.antonyshyn.paymentsystem.dto.request;

import com.antonyshyn.paymentsystem.entity.Account;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentRequestDTO {
    public String paymentServiceProvider;
    public BigDecimal paymentAmount;
    public Long senderAccountId;
    public Long recipientAccountId;
    public Date paymentDate;
}
