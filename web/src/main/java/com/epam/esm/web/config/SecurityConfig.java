package com.epam.esm.web.config;

import com.epam.esm.model.service.UserService;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
public class SecurityConfig {

    private UserService userService;
}
