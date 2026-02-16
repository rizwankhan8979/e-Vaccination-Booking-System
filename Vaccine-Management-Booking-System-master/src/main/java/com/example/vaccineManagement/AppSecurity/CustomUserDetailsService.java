package com.example.vaccineManagement.AppSecurity;

import com.example.vaccineManagement.Entity.AuthUser;
import com.example.vaccineManagement.Repository.AuthUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthUserRepository authUserRepository;

    public CustomUserDetailsService(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        AuthUser authUser = authUserRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Auth user not found with email: " + email
                        )
                );

        return org.springframework.security.core.userdetails.User
                .withUsername(authUser.getEmail())
                .password(authUser.getPassword())
                .roles(authUser.getRole().name()) // ROLE_USER / ROLE_ADMIN
                .build();
    }

}
