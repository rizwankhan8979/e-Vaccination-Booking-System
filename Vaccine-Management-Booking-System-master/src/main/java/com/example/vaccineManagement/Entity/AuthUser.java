package com.example.vaccineManagement.Entity;

import com.example.vaccineManagement.Enums.Role;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "auth_users")
public class AuthUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @com.fasterxml.jackson.annotation.JsonProperty(access = com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean emailVerified = false;

    // Yeh mapping add ki hai user details fetch karne ke liye
    // mappedBy ka matlab hai ki foreign key 'User' table mein hai
    @OneToOne(mappedBy = "authUser", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User user;
}
