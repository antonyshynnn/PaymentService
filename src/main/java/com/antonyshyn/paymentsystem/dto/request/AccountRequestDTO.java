package com.antonyshyn.paymentsystem.dto.request;

import com.antonyshyn.paymentsystem.entity.types.Status;

import java.math.BigDecimal;

public class AccountRequestDTO {
    public Long accountId;
    public BigDecimal balance;
    public Status status;
}
