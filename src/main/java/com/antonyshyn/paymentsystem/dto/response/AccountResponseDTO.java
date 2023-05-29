package com.antonyshyn.paymentsystem.dto.response;

import com.antonyshyn.paymentsystem.entity.types.Status;

import java.math.BigDecimal;

public class AccountResponseDTO {
    private Long accountId;
    private BigDecimal balance;
    private Status status;

    public AccountResponseDTO(Long accountId, BigDecimal balance, Status status) {
        this.accountId = accountId;
        this.balance = balance;
        this.status = status;
    }
}
