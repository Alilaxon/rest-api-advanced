package com.epam.esm.persistance.dao.builders;


import com.epam.esm.persistance.entity.GiftCertificate;
import com.epam.esm.persistance.entity.Tag;

import java.util.List;

public class GiftBuilder {

    private Long id;
    private String name;
    private String description;
    private Long price;
    private Long duration;
    private String createDate;
    private String lastUpdateDate;

    private List<Tag> tags;



    public static GiftBuilder builder() {

        return new GiftBuilder();
    }

    public GiftBuilder id(Long id) {

        this.id = id;

        return this;
    }

    public GiftBuilder name (String name) {

        this.name = name;

        return this;
    }

    public GiftBuilder description(String description) {

        this.description = description;

        return this;
    }

    public GiftBuilder price (Long price) {

        this.price = price;

        return this;
    }

    public GiftBuilder duration(Long duration) {

        this.duration = duration;

        return this;
    }

    public GiftBuilder createDate(String createDate) {

        this.createDate = createDate;

        return this;
    }

    public GiftBuilder lastUpdateDate(String lastUpdateDate) {

        this.lastUpdateDate = lastUpdateDate;

        return this;
    }


    public GiftBuilder tags (List<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public GiftCertificate build(){

        return new GiftCertificate(id,name,description,price,
                duration,createDate,lastUpdateDate,tags);
    }
}

