package com.epam.esm.model.service;

import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.persistance.entity.Order;
import com.epam.esm.persistance.entity.User;

import java.util.List;

public interface OrderService {

    Order create (OrderDTO orderDTO);

    List<OrderDTO> getAllOrdersByUserId(Long id);
}
