package com.epam.esm.model.dto;

import org.springframework.hateoas.RepresentationModel;

import java.beans.ConstructorProperties;
import java.util.Objects;

public class UserDTO extends RepresentationModel<UserDTO> {

    private Long id;

    private String username;

    private String email;

    private String password;

    public UserDTO() {
    }
    @ConstructorProperties({"username", "email", "password"})
    public UserDTO(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    public UserDTO(Long id, String username,
                   String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", userName='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;
        UserDTO userDTO = (UserDTO) o;
        return username.equals(userDTO.username) && email.equals(userDTO.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email);
    }
}
