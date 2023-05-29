package com.antonyshyn.paymentsystem.service;

import com.antonyshyn.paymentsystem.dto.request.*;
import com.antonyshyn.paymentsystem.dto.response.AccountStatusResponseDTO;
import com.antonyshyn.paymentsystem.entity.Account;
import com.antonyshyn.paymentsystem.entity.types.Status;
import com.antonyshyn.paymentsystem.exception.*;
import com.antonyshyn.paymentsystem.repo.AccountRepository;
import com.antonyshyn.paymentsystem.repo.CreditCardRepository;
import com.antonyshyn.paymentsystem.repo.PaymentRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CreditCardRepository creditCardRepository;
    @Autowired
    PaymentRepository paymentRepository;

    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
    }

    public Account saveAccount(AccountRequestDTO accountRequestDTO) {
        Account account = new Account(accountRequestDTO.balance, accountRequestDTO.status);

        return accountRepository.save(account);
    }

    public Account updateAccount(AccountRequestDTO accountRequestDTO) {
        BigDecimal balance = Optional.ofNullable(accountRequestDTO.balance).orElse(getAccountBalance(accountRequestDTO.accountId));
        Account account = new Account(accountRequestDTO.accountId, balance, accountRequestDTO.status);

        return accountRepository.save(account);
    }

    public BigDecimal getAccountBalance(Long accountId) {
        Account account = getAccountById(accountId);

        return account.getBalance();
    }

    public Account replenishBalance(ProcessPaymentRequestDTO processPaymentRequestDTO) {
        Account recipientAccount = getAccountById(processPaymentRequestDTO.recipientAccountId);
        if (recipientAccount.getStatus().equals(Status.BLOCKED)) {
            throw new AccountBlockedException("Recipient account is blocked and cannot process payment");
        }

        BigDecimal currentBalance = recipientAccount.getBalance();
        BigDecimal updatedBalance = currentBalance.add(processPaymentRequestDTO.amount);
        recipientAccount.setBalance(updatedBalance);

        return accountRepository.save(recipientAccount);
    }

    public void makePayment(ProcessPaymentRequestDTO processPaymentRequestDTO) throws FailedProcessingPaymentException, InsufficientPaymentException {
        try {
            deductBalance(processPaymentRequestDTO);
            replenishBalance(processPaymentRequestDTO);
        } catch (FailedUpdatingAccountException failedUpdatingAccountException) {
            throw new FailedProcessingPaymentException("Failed making payment " + failedUpdatingAccountException);
        }
    }

    public void deductBalance(ProcessPaymentRequestDTO processPaymentRequestDTO) {
        Account senderAccount = getAccountById(processPaymentRequestDTO.senderAccountId);
        if (senderAccount.getStatus().equals(Status.BLOCKED)) {
            throw new AccountBlockedException("Sender account is blocked and cannot process payment");
        }

        BigDecimal currentBalance = senderAccount.getBalance();
        if (currentBalance.compareTo(processPaymentRequestDTO.amount) < 0) {
            throw new InsufficientPaymentException("Insufficient account balance");
        }
        BigDecimal updatedBalance = currentBalance.subtract(processPaymentRequestDTO.amount);
        senderAccount.setBalance(updatedBalance);
        accountRepository.save(senderAccount);
    }

    public AccountStatusResponseDTO blockAccount(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException("Account cannot be found"));

        account.setStatus(Status.BLOCKED);

        Account savedAccount = accountRepository.save(account);

        return new AccountStatusResponseDTO(savedAccount.getAccountId(), account.getStatus());
    }
}
