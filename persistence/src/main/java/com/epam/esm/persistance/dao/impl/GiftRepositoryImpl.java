package com.epam.esm.persistance.dao.impl;


import com.epam.esm.persistance.dao.GiftRepository;
import com.epam.esm.persistance.dao.mapper.Columns;
import com.epam.esm.persistance.dao.mapper.GiftMapper;
import com.epam.esm.persistance.dao.mapper.TagMapper;
import com.epam.esm.persistance.entity.GiftCertificate;
import com.epam.esm.persistance.entity.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public class GiftRepositoryImpl implements GiftRepository {

    private static final Logger log = LogManager.getLogger(GiftRepositoryImpl.class);

    private final DataSource dataSource;

    @Autowired
    public GiftRepositoryImpl(DataSource dataSource
    ) {
        this.dataSource = dataSource;
    }

    @Override
    public GiftCertificate save(GiftCertificate gift) {
        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO gifts " +
                            "(gift_name, description, price," +
                            " duration, create_date, last_update_date)" +
                            " VALUES(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            connection.setAutoCommit(false);
            statement.setString(1, gift.getName());
            statement.setString(2, gift.getDescription());
            statement.setLong(3, gift.getPrice());
            statement.setLong(4, gift.getDuration());
            statement.setString(5, gift.getCreateDate());
            statement.setString(6, gift.getLastUpdateDate());
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating gift failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    gift.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating gift failed, no ID obtained.");

                }
            }

            PreparedStatement manyToMany = connection.prepareStatement
                    ("INSERT INTO gifts_tags(gift_id, tag_id) VALUES(?,?) ");

            for (Tag tag : gift.getTags()) {
                manyToMany.setLong(1, gift.getId());
                manyToMany.setLong(2, tag.getId());
                manyToMany.execute();
            }
            connection.commit();
            connection.setAutoCommit(true);
            statement.close();
            manyToMany.close();
            return gift;

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {

        GiftCertificate giftCertificate = null;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM gifts WHERE id =?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                giftCertificate = (GiftMapper.extractGift(resultSet,findByGiftId(resultSet.getLong(Columns.ID))));
            }
            statement.close();

            return Optional.ofNullable(giftCertificate);

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    @Override
    public GiftCertificate findByName(String name) {
        GiftCertificate giftCertificate = new GiftCertificate();
        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM gifts WHERE gift_name =?");
            statement.setString(1,name);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                giftCertificate = (GiftMapper.extractGift(resultSet,findByGiftId(resultSet.getLong(Columns.ID))));

            }
            statement.close();

            return giftCertificate;


        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsByName(String name) {
        try (Connection connection = dataSource.getConnection()) {

            boolean existsByName;

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM gifts WHERE gift_name =?");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            existsByName = resultSet.next();
            statement.close();

            return existsByName;

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }


    }


    @Override
    public List<GiftCertificate> findAll() {

        List<GiftCertificate> giftCertificateList = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM gifts");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                giftCertificateList.add(GiftMapper.extractGift(resultSet,findByGiftId(resultSet.getLong(Columns.ID))));
            }

            statement.close();

        } catch (SQLException e) {

            log.error(e.getMessage());
        }
        return giftCertificateList;
    }

    @Override
    public List<GiftCertificate> findAllByTag(Long id ,Long page) {

        log.info("tag id = {}",id);

        List<GiftCertificate> giftCertificateList = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM  gifts " +
                            "JOIN gifts_tags gt on gifts.id = gt.gift_id WHERE tag_id = ? " +
                            "ORDER BY gift_name ASC LIMIT 50 OFFSET ?");
            statement.setLong(1, id);
            statement.setLong(2,page);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                giftCertificateList.add(GiftMapper.extractGift(resultSet,findByGiftId(resultSet.getLong(Columns.GIFT_ID))));
            }

            statement.close();

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }


        return giftCertificateList;
    }

    @Override
    public List<GiftCertificate> findAllByPartOfDescription(String part) {
        List<GiftCertificate> giftCertificateList = new ArrayList<>();

        String partOfDescription = String.valueOf(new StringBuilder().append("%").append(part).append("%"));

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * from gifts where gifts.description LIKE ? ORDER BY gift_name ASC ");
            statement.setString(1, partOfDescription);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                giftCertificateList.add(GiftMapper.extractGift(resultSet,findByGiftId(resultSet.getLong(Columns.ID))));
            }

            statement.close();

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
        return giftCertificateList;
    }

    @Override
    public GiftCertificate update(GiftCertificate gift) {
        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE gifts SET gift_name = ? , description = ?," +
                            "price =?,duration =?,last_update_date = ?  WHERE id = ?");

            statement.setString(1, gift.getName());
            statement.setString(2, gift.getDescription());
            statement.setLong(3, gift.getPrice());
            statement.setLong(4, gift.getDuration());
            statement.setString(5,gift.getLastUpdateDate());
            statement.setLong(6, gift.getId());
            statement.executeUpdate();
            statement.close();


        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
        return gift;
    }

    @Override
    public void delete(Long id) {

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement("DELETE FROM gifts WHERE id =?");
            statement.setLong(1, id);
            statement.execute();
            statement.close();

        } catch (Exception e) {

            throw new RuntimeException(e);
        }


    }

    private List<Tag> findByGiftId(Long id) {

        List<Tag> tags = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM tags " +
                    "JOIN gifts_tags gt on tags.id = gt.tag_id WHERE gift_id=?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();


            while (resultSet.next()) {
                tags.add(TagMapper.extractTag(resultSet));
            }

            statement.close();

            return tags;

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }
}
