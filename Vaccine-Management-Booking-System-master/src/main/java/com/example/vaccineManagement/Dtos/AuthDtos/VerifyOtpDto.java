package com.example.vaccineManagement.Dtos.AuthDtos;

import lombok.Data;

@Data
public class VerifyOtpDto {

    private String email;
    private int otp;
}
