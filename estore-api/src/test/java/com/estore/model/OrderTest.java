package com.estore.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.FoodDish;
import com.estore.api.estoreapi.model.Order;

@Tag("Model-tier")
public class OrderTest {
    @Test
    public void testConstructor() {
        int expected_id = 100;
        int expected_customerId = 34;

        FoodDish dish = new FoodDish(0, "Test", 10, 11, "Test1", "Test2", "Test3");
        FoodDish[] dishes = new FoodDish[1];
        dishes[0] = dish;

        Order order = new Order(expected_id, expected_customerId, dishes);

        assertEquals(expected_id, order.getId());
        assertEquals(expected_customerId, order.getCustomerId());
        assertEquals(dishes[0], order.getDishes()[0]);
    }

    @Test
    public void testSetID() {
        int id = 100;
        int customerId = 34;

        FoodDish dish = new FoodDish(0, "Test", 10, 11, "Test1", "Test2", "Test3");
        FoodDish[] dishes = new FoodDish[1];
        dishes[0] = dish;

        Order order = new Order(id, customerId, dishes);

        int expected_id = 101;

        order.SetId(expected_id);

        assertEquals(expected_id, order.getId());
    }

    @Test
    public void testSetCustomerID() {
        int id = 100;
        int customerId = 34;

        FoodDish dish = new FoodDish(0, "Test", 10, 11, "Test1", "Test2", "Test3");
        FoodDish[] dishes = new FoodDish[1];
        dishes[0] = dish;

        Order order = new Order(id, customerId, dishes);

        int expected_customerId = 35;

        order.setCustomerId(expected_customerId);

        assertEquals(expected_customerId, order.getCustomerId());
    }

    @Test
    public void testSetDishes() {
        int id = 100;
        int customerId = 34;

        FoodDish dish = new FoodDish(0, "Test", 10, 11, "Test1", "Test2", "Test3");
        FoodDish[] dishes = new FoodDish[1];
        dishes[0] = dish;

        Order order = new Order(id, customerId, dishes);

        FoodDish dish2 = new FoodDish(1, "Test", 10, 11, "Test1", "Test2", "Test3");
        FoodDish[] dishes2 = new FoodDish[1];
        dishes2[0] = dish2;

        order.setDishes(dishes2);

        assertEquals(dishes2[0], order.getDishes()[0]);
    }
}
