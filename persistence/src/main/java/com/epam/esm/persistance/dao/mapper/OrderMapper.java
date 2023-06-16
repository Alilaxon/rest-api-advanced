package com.epam.esm.persistance.dao.mapper;

import com.epam.esm.persistance.dao.builders.OrderBuilder;
import com.epam.esm.persistance.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapper {

    public static Order extractOrder(ResultSet resultSet) throws SQLException {

        return OrderBuilder.builder()
                .id(resultSet.getLong(Columns.ID))
                .giftId(resultSet.getLong(Columns.GIFT_ID))
                .userId(resultSet.getLong(Columns.USER_ID))
                .price(resultSet.getLong(Columns.ORDER_PRICE))
                .createDate(resultSet.getString(Columns.CREATE_DATE))
                .build();
    }
}
