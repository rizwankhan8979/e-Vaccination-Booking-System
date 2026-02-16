package com.example.vaccineManagement.Dtos.AuthDtos;

import lombok.Data;

@Data
public class VerifyUpdateEmailDto {
    private String newEmail;
    private int otp;
}
