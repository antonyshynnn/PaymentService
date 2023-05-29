package com.antonyshyn.paymentsystem.payload.request;

import com.antonyshyn.paymentsystem.entity.types.UserRoles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    private String username;
    private String password;
    private String email;
    private Long roomId;
    private UserRoles role;
}
