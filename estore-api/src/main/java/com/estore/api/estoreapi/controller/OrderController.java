package com.estore.api.estoreapi.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estore.api.estoreapi.model.Order;
import com.estore.api.estoreapi.persistence.DAOs.OrderDAO;
import com.estore.api.estoreapi.services.OrderService;

/**
 * Handles the REST API requests for the Order resource
 * <p>
 * {@literal RestController} Spring annotation identifies this class as a REST
 * API
 * method handler to the Spring framework
 * 
 * @author Jiangnan Xu
 */
@RestController
@RequestMapping("order")
public class OrderController {

    private OrderService orderService;

    private static final Logger LOG = Logger.getLogger(OrderController.class.getName());

    /**
     * Creates the REST API controller to allow request-response cycle.
     * 
     * @param orderDAO is the {@link OrderDAO} data access object for CRUD
     *                 operations on the data
     */
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Responds to the GET request for the {@linkplain Order} for the given id
     * 
     * @param id is the id used to locate the {@link Order}
     * @return Response entity with {@link Order} object and HTTP status of OK
     *         or HTTP status of NOT_FOUND if the resource is not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable int id) {
        LOG.info("GET /order/" + id);

        Order order = orderService.findById(id);
        if (order != null) {
            LOG.info("Length: " + order.getDishes().length);
            return new ResponseEntity<Order>(order, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Responds to the GET request for the {@linkplain Order} for the given customer
     * id
     * 
     * @param customerId is the customer id used to locate the {@link Order}
     * @return Response entity with {@link Order} object and HTTP status of OK.
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Order[]> getOrders(@PathVariable int customerId) {
        LOG.info("GET /order/customers/" + customerId);

        Order[] orders = orderService.findByCustomerId(customerId);

        return new ResponseEntity<Order[]>(orders, HttpStatus.OK);
    }

    /**
     * Creates a {@linkplain Order order} with the provided order object
     * 
     * @param body - The {@link Order order} to create
     * 
     * @return ResponseEntity with created {@link Order order} object and HTTP
     *         status
     *         of CREATED<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("/createOrder")
    public ResponseEntity<Order> createOrder(@RequestBody Order newOrder) {
        LOG.info("GET /order/customers/createOrder");
        // TO-DO

        try {
            Order createdOrder = orderService.createOrder(newOrder.getCustomerId(), newOrder.getDishes());
            if (createdOrder != null) {
                return new ResponseEntity<Order>(createdOrder, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<Order>(HttpStatus.CONFLICT);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Also need to clear the current shopping cart. Do it in the back-end.

    }

    /**
     * Deletes a {@linkplain Order order} with the given id
     * 
     * @param id The id of the {@link Order order} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Order> deleteOrder(@PathVariable int id) {
        LOG.info("Delete /order/" + id);

        try {
            boolean found = orderService.deleteById(id);
            if (found) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
