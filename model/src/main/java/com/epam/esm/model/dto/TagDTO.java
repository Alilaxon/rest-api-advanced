package com.epam.esm.model.dto;

import java.beans.ConstructorProperties;
import java.util.Objects;

public class TagDTO {

    private Long id;

    private String name;

    public TagDTO() {
    }

    @ConstructorProperties("name")
    public TagDTO(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TagDTO)) return false;
        TagDTO tagDto = (TagDTO) o;
        return name.equals(tagDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "TagDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
