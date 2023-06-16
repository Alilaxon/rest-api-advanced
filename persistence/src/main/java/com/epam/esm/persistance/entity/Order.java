package com.epam.esm.persistance.entity;

import javax.persistence.Id;
import java.util.Objects;

public class Order {
    @Id
    private Long id;

    private Long giftId;

    private Long userId;

    private Long price;

    private String createDate;

    public Order() {
    }

    public Order(Long id, Long giftId, Long userId, Long price, String createDate) {
        this.id = id;
        this.giftId = giftId;
        this.userId = userId;
        this.price = price;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGiftId() {
        return giftId;
    }

    public void setGiftId(Long giftId) {
        this.giftId = giftId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return giftId.equals(order.giftId) && userId.equals(order.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(giftId, userId);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", giftId=" + giftId +
                ", userId=" + userId +
                ", price=" + price +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
