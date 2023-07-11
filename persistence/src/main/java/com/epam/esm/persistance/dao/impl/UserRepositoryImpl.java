package com.epam.esm.persistance.dao.impl;

import com.epam.esm.persistance.dao.UserRepository;
import com.epam.esm.persistance.dao.mapper.UserMapper;
import com.epam.esm.persistance.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
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
@Primary
public class UserRepositoryImpl implements UserRepository {

    private static final Logger log = LogManager.getLogger(UserRepositoryImpl.class);
    private final DataSource dataSource;

    @Autowired
    public UserRepositoryImpl(DataSource dataSource) {

        this.dataSource = dataSource;
    }

    @Override
    public Optional<User> get(long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM users WHERE id=?");
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
        log.info("getAll");
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM users");
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
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO users (user_name, password, email) VALUES(?,?,?)");
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void delete(User user) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement("DELETE FROM users WHERE id =?");
            statement.setLong(1, user.getId());
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteByPartOfName(String name) {
        String partOfName = "%" + name + "%";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement("DELETE FROM users WHERE user_name LIKE ?");
            statement.setString(1, partOfName);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
