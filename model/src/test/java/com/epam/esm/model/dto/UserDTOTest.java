package com.epam.esm.model.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class UserDTOTest {

 private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO();
    }
     @Test
     void UserDTOSettersAndGettersTest (){
        Long id = 1L;
        String username = "user";
        String password = "123456";
        String email = "user@epam.com";
        userDTO.setId(id);
        userDTO.setUserName(username);
        userDTO.setPassword(password);
        userDTO.setEmail(email);
        assertEquals(userDTO.getId(), id);
        assertEquals(userDTO.getUserName(), username);
        assertEquals(userDTO.getPassword(), password);
        assertEquals(userDTO.getEmail(),email);
     }
}