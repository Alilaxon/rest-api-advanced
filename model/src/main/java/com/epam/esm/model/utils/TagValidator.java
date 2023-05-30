package com.epam.esm.model.utils;

import com.epam.esm.model.dto.TagDto;
import com.epam.esm.persistance.entity.Tag;
import com.epam.esm.model.exception.InvalidTagDtoException;
import com.epam.esm.model.exception.InvalidTagException;

public class TagValidator {

    public static void checkTagDto (TagDto dto) throws InvalidTagDtoException {
        if(dto.getName() == null){
            throw new InvalidTagDtoException();
        }

        if(!(dto.getName().length() >= 1 && dto.getName().length() <= 32)){
            throw new InvalidTagDtoException();
        }
    }

    public static void checkTag (Tag tag) throws InvalidTagException {
        if(tag.getName() == null){
            throw new InvalidTagException();
        }

        if(!(tag.getName().length() >= 1 && tag.getName().length() <= 32)){
            throw new InvalidTagException();
        }
    }
}
