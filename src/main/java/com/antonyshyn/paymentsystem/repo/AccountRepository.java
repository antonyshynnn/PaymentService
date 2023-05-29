package com.antonyshyn.paymentsystem.repo;

import com.antonyshyn.paymentsystem.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
