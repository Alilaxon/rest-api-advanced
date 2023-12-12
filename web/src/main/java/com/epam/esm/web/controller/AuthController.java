package com.epam.esm.web.controller;

import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.exception.UserAlreadyRegisteredException;
import com.epam.esm.model.service.UserService;
import com.epam.esm.web.DTO.JwtRefreshRequest;
import com.epam.esm.web.DTO.JwtRequest;
import com.epam.esm.web.DTO.JwtResponse;
import com.epam.esm.web.utils.UrlParts;
import com.epam.esm.web.utils.jwt.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;


    @Autowired
    public AuthController(UserService userService,
                          JwtTokenProvider jwtTokenProvider,
                          AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(UrlParts.AUTH+UrlParts.TOKEN)
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest request)
            throws BadCredentialsException {
        // как это работало без authenticationManager
       // UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
        System.out.println(request.toString());
        log.info(String.valueOf(request));
      Authentication authentication = authenticationManager
              .authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()));
        log.info(String.valueOf(authentication));
        return ResponseEntity.ok(jwtTokenProvider.generateToken(
                authentication.getName(),
                authentication.getAuthorities()));
    }

    @PostMapping(UrlParts.AUTH+UrlParts.TOKEN+UrlParts.REFRESH)
    public ResponseEntity<JwtResponse> refreshAuthToken(@RequestBody JwtRefreshRequest request)
            throws BadCredentialsException {
        return ResponseEntity.ok(jwtTokenProvider.refreshToken(
                request.getAccessToken(),
                request.getRefreshToken()));
    }

    @PostMapping(UrlParts.REGISTRATION)
    public ResponseEntity<?> createNewUser(@RequestBody UserDTO userDTO)
            throws UserAlreadyRegisteredException {
        if (userDTO.getUsername() == null) {
            return new ResponseEntity<>("error of fields",HttpStatus.BAD_REQUEST);
        }
        userService.create(userDTO);
        return new ResponseEntity<>("User was created", HttpStatus.ACCEPTED);
    }

}
