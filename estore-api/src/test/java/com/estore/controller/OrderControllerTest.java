package com.estore.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.estore.api.estoreapi.controller.OrderController;
import com.estore.api.estoreapi.model.FoodDish;
import com.estore.api.estoreapi.model.Order;
import com.estore.api.estoreapi.services.OrderService;

@Tag("Controller-tier")
public class OrderControllerTest {
    private OrderController orderController;
    private OrderService mockOrderService;

    @BeforeEach
    public void setupOrderController() {
        mockOrderService = mock(OrderService.class);
        orderController = new OrderController(mockOrderService);
    }

    @Test
    public void testGetOrderByID() {
        FoodDish dish = new FoodDish(0, "Test", 10, 11, "Test1", "Test2", "Test3");
        FoodDish[] dishes = new FoodDish[1];
        dishes[0] = dish;

        Order order = new Order(1001, 3, dishes);
        when(mockOrderService.findById(order.getId())).thenReturn(order);

        ResponseEntity<Order> response = orderController.getOrderById(order.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order, response.getBody());
    }

    @Test
    public void testGetOrderByIDNotFound() {
        FoodDish dish = new FoodDish(0, "Test", 10, 11, "Test1", "Test2", "Test3");
        FoodDish[] dishes = new FoodDish[1];
        dishes[0] = dish;

        Order order = new Order(1001, 3, dishes);
        when(mockOrderService.findById(order.getId())).thenReturn(null);

        ResponseEntity<Order> response = orderController.getOrderById(order.getId());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetOrdersByCustomerID() {
        FoodDish dish = new FoodDish(0, "Test", 10, 11, "Test1", "Test2", "Test3");
        FoodDish[] dishes = new FoodDish[1];
        dishes[0] = dish;

        Order[] orders = new Order[1];
        orders[0] = new Order(1001, 3, dishes);
        when(mockOrderService.findByCustomerId(3)).thenReturn(orders);

        ResponseEntity<Order[]> response = orderController.getOrders(3);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orders, response.getBody());
    }

    @Test
    public void testCreateOrder() throws IOException {
        FoodDish dish = new FoodDish(0, "Test", 10, 11, "Test1", "Test2", "Test3");
        FoodDish[] dishes = new FoodDish[1];
        dishes[0] = dish;

        Order order = new Order(1001, 3, dishes);
        when(mockOrderService.createOrder(3, dishes)).thenReturn(order);

        ResponseEntity<Order> response = orderController.createOrder(order);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(order, response.getBody());
    }

    @Test
    public void testCreateOrderConflict() throws IOException {
        FoodDish dish = new FoodDish(0, "Test", 10, 11, "Test1", "Test2", "Test3");
        FoodDish[] dishes = new FoodDish[1];
        dishes[0] = dish;

        Order order = new Order(1001, 3, dishes);
        when(mockOrderService.createOrder(3, dishes)).thenReturn(null);

        ResponseEntity<Order> response = orderController.createOrder(order);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testCreateOrderExceptionHandler() throws IOException {
        FoodDish dish = new FoodDish(0, "Test", 10, 11, "Test1", "Test2", "Test3");
        FoodDish[] dishes = new FoodDish[1];
        dishes[0] = dish;

        Order order = new Order(1001, 3, dishes);
        doThrow(new IOException()).when(mockOrderService).createOrder(3, dishes);

        ResponseEntity<Order> response = orderController.createOrder(order);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteOrder() throws IOException {
        FoodDish dish = new FoodDish(0, "Test", 10, 11, "Test1", "Test2", "Test3");
        FoodDish[] dishes = new FoodDish[1];
        dishes[0] = dish;

        Order order = new Order(1001, 3, dishes);
        when(mockOrderService.deleteById(order.getId())).thenReturn(true);

        ResponseEntity<Order> response = orderController.deleteOrder(order.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteOrderNotFound() throws IOException {
        FoodDish dish = new FoodDish(0, "Test", 10, 11, "Test1", "Test2", "Test3");
        FoodDish[] dishes = new FoodDish[1];
        dishes[0] = dish;

        Order order = new Order(1001, 3, dishes);
        when(mockOrderService.deleteById(order.getId())).thenReturn(false);

        ResponseEntity<Order> response = orderController.deleteOrder(order.getId());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteOrderExceptionHandler() throws IOException {
        FoodDish dish = new FoodDish(0, "Test", 10, 11, "Test1", "Test2", "Test3");
        FoodDish[] dishes = new FoodDish[1];
        dishes[0] = dish;

        Order order = new Order(1001, 3, dishes);
        doThrow(new IOException()).when(mockOrderService).deleteById(order.getId());

        ResponseEntity<Order> response = orderController.deleteOrder(order.getId());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

}
