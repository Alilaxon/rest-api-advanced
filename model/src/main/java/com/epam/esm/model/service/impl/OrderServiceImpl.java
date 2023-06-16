package com.epam.esm.model.service.impl;

import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.service.OrderService;
import com.epam.esm.persistance.dao.OrderRepository;
import com.epam.esm.persistance.dao.builders.OrderBuilder;
import com.epam.esm.persistance.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final DateTimeFormatter FORMATTER;


    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            DateTimeFormatter FORMATTER) {
        this.orderRepository = orderRepository;
        this.FORMATTER = FORMATTER;
    }

    @Override
    public Order create(OrderDTO orderDTO) {
        Order order = OrderBuilder.builder()
                .userId(orderDTO.getUserId())
                .giftId(orderDTO.getGiftId())
                .price(orderDTO.getPrice())
                .createDate(LocalDateTime.now().format(FORMATTER))
                .build();
        orderRepository.save(order);
        return order;
    }

    @Override
    public List<OrderDTO> getAllOrdersByUserId(Long id) {

        return orderRepository.getAllByUserId(id)
                .stream()
                .map(order -> new OrderDTO(
                        order.getId(),
                        order.getGiftId(),
                        order.getUserId(),
                        order.getPrice(),
                        order.getCreateDate()))
                .collect(Collectors.toList());
    }
}
