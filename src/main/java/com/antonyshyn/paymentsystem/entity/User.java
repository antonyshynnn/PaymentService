package com.antonyshyn.paymentsystem.entity;

import com.antonyshyn.paymentsystem.entity.types.UserRoles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRoles userRole;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "creditCardId")
    @JsonIgnore
    private Set<CreditCard> creditCard;

    public User(String username, String password, UserRoles userRole) {
        this.username = username;
        this.password = password;
        this.userRole = userRole;
    }
}
