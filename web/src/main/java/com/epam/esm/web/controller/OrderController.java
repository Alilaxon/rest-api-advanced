package com.epam.esm.web.controller;

import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.exception.NoSuchGiftException;
import com.epam.esm.model.exception.NoSuchUserException;
import com.epam.esm.model.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {

        this.orderService = orderService;
    }

    @RequestMapping("/get-all")
    public List<OrderDTO> getAllByUserId(@RequestParam("id") Long id) {

        return orderService.getAllOrdersByUserId(id);
    }

    @RequestMapping("/create")
    public ResponseEntity<Long> create(@RequestBody OrderDTO orderDTO)
            throws NoSuchUserException, NoSuchGiftException {
        Long id = orderService.create(orderDTO).getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(id);

    }

    @GetMapping("/fillDataBase")
    public Integer fillDataBase() {
        orderService.fillTable();
        return orderService.getAll().size();
    }
}
