package com.antonyshyn.paymentsystem.service;

import com.antonyshyn.paymentsystem.dto.request.CreditCardRequestDTO;
import com.antonyshyn.paymentsystem.dto.request.LinkRequestDTO;
import com.antonyshyn.paymentsystem.dto.response.CreditCardResponseDTO;
import com.antonyshyn.paymentsystem.entity.Account;
import com.antonyshyn.paymentsystem.entity.CreditCard;
import com.antonyshyn.paymentsystem.entity.User;
import com.antonyshyn.paymentsystem.entity.types.Status;
import com.antonyshyn.paymentsystem.exception.AccountBlockedException;
import com.antonyshyn.paymentsystem.exception.AccountNotFoundException;
import com.antonyshyn.paymentsystem.exception.CreditCardNotFoundException;
import com.antonyshyn.paymentsystem.repo.AccountRepository;
import com.antonyshyn.paymentsystem.repo.CreditCardRepository;
import com.antonyshyn.paymentsystem.repo.PaymentRepository;
import com.antonyshyn.paymentsystem.repo.UserRepository;
import com.antonyshyn.paymentsystem.utils.CreditCardGenerator;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardService {
    @Autowired
    CreditCardRepository creditCardRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    UserRepository userRepository;

    public CreditCard getCreditCardById(Long creditCardId) {
        return creditCardRepository.findById(creditCardId)
                .orElseThrow(() -> new CreditCardNotFoundException("CreditCard not found"));
    }

    public CreditCardResponseDTO createCreditCard(CreditCardRequestDTO creditCardRequestDTO) {
        CreditCard creditCard;

        if (creditCardRequestDTO.cardNumber != null && creditCardRequestDTO.expirationDate != null && creditCardRequestDTO.CVV != null) {
            creditCard = new CreditCard(creditCardRequestDTO.cardNumber, creditCardRequestDTO.expirationDate, creditCardRequestDTO.CVV);
        } else {
            creditCard = new CreditCard(CreditCardGenerator.generateRandomCreditCardNumber(), CreditCardGenerator.generateRandomExpirationDate(), CreditCardGenerator.generateRandomCVV());
        }

        User user = userRepository.findById(creditCardRequestDTO.userId).orElseThrow();

        creditCard.setUser(user);
        creditCard = creditCardRepository.save(creditCard);

        return new CreditCardResponseDTO(creditCard);
    }

    public CreditCardResponseDTO linkCreditCardWithAccount(LinkRequestDTO linkRequestDTO) {
        CreditCard creditCard = creditCardRepository.findById(linkRequestDTO.creditCardId).orElseThrow();
        Account account = accountRepository.findById(linkRequestDTO.accountId).orElseThrow();

        creditCard.setAccount(account);
        creditCard = creditCardRepository.save(creditCard);

        return new CreditCardResponseDTO(creditCard);
    }

    public BigDecimal getCreditCardBalance(String creditCardNumber) {
        CreditCard creditCard = creditCardRepository.findCreditCardByCardNumber(creditCardNumber).orElseThrow();

        if (creditCard.getAccount() == null) {
            throw new AccountBlockedException("Account is not linked to the credit card");
        }
        if (creditCard.getAccount().getStatus().equals(Status.BLOCKED)) {
            throw new AccountBlockedException("Account is blocked and cannot get balance");
        }

        return creditCard.getAccount().getBalance();
    }
}
