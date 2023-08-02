package com.epam.esm.persistance.entity;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   @Column(name = "user_name")
   private String userName;

   @Column(name = "password")
   private String email;

   @Column(name = "email")
   private String password;

   @ManyToMany
   @JoinTable(name = "users_roles",
   joinColumns = @JoinColumn(name = "user_id"),
   inverseJoinColumns = @JoinColumn(name = "role_id"))
   private Collection<Role>roles ;



   public User() {
   }

   public User(Long id, String userName, String email, String password) {
      this.id = id;
      this.userName = userName;
      this.email = email;
      this.password = password;
   }

   public User(Long id, String userName, String email, String password, Collection<Role> roles) {
      this.id = id;
      this.userName = userName;
      this.email = email;
      this.password = password;
      this.roles = roles;
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

   public Collection<Role> getRoles() {
      return roles;
   }

   public void setRoles(Collection<Role> roles) {
      this.roles = roles;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof User)) return false;
      User user = (User) o;
      return userName.equals(user.userName) && email.equals(user.email);
   }

   @Override
   public int hashCode() {
      return Objects.hash(userName, email);
   }

   @Override
   public String toString() {
      return "User{" +
              "id=" + id +
              ", userName='" + userName + '\'' +
              ", email='" + email + '\'' +
              ", password='" + password + '\'' +
              '}';
   }
}
