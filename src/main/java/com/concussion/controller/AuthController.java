package com.concussion.controller;

import com.concussion.model.UserRole;
import com.concussion.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication) {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ATHLETE"))) {
            return "redirect:/athlete/summary";
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_COACH"))) {
            return "redirect:/coach/athletes";
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_HEALTHCARE"))) {
            return "redirect:/healthcare/dashboard";
        }
        return "redirect:/login";
    }
}
