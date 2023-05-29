package com.antonyshyn.paymentsystem.dto.request;

import java.math.BigDecimal;
import java.util.Date;

public class CreditCardRequestDTO {
    public String cardNumber;
    public String expirationDate;
    public String CVV;
    public Long accountId;
    public Long userId;

    public CreditCardRequestDTO(String cardNumber, String expirationDate, String CVV) {
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.CVV = CVV;
    }
}
