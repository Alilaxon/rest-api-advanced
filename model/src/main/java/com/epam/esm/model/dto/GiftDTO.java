package com.epam.esm.model.dto;

import com.epam.esm.persistance.entity.Tag;
import org.springframework.hateoas.RepresentationModel;

import java.beans.ConstructorProperties;
import java.util.List;

public class GiftDTO extends RepresentationModel<GiftDTO> {


    private Long id;

    private String name;

    private String description;

    private Long price;

    private Long duration;

    private List<Tag> tags;


    public GiftDTO() {
    }
    @ConstructorProperties({"name", "description","price","duration","tags"})
    public GiftDTO(String name, String description, Long price, Long duration, List<Tag> tags) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.tags = tags;
    }

    public GiftDTO(Long id, String name, String description, Long price, Long duration, List<Tag> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "GiftDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", tags=" + tags +
                '}';
    }
}
