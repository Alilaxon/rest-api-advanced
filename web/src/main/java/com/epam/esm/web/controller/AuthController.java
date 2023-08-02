package com.epam.esm.web.controller;

import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.exception.UserAlreadyRegisteredException;
import com.epam.esm.model.service.UserService;
import com.epam.esm.web.DTO.AppError;
import com.epam.esm.web.DTO.JwtRequest;
import com.epam.esm.web.DTO.JwtResponse;
import com.epam.esm.web.utils.jwt.JwtTokenProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AuthController {

    private static final Logger log = LogManager.getLogger(AuthController.class);

    private final UserService userService;

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;


    @Autowired
    public AuthController(UserService userService, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken (@RequestBody JwtRequest authRequest){
        log.info(authRequest.toString());
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(),
                    authRequest.getPassword()));
        }
        catch (BadCredentialsException e){
            log.info(e.getMessage());
          return new ResponseEntity<>(new AppError(
                  HttpStatus.UNAUTHORIZED.value(),
                  "incorrect login or password"),
                  HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        log.info(userDetails.toString());
        String token = jwtTokenProvider.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@RequestBody UserDTO userDTO) throws UserAlreadyRegisteredException {
        userService.create(userDTO);
        return new ResponseEntity<>("User war created",HttpStatus.ACCEPTED);
    }

}
