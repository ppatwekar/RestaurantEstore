package com.estore.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.FoodDish;
import com.estore.api.estoreapi.model.ShoppingCartFiles.ShoppingCartItemFull;

@Tag("Model-tier")
public class ShoppingCartItemFullTest {

    @Test
    public void testConstructor() {
        FoodDish dish = new FoodDish(1, "Test", 10, 11, "Test1", "Test2", "Test3");
        ShoppingCartItemFull shoppingCartItemFull = new ShoppingCartItemFull(dish, 2);

        assertEquals(dish, shoppingCartItemFull.getFoodDish());
        assertEquals(2, shoppingCartItemFull.getQuantity());
    }

    @Test
    public void testSetQuantity() {
        FoodDish dish = new FoodDish(1, "Test", 10, 11, "Test1", "Test2", "Test3");
        ShoppingCartItemFull shoppingCartItemFull = new ShoppingCartItemFull(dish, 2);

        int expected_quantity = 4;
        shoppingCartItemFull.setQuantity(expected_quantity);

        assertEquals(expected_quantity, shoppingCartItemFull.getQuantity());
    }

    @Test
    public void testIncrementQuantity() {
        FoodDish dish = new FoodDish(1, "Test", 10, 11, "Test1", "Test2", "Test3");
        ShoppingCartItemFull shoppingCartItemFull = new ShoppingCartItemFull(dish, 2);

        shoppingCartItemFull.incrementQuantity();

        assertEquals(3, shoppingCartItemFull.getQuantity());
    }

    @Test
    public void testClone() throws CloneNotSupportedException {
        FoodDish dish = new FoodDish(1, "Test", 10, 11, "Test1", "Test2", "Test3");
        ShoppingCartItemFull shoppingCartItemFull = new ShoppingCartItemFull(dish, 2);

        ShoppingCartItemFull shoppingCartItemFull2 = (ShoppingCartItemFull) shoppingCartItemFull.clone();

        assertEquals(shoppingCartItemFull2.getFoodDish(), shoppingCartItemFull.getFoodDish());
        assertEquals(shoppingCartItemFull2.getQuantity(), shoppingCartItemFull.getQuantity());
    }

    @Test
    public void testHashCode() {
        FoodDish dish = new FoodDish(1, "Test", 10, 11, "Test1", "Test2", "Test3");
        ShoppingCartItemFull shoppingCartItemFull = new ShoppingCartItemFull(dish, 2);

        assertEquals(dish.getID(), shoppingCartItemFull.hashCode());
    }

    @Test
    public void testEqualsFalse() {
        FoodDish dish = new FoodDish(1, "Test", 10, 11, "Test1", "Test2", "Test3");
        ShoppingCartItemFull shoppingCartItemFull = new ShoppingCartItemFull(dish, 2);
        boolean result = shoppingCartItemFull.equals(null);

        assertEquals(false, result);
    }

    @Test
    public void testEqualsFalse2() {
        FoodDish dish = new FoodDish(1, "Test", 10, 11, "Test1", "Test2", "Test3");
        ShoppingCartItemFull shoppingCartItemFull = new ShoppingCartItemFull(dish, 2);
        boolean result = shoppingCartItemFull.equals(dish);

        assertEquals(false, result);
    }

    @Test
    public void testEqualsFalseDish() {
        FoodDish dish = new FoodDish(1, "Test", 10, 11, "Test1", "Test2", "Test3");
        FoodDish dish2 = new FoodDish(2, "Test2", 10, 11, "Test1", "Test2", "Test3");
        ShoppingCartItemFull shoppingCartItemFull = new ShoppingCartItemFull(dish, 2);
        ShoppingCartItemFull shoppingCartItemFull2 = new ShoppingCartItemFull(dish2, 2);
        boolean result = shoppingCartItemFull.equals(shoppingCartItemFull2);

        assertEquals(false, result);
    }

    @Test
    public void testEqualsFalseQuantity() {
        FoodDish dish = new FoodDish(1, "Test", 10, 11, "Test1", "Test2", "Test3");
        ShoppingCartItemFull shoppingCartItemFull = new ShoppingCartItemFull(dish, 2);
        ShoppingCartItemFull shoppingCartItemFull2 = new ShoppingCartItemFull(dish, 3);
        boolean result = shoppingCartItemFull.equals(shoppingCartItemFull2);

        assertEquals(false, result);
    }
}
