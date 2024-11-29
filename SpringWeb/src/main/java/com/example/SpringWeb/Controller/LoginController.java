package com.example.SpringWeb.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    // Display login page
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Return login.html view
    }
}
