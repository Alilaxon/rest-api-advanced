package com.epam.esm.model.service.impl;

import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.exception.NoSuchGiftException;
import com.epam.esm.model.exception.NoSuchUserException;
import com.epam.esm.model.service.OrderService;
import com.epam.esm.persistance.dao.GiftRepository;
import com.epam.esm.persistance.dao.OrderRepository;
import com.epam.esm.persistance.dao.UserRepository;
import com.epam.esm.persistance.dao.builders.OrderBuilder;
import com.epam.esm.persistance.entity.GiftCertificate;
import com.epam.esm.persistance.entity.Order;
import com.epam.esm.persistance.entity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final GiftRepository giftRepository;
    private final DateTimeFormatter dateTimeFormatter;


    public OrderServiceImpl(OrderRepository orderRepository,
                            UserRepository userRepository,
                            GiftRepository giftRepository,
                            DateTimeFormatter dateTimeFormatter) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.giftRepository = giftRepository;
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    public Order create(OrderDTO orderDTO) throws NoSuchUserException, NoSuchGiftException {

        User user = userRepository.get(orderDTO.getUserId())
                .orElseThrow(() -> new NoSuchUserException(orderDTO.getId()));
        var gift = giftRepository.findById(orderDTO.getGiftId())
                .orElseThrow(() -> new NoSuchGiftException((orderDTO.getId())));
        Order order = OrderBuilder.builder()
                .userId(user.getId())
                .giftId(gift.getId())
                .price(gift.getPrice())
                .createDate(LocalDateTime.now().format(dateTimeFormatter))
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

    @Override
    public List<OrderDTO> getAll() {
        return orderRepository.getAll()
                .stream()
                .map(order -> new OrderDTO(
                        order.getId(),
                        order.getGiftId(),
                        order.getUserId(),
                        order.getPrice(),
                        order.getCreateDate()))
                .collect(Collectors.toList());

    }

    @Override
    public void fillTable() {
        List<User> userList = userRepository.getAll();
        List<GiftCertificate> giftCertificateList = giftRepository.findAll();
        for (User user : userList) {
            for (GiftCertificate gift : giftCertificateList) {
                orderRepository.save(OrderBuilder.builder()
                        .userId(user.getId())
                        .giftId(gift.getId())
                        .price(gift.getPrice())
                        .createDate(LocalDateTime.now().format(dateTimeFormatter))
                        .build());
            }
        }

    }

    @Override
    public void cleanTable() {

    }
}
