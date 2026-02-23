package com.example.vaccineManagement.Dtos.AuthDtos;

import com.example.vaccineManagement.Enums.Role;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {

    private String email;     // login identity
    private String password;  //encrypted
    //private Role role;
}
