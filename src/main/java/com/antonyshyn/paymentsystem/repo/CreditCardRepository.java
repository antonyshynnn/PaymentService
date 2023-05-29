package com.antonyshyn.paymentsystem.repo;

import com.antonyshyn.paymentsystem.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    Optional<CreditCard> findCreditCardByCardNumber(String cardNumber);
}
