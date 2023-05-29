package com.antonyshyn.paymentsystem.dto.response;

import com.antonyshyn.paymentsystem.entity.types.Status;

public class AccountStatusResponseDTO {
    public Long accountId;
    public Status status;

    public AccountStatusResponseDTO(Long accountId, Status status) {
        this.accountId = accountId;
        this.status = status;
    }
}
