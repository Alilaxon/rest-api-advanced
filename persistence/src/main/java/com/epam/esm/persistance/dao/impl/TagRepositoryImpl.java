package com.epam.esm.persistance.dao.impl;

import com.epam.esm.persistance.dao.TagRepository;
import com.epam.esm.persistance.dao.mapper.Columns;
import com.epam.esm.persistance.dao.mapper.TagMapper;
import com.epam.esm.persistance.entity.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public class TagRepositoryImpl implements TagRepository {

    private static final Logger log = LogManager.getLogger(TagRepositoryImpl.class);
    private final DataSource dataSource;

    private static final String SELECT_FOR_USER_ID = "SELECT u.id AS user_id, sum(o.order_price) AS total_price " +
            "FROM users AS u\n" +
            "JOIN orders AS o ON u.id = o.user_id\n" +
            "GROUP BY u.id\n" +
            "ORDER BY total_price DESC\n" +
            "LIMIT 1";

    private static final String SELECT_FOR_TAG = "SELECT tag_name , count(tag_id) AS tag_counter FROM tags\n" +
            "JOIN gifts_tags gt on tags.id = gt.tag_id\n" +
            "JOIN gifts g on g.id = gt.gift_id\n" +
            "JOIN orders o on g.id = o.gift_id\n" +
            "WHERE user_id = ?\n" +
            "GROUP BY tag_name\n" +
            "ORDER BY tag_counter DESC\n" +
            "LIMIT 1";


    public TagRepositoryImpl(DataSource dataSource) {

        this.dataSource = dataSource;
    }

    @Override
    public List<Tag> getAll() {
        List<Tag> tagList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM tags");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tagList.add(TagMapper.extractTag(resultSet));
            }
            statement.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return tagList;
    }

    public Tag save(Tag tag) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO tags (tag_name) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, tag.getName());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating Tag failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    tag.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating Tag failed, no ID obtained.");
                }
            }
            statement.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return tag;
    }

    @Override
    public Optional<Tag> findByName(String name) {
        log.info("Find Tag by name {}",name);
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM tags WHERE tag_name=?");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            Tag tag = null;
            while (resultSet.next()) {
                tag = TagMapper.extractTag(resultSet);
            }
            statement.close();
            return Optional.ofNullable(tag);

        } catch (SQLException e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Tag> findById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM tags WHERE id=?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Tag tag = null;
            while (resultSet.next()) {
                tag = TagMapper.extractTag(resultSet);
            }
            statement.close();
            return Optional.ofNullable(tag);

        } catch (SQLException e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }


    @Override
    public Long delete(Long id) {

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement("DELETE FROM tags WHERE id =?");
            statement.setLong(1, id);
            statement.execute();
            statement.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
     return id;
    }

    @Override
    public void deleteByPartOfName(String part) {
        String partOfName = String.valueOf(new StringBuilder().append("%").append(part).append("%"));
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement("DELETE FROM tags WHERE tags.tag_name LIKE ?");
            statement.setString(1, partOfName);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public boolean existsByName(String name) {
        try (Connection connection = dataSource.getConnection()) {
            boolean existsByName;
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM tags WHERE tag_name =?");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            existsByName = resultSet.next();
            statement.close();

            return existsByName;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Tag> getTheMostWidelyUsedTagOfUserWithTheHighestCostOfAllOrders() {
        Tag tag = new Tag();
        Long id = null;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statementForUser = connection.prepareStatement(SELECT_FOR_USER_ID);
            ResultSet resultSetWithUser = statementForUser.executeQuery();
            while (resultSetWithUser.next()) {
                id = resultSetWithUser.getLong(Columns.USER_ID);
            }
            statementForUser.close();
            PreparedStatement statementForTag = connection.prepareStatement(SELECT_FOR_TAG);
            statementForTag.setLong(1,id);
            ResultSet resultSetWithTag =statementForTag.executeQuery();
            while (resultSetWithTag.next()) {
                tag.setName(resultSetWithTag.getString(Columns.TAG_NAME));
            }
            statementForTag.close();

            return Optional.ofNullable(tag);

        } catch (SQLException e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }
}


