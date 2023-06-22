package com.epam.esm.model.dto;

import java.beans.ConstructorProperties;
import java.util.Objects;

public class UserDTO {

    private Long id;

    private String userName;

    private String email;

    private String password;

    public UserDTO() {
    }

    public UserDTO(Long id, String userName,
                   String email, String password) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    @ConstructorProperties({"name", "email", "password"})
    public UserDTO(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;
        UserDTO userDTO = (UserDTO) o;
        return userName.equals(userDTO.userName) && email.equals(userDTO.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, email);
    }
}
