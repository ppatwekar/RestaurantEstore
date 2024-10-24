package com.estore.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import com.estore.api.estoreapi.model.FoodDish;
import com.estore.api.estoreapi.model.Order;
import com.estore.api.estoreapi.persistence.DAOFiles.OrderFileDAO;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Persistence-tier")
public class OrderFileDAOTest {
    OrderFileDAO orderFileDAO;
    Order[] testOrders;
    ObjectMapper mockObjectMapper;

    @BeforeEach
    public void setupOrderFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        FoodDish dish = new FoodDish(0, "Test", 10, 11, "Test1", "Test2", "Test3");
        FoodDish[] dishes = new FoodDish[1];
        dishes[0] = dish;

        FoodDish dish2 = new FoodDish(1, "Test1", 10, 11, "Test1", "Test2", "Test3");
        FoodDish[] dishes2 = new FoodDish[1];
        dishes2[0] = dish2;

        FoodDish dish3 = new FoodDish(2, "Test2", 10, 11, "Test1", "Test2", "Test3");
        FoodDish[] dishes3 = new FoodDish[1];
        dishes3[0] = dish3;

        testOrders = new Order[3];
        testOrders[0] = new Order(1001, 3, dishes);
        testOrders[1] = new Order(1002, 4, dishes2);
        testOrders[2] = new Order(1003, 3, dishes3);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the Order array above
        when(mockObjectMapper
                .readValue(new File("doesnt_matter.txt"), Order[].class))
                .thenReturn(testOrders);
        orderFileDAO = new OrderFileDAO("doesnt_matter.txt", mockObjectMapper);
    }

    @Test
    public void testFindByID() {
        Order order = orderFileDAO.findById(1001);

        assertEquals(order, testOrders[0]);
    }

    @Test
    public void testFindByCustomerID() {
        Order[] orders = orderFileDAO.findByCustomerId(4);

        assertEquals(orders[0], testOrders[1]);
    }

    @Test
    public void testDeleteByID() {
        boolean result = assertDoesNotThrow(() -> orderFileDAO.deleteById(1001),
                "Unexpected exception thrown");

        assertEquals(true, result);
        assertEquals(1, orderFileDAO.findByCustomerId(3).length);
    }

    @Test
    public void testCreateOrder() throws IOException {
        FoodDish dish = new FoodDish(0, "Test", 10, 11, "Test1", "Test2", "Test3");
        FoodDish[] dishes = new FoodDish[1];
        dishes[0] = dish;

        Order newOrder = orderFileDAO.createOrder(5, dishes);

        assertEquals(1004, newOrder.getId());
        assertEquals(5, newOrder.getCustomerId());
        assertEquals(dish, newOrder.getDishes()[0]);
    }

    @Test
    public void testFindByIDNotFound() {
        Order order = orderFileDAO.findById(101);
        assertEquals(null, order);
    }

    @Test
    public void testDeleteByIDNotFound() throws IOException {
        boolean result = orderFileDAO.deleteById(101);

        assertEquals(false, result);
    }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        // We want to simulate with a Mock Object Mapper that an
        // exception was raised during JSON object deseerialization
        // into Java objects
        // When the Mock Object Mapper readValue method is called
        // from the OrderFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
                .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"), Order[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                () -> new OrderFileDAO("doesnt_matter.txt", mockObjectMapper),
                "IOException not thrown");
    }
}
