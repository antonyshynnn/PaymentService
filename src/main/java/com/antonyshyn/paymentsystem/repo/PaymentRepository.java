package com.antonyshyn.paymentsystem.repo;

import com.antonyshyn.paymentsystem.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<List<Payment>>findPaymentsBySenderAccount_AccountIdOrRecipientAccount_AccountId(Long senderId, Long recipientId);
}
