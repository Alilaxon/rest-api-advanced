package com.epam.esm.model.service;

import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.exception.NoSuchUserException;
import com.epam.esm.persistance.entity.User;
import java.util.List;

public interface UserService extends TableFiller {

    User create (UserDTO userDTO) ;

    UserDTO getById(Long id) throws NoSuchUserException;

    List<UserDTO> getAll();

    Long deleteById(Long id);

}
