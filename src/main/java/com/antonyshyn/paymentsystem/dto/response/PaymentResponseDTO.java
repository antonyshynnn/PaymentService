package com.antonyshyn.paymentsystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDTO {
    public Long paymentId;
    public String paymentServiceProvider;
    public BigDecimal paymentAmount;
    public Long senderAccountId;
    public Long recipientAccountId;
    public Date paymentDate;
}
