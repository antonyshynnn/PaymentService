package com.antonyshyn.paymentsystem.entity;

import com.antonyshyn.paymentsystem.entity.types.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accountId;
    private BigDecimal balance;
    private Status status;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "paymentId")
    @JsonIgnore
    private Set<Payment> payments;

    public Account(BigDecimal balance, Status status) {
        this.balance = balance;
        this.status = status;
    }

    public Account(Long accountId, BigDecimal balance, Status status) {
        this.accountId = accountId;
        this.balance = balance;
        this.status = status;
    }
}
