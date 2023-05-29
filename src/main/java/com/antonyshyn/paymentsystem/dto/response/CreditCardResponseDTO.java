package com.antonyshyn.paymentsystem.dto.response;

import com.antonyshyn.paymentsystem.entity.CreditCard;
import lombok.Getter;
import lombok.Setter;

public class CreditCardResponseDTO {
    public Long creditCardId;
    public String cardNumber;
    public String expirationDate;
    public Long accountId;
    public Long userId;
    public String CVV;

    public CreditCardResponseDTO(CreditCard creditCard) {
        this.creditCardId = creditCard.getCreditCardId();
        this.cardNumber = creditCard.getCardNumber();
        this.expirationDate = creditCard.getExpirationDate();
        if (creditCard.getAccount() != null) {
            this.accountId = creditCard.getAccount().getAccountId();
        }
        this.userId = creditCard.getUser().getUserId();
        this.CVV = creditCard.getCVV();
    }
}
