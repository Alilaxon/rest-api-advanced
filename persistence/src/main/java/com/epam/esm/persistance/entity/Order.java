package com.epam.esm.persistance.entity;

import javax.persistence.Id;

public class Order {
    @Id
    private Long id;

    private Long giftId;

    private Long userId;

    private String createDate;

}
