package com.epam.esm.web.controller;

import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.exception.NoSuchGiftException;
import com.epam.esm.model.exception.NoSuchUserException;
import com.epam.esm.model.service.OrderService;
import com.epam.esm.web.utils.UrlParts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(UrlParts.ORDERS)
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping("/get-all")
    public List<OrderDTO> getAllByUserId(Principal principal) {
        return orderService.getAllOrdersByUser(principal.getName());
    }

    @RequestMapping(UrlParts.CREATE)
    public ResponseEntity<Long> create(@RequestBody OrderDTO orderDTO, Principal principal)
            throws NoSuchUserException, NoSuchGiftException {
          log.info(principal.getName());
        Long id = orderService.create(orderDTO, principal.getName()).getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @GetMapping(UrlParts.FILL_DATABASE)
    public ResponseEntity<String> fillDataBase() {
        orderService.fillTable();
        int numberOfOrders = orderService.getAll().size();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(numberOfOrders + " orders have been created");
    }

    @GetMapping(UrlParts.CLEAN_DATABASE)
    public ResponseEntity<String> cleanDataBase() {
        orderService.cleanTable();
        int numberOfOrders = orderService.getAll().size();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(numberOfOrders + " orders have been found after cleaning");
    }
}
