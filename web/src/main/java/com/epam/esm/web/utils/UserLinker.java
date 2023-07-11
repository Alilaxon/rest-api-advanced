package com.epam.esm.web.utils;

import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.exception.NoSuchUserException;
import com.epam.esm.web.controller.UserController;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class UserLinker {
    public static List<UserDTO> addLinkToGiftDTO(List<UserDTO> list){

        return list.stream().map(userDTO -> {
            try {
                return userDTO.add(linkTo(methodOn(UserController.class).get(
                        userDTO.getId())).withSelfRel());
            } catch (NoSuchUserException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }
}
