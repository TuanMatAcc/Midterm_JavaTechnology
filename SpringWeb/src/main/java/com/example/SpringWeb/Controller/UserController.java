package com.example.SpringWeb.Controller;

import com.example.SpringWeb.Model.User;
import com.example.SpringWeb.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

//    public UserController(UserService userService) {
//        this.userService = userService;
//    }

    // Display the registration form
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // Thymeleaf template name
    }

    // Handle the registration form submission
    @PostMapping("/register")
    public String registerUser(User user) {
        userService.saveUser(user); // Save user in database
        return "redirect:/login"; // Redirect to login page after successful registration
    }
}
