package com.epam.esm.web.controller.admin;

import com.epam.esm.model.exception.UserAlreadyRegisteredException;
import com.epam.esm.model.service.UserService;
import com.epam.esm.web.controller.AuthController;
import com.epam.esm.web.utils.UrlParts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(UrlParts.ADMIN)
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(UrlParts.REGISTRATION)
    public ResponseEntity<?> createNewUser(
            @Value("${admin.name}") String name,
            @Value("${admin.password}") String password,
            @Value("${admin.email}") String email) throws UserAlreadyRegisteredException {

        userService.createAdmin(name, password, email);
        log.info("Try to create admin account");
        return new ResponseEntity<>("Admin was created", HttpStatus.ACCEPTED);
    }

}
