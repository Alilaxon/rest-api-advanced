package com.epam.esm.model.service.impl;

import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.exception.NoSuchUserException;
import com.epam.esm.model.exception.UserAlreadyRegisteredException;
import com.epam.esm.model.service.UserService;
import com.epam.esm.persistance.dao.RoleRepository;
import com.epam.esm.persistance.dao.UserRepository;
import com.epam.esm.persistance.dao.builders.UserBuilder;
import com.epam.esm.persistance.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(@Qualifier("hibernateUserRepository") UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User create(UserDTO userDTO) throws UserAlreadyRegisteredException {
        log.info("User with name = {} will be create",userDTO.getUsername());
      if(userRepository.findByUserName(userDTO.getUsername()).isPresent()){
          throw new UserAlreadyRegisteredException(userDTO.getUsername());
      }
        User user = UserBuilder.builder()
                .userName(userDTO.getUsername())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .roles(List.of(roleRepository.findByName("ROLE_USER").get()))
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
    public User createAdmin(String name,String password,String email) throws UserAlreadyRegisteredException {
        if(userRepository.findByUserName(name).isPresent()){
            throw new UserAlreadyRegisteredException(name);
        }
        User user = UserBuilder.builder()
                .userName(name)
                .email(email)
                .password(passwordEncoder.encode(password))
                .roles(List.of(roleRepository.findByName("ROLE_ADMIN").get()))
                .build();
        userRepository.save(user);
        return user;
    }

    @Override
    public Optional<User> findByUserName(String name) {
        return userRepository.findByUserName(name);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUserName(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User not found")));
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList()));

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
