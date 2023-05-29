package com.antonyshyn.paymentsystem.resource;

import com.antonyshyn.paymentsystem.dto.request.AccountRequestDTO;
import com.antonyshyn.paymentsystem.dto.request.CreditCardRequestDTO;
import com.antonyshyn.paymentsystem.dto.request.LinkRequestDTO;
import com.antonyshyn.paymentsystem.dto.response.CreditCardResponseDTO;
import com.antonyshyn.paymentsystem.entity.Account;
import com.antonyshyn.paymentsystem.entity.CreditCard;
import com.antonyshyn.paymentsystem.payload.response.MessageResponse;
import com.antonyshyn.paymentsystem.service.AccountService;
import com.antonyshyn.paymentsystem.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/creditCard")
public class CreditCardController {
    @Autowired
    private final CreditCardService creditCardService;

    public CreditCardController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @GetMapping("number/{number}")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<?> getCreditCardBalance(@PathVariable String number) {
        try {
            BigDecimal balance = creditCardService.getCreditCardBalance(number);
            return new ResponseEntity<>(balance, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<CreditCardResponseDTO> createCreditCard(@RequestBody CreditCardRequestDTO creditCardRequestDTO) {
        CreditCardResponseDTO creditCard = creditCardService.createCreditCard(creditCardRequestDTO);
        return new ResponseEntity<>(creditCard, HttpStatus.OK);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<CreditCardResponseDTO> linkCreditCardWithAccount(@RequestBody LinkRequestDTO linkRequestDTO) {
        CreditCardResponseDTO creditCard = creditCardService.linkCreditCardWithAccount(linkRequestDTO);
        return new ResponseEntity<>(creditCard, HttpStatus.OK);
    }
}
