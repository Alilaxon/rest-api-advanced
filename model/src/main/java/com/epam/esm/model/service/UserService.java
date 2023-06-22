package com.epam.esm.model.service;

import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.persistance.entity.User;

import java.util.List;

public interface UserService {

    User create (UserDTO userDTO) ;

    UserDTO getById(Long id);

    List<UserDTO> getAll();

    Long deleteById(Long id);

    public void fillDataBase();

    public void cleanDataBase();
}
