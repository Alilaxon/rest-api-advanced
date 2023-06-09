package com.epam.esm.persistance.dao.impl;

import com.epam.esm.persistance.dao.OrderRepository;
import com.epam.esm.persistance.entity.Order;

import java.util.List;
import java.util.Optional;

public class OrderRepositoryImpl implements OrderRepository {
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

    }

    @Override
    public void delete(Order order) {

    }
}
