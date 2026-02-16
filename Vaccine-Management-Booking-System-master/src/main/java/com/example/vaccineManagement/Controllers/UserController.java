package com.example.vaccineManagement.Controllers;
import com.example.vaccineManagement.Entity.User;
import com.example.vaccineManagement.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController
{

    @Autowired
    UserService userService;

    // ================= 1. COMPLETE PROFILE =================
    @PostMapping("/add")
    public User addUser(
            @RequestBody User user,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal
    ) {
        // principal reopening logged-in email
        return userService.addUser(user, principal.getUsername());
    }


    // ================= 2. GET MY PROFILE =================
    @GetMapping("/profile")
    public User getMyProfile(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal
    ) {
        return userService.getMyProfile(principal.getUsername());
    }

    // ================= 3. GET VACCINATION DATE =================
    @GetMapping("/getVaccinationDate")
    public Date getVaccinationDate(@RequestParam Integer userId) {
        return userService.getVaccDate(userId);
    }

}




















