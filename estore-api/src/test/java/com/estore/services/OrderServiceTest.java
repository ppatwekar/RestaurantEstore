package com.estore.services;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.FoodDish;
import com.estore.api.estoreapi.model.Order;
import com.estore.api.estoreapi.model.ShoppingCartFiles.*;
import com.estore.api.estoreapi.persistence.DAOFiles.FoodDishFileDAO;
import com.estore.api.estoreapi.persistence.DAOFiles.OrderFileDAO;
import com.estore.api.estoreapi.persistence.DAOFiles.ShoppingCartFileDAO;
import com.estore.api.estoreapi.services.OrderService;

@Tag("Service-tier")
public class OrderServiceTest {
    private OrderFileDAO mockOrderFileDAO;
    private ShoppingCartFileDAO mockShoppingCartFileDAO;
    private FoodDishFileDAO mockFoodDishFileDAO;
    private OrderService orderService;

    @BeforeEach
    public void setupOrderController() {
        mockOrderFileDAO = mock(OrderFileDAO.class);
        mockShoppingCartFileDAO = mock(ShoppingCartFileDAO.class);
        mockFoodDishFileDAO = mock(FoodDishFileDAO.class);
        orderService = new OrderService(mockOrderFileDAO, mockShoppingCartFileDAO, mockFoodDishFileDAO);
    }

    @Test
    public void testFindById() {
        FoodDish dish = new FoodDish(0, "Test", 10, 11, "Test1", "Test2", "Test3");
        FoodDish[] dishes = new FoodDish[1];
        dishes[0] = dish;

        Order order = new Order(1001, 3, dishes);
        when(mockOrderFileDAO.findById(order.getId())).thenReturn(order);

        Order result = orderService.findById(order.getId());

        assertEquals(result, order);
    }

    @Test
    public void testFindByCustomerId() {
        FoodDish dish = new FoodDish(0, "Test", 10, 11, "Test1", "Test2", "Test3");
        FoodDish[] dishes = new FoodDish[1];
        dishes[0] = dish;

        Order[] orders = new Order[1];
        orders[0] = new Order(1001, 3, dishes);
        when(mockOrderFileDAO.findByCustomerId(3)).thenReturn(orders);

        Order[] results = orderService.findByCustomerId(3);

        assertEquals(results, orders);
    }

    @Test
    public void testCreateOrder() throws IOException {
        FoodDish dish = new FoodDish(0, "Test", 10, 11, "Test1", "Test2", "Test3");
        FoodDish dish2 = new FoodDish(-1, "Test2", 10, 11, "Test1", "Test2", "Test3");
        FoodDish[] dishes = new FoodDish[2];
        dishes[0] = dish;
        dishes[1] = dish2;

        Order order = new Order(1001, 3, dishes);

        FoodDish inventoryDish = new FoodDish(0, "Test", 10, 21, "Test1", "Test2", "Test3");
        FoodDish changedDish = new FoodDish(0, "Test", 10, 10, "Test1", "Test2", "Test3");

        ShoppingCart cart = new ShoppingCart(3, null, null);

        when(mockFoodDishFileDAO.getFoodDish(0)).thenReturn(inventoryDish);
        when(mockFoodDishFileDAO.updateFoodDish(changedDish)).thenReturn(changedDish);
        when(mockOrderFileDAO.createOrder(3, dishes)).thenReturn(order);
        when(mockShoppingCartFileDAO.clearShoppingCart(3)).thenReturn(cart);

        Order result = orderService.createOrder(3, dishes);

        assertEquals(result, order);
    }

    @Test
    public void testCreateOrderFail() throws IOException {
        FoodDish dish = new FoodDish(0, "Test", 10, 11, "Test1", "Test2", "Test3");
        FoodDish[] dishes = new FoodDish[1];
        dishes[0] = dish;

        Order order = new Order(1001, 3, dishes);

        FoodDish inventoryDish = new FoodDish(0, "Test", 10, 1, "Test1", "Test2", "Test3");
        FoodDish changedDish = new FoodDish(0, "Test", 10, 10, "Test1", "Test2", "Test3");

        ShoppingCart cart = new ShoppingCart(3, null, null);

        when(mockFoodDishFileDAO.getFoodDish(0)).thenReturn(inventoryDish);
        when(mockFoodDishFileDAO.updateFoodDish(changedDish)).thenReturn(changedDish);
        when(mockOrderFileDAO.createOrder(3, dishes)).thenReturn(order);
        when(mockShoppingCartFileDAO.clearShoppingCart(3)).thenReturn(cart);

        Order result = orderService.createOrder(3, dishes);

        assertEquals(null, result);
    }

    @Test
    public void testCreateOrderHandleException() throws IOException {
        FoodDish dish = new FoodDish(0, "Test", 10, 11, "Test1", "Test2", "Test3");
        FoodDish[] dishes = new FoodDish[1];
        dishes[0] = dish;

        Order order = new Order(1001, 3, dishes);

        FoodDish inventoryDish = new FoodDish(0, "Test", 10, 21, "Test1", "Test2", "Test3");
        FoodDish changedDish = new FoodDish(0, "Test", 10, 10, "Test1", "Test2", "Test3");

        ShoppingCart cart = new ShoppingCart(3, null, null);

        doThrow(new IOException()).when(mockFoodDishFileDAO).getFoodDish(0);
        when(mockFoodDishFileDAO.updateFoodDish(changedDish)).thenReturn(changedDish);
        when(mockOrderFileDAO.createOrder(3, dishes)).thenReturn(order);
        when(mockShoppingCartFileDAO.clearShoppingCart(3)).thenReturn(cart);

        Order result = orderService.createOrder(3, dishes);

        assertEquals(null, result);
    }

    @Test
    public void testDeleteById() throws IOException{
        when(mockOrderFileDAO.deleteById(1001)).thenReturn(true);

        boolean result = orderService.deleteById(1001);
        
        assertEquals(true, result);
    }

    @Test
    public void testDeleteByIdHandleException() throws IOException {
        doThrow(new IOException()).when(mockOrderFileDAO).deleteById(1001);

        boolean result = orderService.deleteById(1001);
        assertEquals(false, result);
    }
}
