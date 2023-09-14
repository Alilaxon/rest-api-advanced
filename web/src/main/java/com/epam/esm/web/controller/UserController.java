package com.epam.esm.web.controller;


import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.exception.NoSuchUserException;
import com.epam.esm.model.service.UserService;
import com.epam.esm.web.utils.UrlParts;
import com.epam.esm.web.utils.UserLinker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UrlParts.USERS)
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {

        this.userService = userService;
    }
    @GetMapping
    public List<UserDTO> getAll(){
        return UserLinker.addLinkToGiftDTO(userService.getAll());
    }
    @GetMapping(UrlParts.ID)
    @ResponseStatus(HttpStatus.OK)
    public UserDTO get(@PathVariable ("id") Long id)
            throws NoSuchUserException {
        return userService.getById(id);
    }

    @GetMapping(UrlParts.FILL_DATABASE)
    public ResponseEntity<String> fillDataBase (){
        userService.fillTable();
        int numberOfUsers = userService.getAll().size();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(numberOfUsers + " users have been created");
    }

    @GetMapping(UrlParts.CLEAN_DATABASE)
    public ResponseEntity<String> cleanDataBase (){
        userService.cleanTable();
        int numberOfUsers = userService.getAll().size();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(numberOfUsers + " users have been found after cleaning");
    }
}
