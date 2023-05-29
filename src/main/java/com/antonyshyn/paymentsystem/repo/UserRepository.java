package com.antonyshyn.paymentsystem.repo;

import com.antonyshyn.paymentsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserEntitiesByUsername(String username);

    boolean existsByUsername(String username);
}
