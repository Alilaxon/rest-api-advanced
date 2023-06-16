package com.epam.esm.persistance.dao;

import com.epam.esm.persistance.entity.Order;

import java.util.List;

public interface OrderRepository extends DefaultRepository<Order>{

  List<Order> getAllByUserId(Long id);
}
