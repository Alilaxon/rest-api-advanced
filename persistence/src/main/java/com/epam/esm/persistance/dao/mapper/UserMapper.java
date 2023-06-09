package com.epam.esm.persistance.dao.mapper;

import com.epam.esm.persistance.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {

    public static User extractUser(ResultSet resultSet) throws SQLException {

        return new User(resultSet.getLong(Columns.ID),
                resultSet.getString(Columns.USERNAME),
                resultSet.getString(Columns.PASSWORD),
                resultSet.getString(Columns.EMAIL));

    }
}
