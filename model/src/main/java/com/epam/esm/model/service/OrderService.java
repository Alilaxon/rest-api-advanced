package com.epam.esm.model.service;

import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.exception.NoSuchGiftException;
import com.epam.esm.model.exception.NoSuchUserException;
import com.epam.esm.persistance.entity.Order;
import com.epam.esm.persistance.entity.User;

import java.util.List;

public interface OrderService extends TableFiller {

    Order create (OrderDTO orderDTO) throws NoSuchUserException, NoSuchGiftException;

    List<OrderDTO> getAllOrdersByUserId(Long id);

    List<OrderDTO> getAll();
}
