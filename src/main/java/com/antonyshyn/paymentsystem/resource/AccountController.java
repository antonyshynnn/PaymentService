package com.antonyshyn.paymentsystem.resource;

import com.antonyshyn.paymentsystem.dto.request.AccountRequestDTO;
import com.antonyshyn.paymentsystem.dto.request.ProcessPaymentRequestDTO;
import com.antonyshyn.paymentsystem.dto.response.AccountStatusResponseDTO;
import com.antonyshyn.paymentsystem.entity.Account;
import com.antonyshyn.paymentsystem.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<Account> createAccount(@RequestBody AccountRequestDTO accountRequestDTO) {
        Account account = accountService.saveAccount(accountRequestDTO);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<Account> updateAccount(@RequestBody AccountRequestDTO accountRequestDTO) {
        Account account = accountService.updateAccount(accountRequestDTO);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping("/block/{id}")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<AccountStatusResponseDTO> blockAccount(@PathVariable String id) {
        AccountStatusResponseDTO account = accountService.blockAccount(Long.valueOf(id));
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping("/replenish")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<Account> replenishBalance(@RequestBody ProcessPaymentRequestDTO processPaymentRequestDTO) {
        Account account = accountService.replenishBalance(processPaymentRequestDTO);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
}
