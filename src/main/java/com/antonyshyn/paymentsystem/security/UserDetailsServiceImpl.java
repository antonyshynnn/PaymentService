package com.antonyshyn.paymentsystem.security;

import com.antonyshyn.paymentsystem.repo.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return SecureUser.fromUser(userRepository.findUserEntitiesByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException("User not found")));
    }
}
