package com.epam.esm.model.service.impl;

import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.exception.NoSuchUserException;
import com.epam.esm.model.service.UserService;
import com.epam.esm.persistance.dao.UserRepository;
import com.epam.esm.persistance.dao.builders.UserBuilder;
import com.epam.esm.persistance.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    private final UserRepository userRepository = mock(UserRepository.class);

    private final UserService userService = new UserServiceImpl(userRepository);

    private User user;

    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        Long id = 1L;
        String username = "user";
        String password = "123456";
        String email = "user@epam.com";
        user = UserBuilder.builder()
                .id(id)
                .userName(username)
                .password(password)
                .email(email)
                .build();
        userDTO = new UserDTO(id, username, email, password);
    }

    @Test
    void create() {
        assertEquals(userService.create(userDTO), user);
    }

    @Test
    void getById() throws NoSuchUserException {
        when(userRepository.get(1L)).thenReturn(Optional.of(user));
        assertEquals(userService.getById(1L), userDTO);
    }

    @Test
    void getAll() {
        when(userRepository.getAll()).thenReturn(List.of(user));
        assertEquals(userService.getAll(), List.of(userDTO));
    }


}