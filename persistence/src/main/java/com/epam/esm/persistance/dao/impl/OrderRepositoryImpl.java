package com.epam.esm.persistance.dao.impl;

import com.epam.esm.persistance.dao.OrderRepository;
import com.epam.esm.persistance.dao.mapper.Columns;
import com.epam.esm.persistance.dao.mapper.GiftMapper;
import com.epam.esm.persistance.dao.mapper.OrderMapper;
import com.epam.esm.persistance.entity.GiftCertificate;
import com.epam.esm.persistance.entity.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private static final Logger log = LogManager.getLogger(OrderRepositoryImpl.class);

    private final DataSource dataSource;
    @Autowired
    public OrderRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Order> get(long id) {

        return Optional.empty();
    }

    @Override
    public List<Order> getAll() {

        return null;
    }

    @Override
    public void save(Order order) {
        log.info("{}",order.toString());

        try (Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO orders(order_price, user_id, gift_id,create_date)" +
                            " VALUES (?,?,?,?)");
            preparedStatement.setLong(1, order.getPrice());
            preparedStatement.setLong(2, order.getUserId());
            preparedStatement.setLong(3,order.getGiftId());
            preparedStatement.setString(4,order.getCreateDate());
            preparedStatement.executeUpdate();
            preparedStatement.close();

            log.info("order was created");

        } catch (SQLException e) {

            log.warn("Catch SQLException");

            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(Order order) {

    }

    @Override
    public List<Order> getAllByUserId(Long id) {
        List<Order> orderList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM orders WHERE user_id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                orderList.add(OrderMapper.extractOrder(resultSet));
            }
            statement.close();
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
        return orderList;

    }
}
