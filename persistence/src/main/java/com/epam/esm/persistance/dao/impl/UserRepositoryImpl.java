package com.epam.esm.persistance.dao.impl;

import com.epam.esm.persistance.dao.DefaultRepository;
import com.epam.esm.persistance.dao.UserRepository;
import com.epam.esm.persistance.dao.mapper.TagMapper;
import com.epam.esm.persistance.dao.mapper.UserMapper;
import com.epam.esm.persistance.entity.Tag;
import com.epam.esm.persistance.entity.User;
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
public class UserRepositoryImpl implements UserRepository {

    private final DataSource dataSource;

    @Autowired
    public UserRepositoryImpl(DataSource dataSource) {

        this.dataSource = dataSource;
    }

    @Override
    public Optional<User> get(long id) {

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id=?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            User user = null;

            while (resultSet.next()) {

                user = UserMapper.extractUser(resultSet);
            }
            statement.close();

            return Optional.ofNullable(user);

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }

    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                users.add(UserMapper.extractUser(resultSet));
            }

            statement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public void save(User user) {

        //TODO

    }


    @Override
    public void delete(User user) {
        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement("DELETE FROM gifts WHERE id =?");
            statement.setLong(1, user.getId());
            statement.execute();
            statement.close();

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }

    }
}
