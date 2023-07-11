package com.epam.esm.web.utils;


import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.exception.NoSuchTagException;
import com.epam.esm.web.controller.TagController;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class TagLinker {
    public static List<TagDTO> addLinkToTagDTO(List<TagDTO> list) {

        return list.stream().map(tagDTO -> {
            try {
                return tagDTO.add(linkTo(methodOn(TagController.class).getById(
                        tagDTO.getId())).withSelfRel());
            } catch (NoSuchTagException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }
}