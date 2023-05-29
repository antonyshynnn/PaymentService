package com.antonyshyn.paymentsystem.entity;

import com.antonyshyn.paymentsystem.entity.types.Status;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long paymentId;
    private String paymentServiceProvider;
    private BigDecimal paymentAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "senderAccountId")
    @JsonManagedReference
    private Account senderAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipientAccountId")
    @JsonManagedReference
    private Account recipientAccount;
    private Date paymentDate;

    public Payment(String paymentServiceProvider, BigDecimal paymentAmount, Date paymentDate) {
        this.paymentServiceProvider = paymentServiceProvider;
        this.paymentAmount = paymentAmount;
        this.paymentDate = paymentDate;
    }
}
