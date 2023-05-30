package com.epam.esm.persistance.dao.mapper;

import com.epam.esm.persistance.entity.Tag;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TagMapper {

    public static Tag extractTag (ResultSet resultSet) throws SQLException {

        Tag tag = new Tag(resultSet.getLong(Columns.ID), resultSet.getString(Columns.TAG_NAME));

        return tag;

    }
}
