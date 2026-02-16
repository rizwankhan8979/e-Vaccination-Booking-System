package com.example.vaccineManagement.Controllers;


import com.example.vaccineManagement.Dtos.AuthDtos.*;
import com.example.vaccineManagement.Entity.AuthUser;
import com.example.vaccineManagement.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    //REGISTER
    @PostMapping("/register")
    public AuthResponseDto register(@RequestBody RegisterRequestDto dto) {
        return new AuthResponseDto(authService.register(dto));
    }

    //OTP VERIFY
    @PostMapping("/verify-email")
    public AuthResponseDto verifyEmail(@RequestBody VerifyOtpDto dto) {
        String msg = authService.verifyEmail(dto.getEmail(), dto.getOtp());
        return new AuthResponseDto(msg);
    }

    // LOGIN
    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody LoginRequestDto dto) {
        String message = authService.login(dto);
        return new AuthResponseDto(message);
    }

    // update email
    @PutMapping("/update-email")
    public AuthResponseDto updateEmail(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal,
            @RequestBody UpdateEmailRequestDto dto
    ) {
        return new AuthResponseDto(
                authService.updateEmail(principal.getUsername(), dto)
        );
    }


    @PostMapping("/verify-update-otp")
    public AuthResponseDto verifyUpdateOtp(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal,
            @RequestBody VerifyUpdateEmailDto dto // Naya DTO banana padega
    ) {
        return new AuthResponseDto(
                authService.verifyUpdateEmail(principal.getUsername(), dto.getNewEmail(), dto.getOtp())
        );
    }



    // find by email
    @GetMapping("/by-email/{email}")
    public AuthUser findByEmail(@PathVariable String email) {
        return authService.findByEmail(email);
    }


}
