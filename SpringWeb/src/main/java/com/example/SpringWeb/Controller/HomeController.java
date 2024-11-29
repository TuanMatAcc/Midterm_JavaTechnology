package com.example.SpringWeb.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

    @GetMapping("/admin")
    public String showAdminPage() {
        return "admin"; // Only accessible by users with ADMIN role
    }
}
