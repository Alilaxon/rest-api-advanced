package com.epam.esm.web.controller;


import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.exception.NoSuchUserException;
import com.epam.esm.model.service.UserService;
import com.epam.esm.web.utils.UrlParts;
import com.epam.esm.web.utils.UserLinker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public UserDTO get(@PathVariable ("id") Long id) throws NoSuchUserException {

        return userService.getById(id);
    }

    @GetMapping("/fillDataBase")
    public Integer fillDataBase (){
        userService.fillTable();
        return userService.getAll().size();
    }

    @GetMapping("/cleanDataBase")
    public Integer cleanDataBase (){
        userService.cleanTable();
        return userService.getAll().size();
    }
}
