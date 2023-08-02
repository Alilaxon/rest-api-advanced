package com.epam.esm.model.service;

import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.exception.NoSuchUserException;
import com.epam.esm.model.exception.UserAlreadyRegisteredException;
import com.epam.esm.persistance.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends TableFiller,UserDetailsService {

    User create (UserDTO userDTO) throws UserAlreadyRegisteredException;

    UserDTO getById(Long id) throws NoSuchUserException;

  Optional<User>findByUserName(String name);

    List<UserDTO> getAll();

    Long deleteById(Long id);

}
