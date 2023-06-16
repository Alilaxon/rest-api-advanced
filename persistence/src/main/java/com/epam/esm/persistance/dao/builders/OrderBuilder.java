package com.epam.esm.persistance.dao.builders;

import com.epam.esm.persistance.entity.Order;

public class OrderBuilder {

    private Long id;

    private Long giftId;

    private Long userId;

    private Long price;

    private String createDate;

    public static OrderBuilder builder() {
        return new OrderBuilder();
    }

    public OrderBuilder id(Long id) {
        this.id = id;

        return this;
    }

    public OrderBuilder giftId(Long giftId) {
        this.giftId = giftId;
        return this;
    }

    public OrderBuilder userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public OrderBuilder price(Long price) {
        this.price = price;
        return this;
    }

    public OrderBuilder createDate(String createDate) {
        this.createDate = createDate;
        return this;
    }

    public Order build(){
        return new Order(id,giftId,userId,price,createDate);
    }
}
