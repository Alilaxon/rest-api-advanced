package com.epam.esm.model.dto;

import java.beans.ConstructorProperties;
import java.util.Objects;

public class OrderDTO {

    private Long id;

    private Long giftId;

    private Long userId;

    private Long price;

    private String timestamp;

    public OrderDTO() {
    }
@ConstructorProperties({"giftId","userId"})
    public OrderDTO(Long giftId, Long userId) {
        this.giftId = giftId;
        this.userId = userId;

    }

    public OrderDTO(Long id, Long giftId, Long userId,
                    Long price,String timestamp) {
        this.id = id;
        this.giftId = giftId;
        this.userId = userId;
        this.price = price;
        this.timestamp = timestamp;
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDTO)) return false;
        OrderDTO orderDTO = (OrderDTO) o;
        return giftId.equals(orderDTO.giftId) && userId.equals(orderDTO.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(giftId, userId);
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", giftId=" + giftId +
                ", userId=" + userId +
                ", price=" + price +
                '}';
    }
}
