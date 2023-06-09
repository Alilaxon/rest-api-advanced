package com.epam.esm.model.service.impl;

import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.service.UserService;
import com.epam.esm.persistance.dao.UserRepository;
import com.epam.esm.persistance.dao.builders.UserBuilder;
import com.epam.esm.persistance.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {

        this.userRepository = userRepository;
    }


    @Override
    public User create(UserDTO userDTO) {
        User user = UserBuilder.builder()
                .userName(userDTO.getUserName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .build();
        userRepository.save(user);
        return user;
    }

    @Override
    public User getById(Long id) {
        return userRepository.get(id).get();
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public Long deleteById(Long id) {
        userRepository.delete(userRepository.get(id).get());
        return id;
    }
}
