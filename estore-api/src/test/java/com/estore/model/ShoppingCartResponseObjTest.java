package com.estore.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.FoodDish;
import com.estore.api.estoreapi.model.ShoppingCartFiles.ShoppingCartItemFull;
import com.estore.api.estoreapi.model.ShoppingCartFiles.ShoppingCartResponseObj;

@Tag("Model-tier")
public class ShoppingCartResponseObjTest {
    @Test
    public void testConstructor() {
        FoodDish dish = new FoodDish(1, "Test", 10, 11, "Test1", "Test2", "Test3");
        ShoppingCartItemFull shoppingCartItemFull = new ShoppingCartItemFull(dish, 2);
        List<ShoppingCartItemFull> items = new ArrayList<ShoppingCartItemFull>();
        items.add(shoppingCartItemFull);
        ShoppingCartResponseObj shoppingCartResponseObj = new ShoppingCartResponseObj(items, 2);

        assertEquals(1, shoppingCartResponseObj.getShoppingCartItems().size());
    }

    @Test
    public void testClone() throws CloneNotSupportedException {
        FoodDish dish = new FoodDish(1, "Test", 10, 11, "Test1", "Test2", "Test3");
        ShoppingCartItemFull shoppingCartItemFull = new ShoppingCartItemFull(dish, 2);
        List<ShoppingCartItemFull> items = new ArrayList<ShoppingCartItemFull>();
        items.add(shoppingCartItemFull);
        ShoppingCartResponseObj shoppingCartResponseObj = new ShoppingCartResponseObj(items, 2);
        ShoppingCartResponseObj shoppingCartResponseObj2 = (ShoppingCartResponseObj) shoppingCartResponseObj.clone();

        assertEquals(shoppingCartResponseObj.getShoppingCartItems().size(),
                shoppingCartResponseObj2.getShoppingCartItems().size());
    }

    @Test
    public void testHashCode() {
        FoodDish dish = new FoodDish(1, "Test", 10, 11, "Test1", "Test2", "Test3");
        ShoppingCartItemFull shoppingCartItemFull = new ShoppingCartItemFull(dish, 2);
        List<ShoppingCartItemFull> items = new ArrayList<ShoppingCartItemFull>();
        items.add(shoppingCartItemFull);
        ShoppingCartResponseObj shoppingCartResponseObj = new ShoppingCartResponseObj(items, 2);

        assertEquals(items.hashCode(), shoppingCartResponseObj.hashCode());
    }

    @Test
    public void testEqualsFalse() {
        FoodDish dish = new FoodDish(1, "Test", 10, 11, "Test1", "Test2", "Test3");
        ShoppingCartItemFull shoppingCartItemFull = new ShoppingCartItemFull(dish, 2);
        List<ShoppingCartItemFull> items = new ArrayList<ShoppingCartItemFull>();
        items.add(shoppingCartItemFull);
        ShoppingCartResponseObj shoppingCartResponseObj = new ShoppingCartResponseObj(items, 2);
        boolean result = shoppingCartResponseObj.equals(null);

        assertEquals(false, result);
    }

    @Test
    public void testEqualsFalse2() {
        FoodDish dish = new FoodDish(1, "Test", 10, 11, "Test1", "Test2", "Test3");
        ShoppingCartItemFull shoppingCartItemFull = new ShoppingCartItemFull(dish, 2);
        List<ShoppingCartItemFull> items = new ArrayList<ShoppingCartItemFull>();
        items.add(shoppingCartItemFull);
        ShoppingCartResponseObj shoppingCartResponseObj = new ShoppingCartResponseObj(items, 2);

        FoodDish dish2 = new FoodDish(2, "Test2", 10, 11, "Test1", "Test2", "Test3");
        ShoppingCartItemFull shoppingCartItemFull2 = new ShoppingCartItemFull(dish2, 2);
        List<ShoppingCartItemFull> items2 = new ArrayList<ShoppingCartItemFull>();
        items2.add(shoppingCartItemFull2);
        ShoppingCartResponseObj shoppingCartResponseObj2 = new ShoppingCartResponseObj(items2, 2);

        boolean result = shoppingCartResponseObj.equals(shoppingCartResponseObj2);

        assertEquals(false, result);
    }

    @Test
    public void testEqualsFalseSize() {
        FoodDish dish = new FoodDish(1, "Test", 10, 11, "Test1", "Test2", "Test3");
        ShoppingCartItemFull shoppingCartItemFull = new ShoppingCartItemFull(dish, 2);
        List<ShoppingCartItemFull> items = new ArrayList<ShoppingCartItemFull>();
        items.add(shoppingCartItemFull);
        ShoppingCartResponseObj shoppingCartResponseObj = new ShoppingCartResponseObj(items, 2);

        FoodDish dish2 = new FoodDish(2, "Test2", 10, 11, "Test1", "Test2", "Test3");
        ShoppingCartItemFull shoppingCartItemFull2 = new ShoppingCartItemFull(dish2, 2);
        List<ShoppingCartItemFull> items2 = new ArrayList<ShoppingCartItemFull>();
        items2.add(shoppingCartItemFull);
        items2.add(shoppingCartItemFull2);
        ShoppingCartResponseObj shoppingCartResponseObj2 = new ShoppingCartResponseObj(items2, 4);

        boolean result = shoppingCartResponseObj.equals(shoppingCartResponseObj2);

        assertEquals(false, result);
    }

    @Test
    public void testEqualsFalseQuantity() {
        FoodDish dish = new FoodDish(1, "Test", 10, 11, "Test1", "Test2", "Test3");
        ShoppingCartItemFull shoppingCartItemFull = new ShoppingCartItemFull(dish, 2);
        List<ShoppingCartItemFull> items = new ArrayList<ShoppingCartItemFull>();
        items.add(shoppingCartItemFull);
        ShoppingCartResponseObj shoppingCartResponseObj = new ShoppingCartResponseObj(items, 2);

        ShoppingCartItemFull shoppingCartItemFull2 = new ShoppingCartItemFull(dish, 2);
        List<ShoppingCartItemFull> items2 = new ArrayList<ShoppingCartItemFull>();
        items2.add(shoppingCartItemFull2);
        ShoppingCartResponseObj shoppingCartResponseObj2 = new ShoppingCartResponseObj(items2, 4);

        boolean result = shoppingCartResponseObj.equals(shoppingCartResponseObj2);

        assertEquals(false, result);
    }
}
