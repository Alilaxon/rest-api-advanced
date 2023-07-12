package com.epam.esm.model.service.impl;

import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.exception.NoSuchUserException;
import com.epam.esm.model.service.UserService;
import com.epam.esm.persistance.dao.UserRepository;
import com.epam.esm.persistance.dao.builders.UserBuilder;
import com.epam.esm.persistance.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(@Qualifier("hibernateUserRepository") UserRepository userRepository) {
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
    public UserDTO getById(Long id) throws NoSuchUserException {
        User user = userRepository.get(id).orElseThrow(() -> new NoSuchUserException(id));
        return new UserDTO(user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getPassword());
    }

    @Override
    public List<UserDTO> getAll() {
        return userRepository.getAll().stream()
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getUserName(),
                        user.getEmail(),
                        user.getPassword()))
                .collect(Collectors.toList());
    }

    @Override
    public Long deleteById(Long id) {
        userRepository.delete(userRepository.get(id).get());
        return id;
    }

    @Override
    public void fillTable() {
        for (int i = 0; i < 1000; i++) {
            String name = "User" + i;
            String email = "email" + i + "@gmail.com";
            String password = "12345678" + i;
            userRepository.save(UserBuilder.builder()
                    .userName(name)
                    .password(password)
                    .email(email).build());
        }
    }

    @Override
    public void cleanTable() {

        userRepository.deleteByPartOfName("ser");
    }
}
