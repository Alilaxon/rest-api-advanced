package com.epam.esm.model.service;

import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.exception.NoSuchGiftException;
import com.epam.esm.model.exception.NoSuchUserException;
import com.epam.esm.persistance.entity.Order;

import java.util.List;

public interface OrderService extends TableFiller {

    Order create (OrderDTO orderDTO ,String name) throws NoSuchUserException, NoSuchGiftException;

    List<OrderDTO> getAllOrdersByUser(String name);

    List<OrderDTO> getAll();
}
