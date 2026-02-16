package com.example.vaccineManagement.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtpEmail(String toEmail, int otp) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("rizwankhan60hy@gmail.com");
            message.setTo(toEmail);
            message.setSubject("Your OTP Verification Code");
            message.setText(
                    "Dear User,\n\n"
                            + "Your One-Time Password (OTP) for vaccination booking verification is: "
                            + otp
                            + "\n\nRegards,\n"
                            + "Vaccine Management System"
            );

            mailSender.send(message);
            System.out.println("OTP Email sent successfully to: " + toEmail);
        } catch (Exception e) {
            System.out.println("Error sending email : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
