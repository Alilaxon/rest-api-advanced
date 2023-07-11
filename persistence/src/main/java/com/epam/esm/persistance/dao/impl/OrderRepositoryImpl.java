package com.epam.esm.persistance.dao.impl;

import com.epam.esm.persistance.dao.OrderRepository;
import com.epam.esm.persistance.dao.mapper.OrderMapper;
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

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM orders WHERE id=?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Order order = null;
            while (resultSet.next()) {
                order = OrderMapper.extractOrder(resultSet);
            }
            statement.close();

            return Optional.ofNullable(order);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Order> getAll() {
        List<Order> orderList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM orders");
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
            log.error("Catch SQLException");
        }
    }

    @Override
    public void delete(Order order) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM orders WHERE id =?");
            statement.setLong(1, order.getId());
            statement.execute();
            statement.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
            }statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orderList;

    }
}
