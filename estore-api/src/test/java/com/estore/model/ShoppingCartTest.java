package com.estore.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.FoodDish;
import com.estore.api.estoreapi.model.ShoppingCartFiles.*;

@Tag("Model-tier")
public class ShoppingCartTest {
    @Test
    public void testConstructor() {
        List<ShoppingCartItemDataFormat> sidfs1 = new ArrayList<ShoppingCartItemDataFormat>();
        ShoppingCartItemDataFormat sidf1 = new ShoppingCartItemDataFormat(1, 3);
        sidfs1.add(sidf1);
        List<FoodDish> dishes = new ArrayList<FoodDish>();
        FoodDish dish = new FoodDish(-1, "Test", 10, 11, "Test1", "Test2", "Test3");
        dishes.add(dish);
        ShoppingCart cart = new ShoppingCart(3, sidfs1, dishes);

        assertEquals(3, cart.getCustomerId());
    }

    @Test
    public void testGetAllCustomDishes() {
        List<ShoppingCartItemDataFormat> sidfs1 = new ArrayList<ShoppingCartItemDataFormat>();
        ShoppingCartItemDataFormat sidf1 = new ShoppingCartItemDataFormat(1, 3);
        sidfs1.add(sidf1);
        List<FoodDish> dishes = new ArrayList<FoodDish>();
        FoodDish dish = new FoodDish(-1, "Test", 10, 11, "Test1", "Test2", "Test3");
        dishes.add(dish);
        ShoppingCart cart = new ShoppingCart(3, sidfs1, dishes);

        List<FoodDish> dishes2 = cart.allCustomDishes();

        assertEquals(dishes.get(0), dishes2.get(0));
    }

    @Test
    public void testGetAllItems() {
        List<ShoppingCartItemDataFormat> sidfs1 = new ArrayList<ShoppingCartItemDataFormat>();
        ShoppingCartItemDataFormat sidf1 = new ShoppingCartItemDataFormat(1, 3);
        sidfs1.add(sidf1);
        List<FoodDish> dishes = new ArrayList<FoodDish>();
        FoodDish dish = new FoodDish(-1, "Test", 10, 11, "Test1", "Test2", "Test3");
        dishes.add(dish);
        ShoppingCart cart = new ShoppingCart(3, sidfs1, dishes);

        List<ShoppingCartItemDataFormat> items = cart.allItems();
        assertEquals(items.get(0), sidfs1.get(0));
    }

    @Test
    public void testSetTotalFoodDishes() {
        List<ShoppingCartItemDataFormat> sidfs1 = new ArrayList<ShoppingCartItemDataFormat>();
        ShoppingCartItemDataFormat sidf1 = new ShoppingCartItemDataFormat(1, 3);
        sidfs1.add(sidf1);
        List<FoodDish> dishes = new ArrayList<FoodDish>();
        FoodDish dish = new FoodDish(-1, "Test", 10, 11, "Test1", "Test2", "Test3");
        dishes.add(dish);
        ShoppingCart cart = new ShoppingCart(3, sidfs1, dishes);
        cart.setTotalFoodDishes(3);

        assertEquals(3, cart.getTotalFoodDishes());
    }

    @Test
    public void testSetCustomizedDishes() {
        List<ShoppingCartItemDataFormat> sidfs1 = new ArrayList<ShoppingCartItemDataFormat>();
        ShoppingCartItemDataFormat sidf1 = new ShoppingCartItemDataFormat(1, 3);
        sidfs1.add(sidf1);
        ShoppingCart cart = new ShoppingCart(3, sidfs1, null);
        List<FoodDish> dishes = new ArrayList<FoodDish>();
        FoodDish dish = new FoodDish(-1, "Test", 10, 11, "Test1", "Test2", "Test3");
        dishes.add(dish);
        cart.setCustomizedDishes(dishes);

        assertEquals(1, cart.allCustomDishes().size());
    }

    @Test
    public void testSetShoppingCartItems() {
        ShoppingCart cart = new ShoppingCart(3, null, null);

        List<ShoppingCartItemDataFormat> sidfs1 = new ArrayList<ShoppingCartItemDataFormat>();
        ShoppingCartItemDataFormat sidf1 = new ShoppingCartItemDataFormat(1, 3);
        sidfs1.add(sidf1);

        cart.setShoppingCartItems(sidfs1);
        assertEquals(1, cart.allItems().size());
    }

    @Test
    public void testRemoveShoppingCartItem() {
        List<ShoppingCartItemDataFormat> sidfs1 = new ArrayList<ShoppingCartItemDataFormat>();
        ShoppingCartItemDataFormat sidf1 = new ShoppingCartItemDataFormat(1, 3);
        sidfs1.add(sidf1);
        List<FoodDish> dishes = new ArrayList<FoodDish>();
        FoodDish dish = new FoodDish(-1, "Test", 10, 11, "Test1", "Test2", "Test3");
        dishes.add(dish);
        ShoppingCart cart = new ShoppingCart(3, sidfs1, dishes);
        cart.updateShoppingCartItem(1, 0);

        assertEquals(0, cart.allItems().size());
    }

    @Test
    public void testUpdateCustomDishZero() {
        List<ShoppingCartItemDataFormat> sidfs1 = new ArrayList<ShoppingCartItemDataFormat>();
        ShoppingCartItemDataFormat sidf1 = new ShoppingCartItemDataFormat(1, 3);
        sidfs1.add(sidf1);
        List<FoodDish> dishes = new ArrayList<FoodDish>();
        FoodDish dish = new FoodDish(-1, "Test", 10, 11, "Test1", "Test2", "Test3");
        dishes.add(dish);
        ShoppingCart cart = new ShoppingCart(3, sidfs1, dishes);
        cart.updateShoppingCartItem(-1, 0);

        assertEquals(0, cart.allCustomDishes().size());
    }

    @Test
    public void testUpdateCustomDish() {
        List<ShoppingCartItemDataFormat> sidfs1 = new ArrayList<ShoppingCartItemDataFormat>();
        ShoppingCartItemDataFormat sidf1 = new ShoppingCartItemDataFormat(1, 3);
        sidfs1.add(sidf1);
        List<FoodDish> dishes = new ArrayList<FoodDish>();
        FoodDish dish = new FoodDish(-1, "Test", 10, 11, "Test1", "Test2", "Test3");
        dishes.add(dish);
        ShoppingCart cart = new ShoppingCart(3, sidfs1, dishes);
        cart.updateShoppingCartItem(-1, 5);

        assertEquals(5, cart.allCustomDishes().get(0).getQuantity());
    }

    @Test
    public void testUpdateCustomDishNotFound() {
        List<ShoppingCartItemDataFormat> sidfs1 = new ArrayList<ShoppingCartItemDataFormat>();
        ShoppingCartItemDataFormat sidf1 = new ShoppingCartItemDataFormat(1, 3);
        sidfs1.add(sidf1);
        List<FoodDish> dishes = new ArrayList<FoodDish>();
        FoodDish dish = new FoodDish(-1, "Test", 10, 11, "Test1", "Test2", "Test3");
        dishes.add(dish);
        ShoppingCart cart = new ShoppingCart(3, sidfs1, dishes);
        ShoppingCart result = cart.updateShoppingCartItem(-10, 5);

        assertEquals(null, result);
    }

    @Test
    public void testAddShoppingCartItem() {
        List<ShoppingCartItemDataFormat> sidfs1 = new ArrayList<ShoppingCartItemDataFormat>();
        ShoppingCartItemDataFormat sidf1 = new ShoppingCartItemDataFormat(1, 3);
        sidfs1.add(sidf1);
        List<FoodDish> dishes = new ArrayList<FoodDish>();
        FoodDish dish = new FoodDish(-1, "Test", 10, 11, "Test1", "Test2", "Test3");
        dishes.add(dish);
        ShoppingCart cart = new ShoppingCart(3, sidfs1, dishes);
        cart.updateShoppingCartItem(1, 5);

        assertEquals(5, cart.allItems().get(0).getQuantity());
    }

    @Test
    public void testAddCustomFoodDish() {
        List<ShoppingCartItemDataFormat> sidfs1 = new ArrayList<ShoppingCartItemDataFormat>();
        ShoppingCartItemDataFormat sidf1 = new ShoppingCartItemDataFormat(1, 3);
        sidfs1.add(sidf1);
        List<FoodDish> dishes = new ArrayList<FoodDish>();
        FoodDish dish = new FoodDish(-1, "Test", 10, 11, "Test1", "Test2", "Test3");
        dishes.add(dish);
        ShoppingCart cart = new ShoppingCart(3, sidfs1, dishes);

        FoodDish dish2 = new FoodDish(-2, "Test2", 10, 11, "Test1", "Test2", "Test3");
        cart.addCustomFoodDish(dish2);

        assertEquals(2, cart.allCustomDishes().size());
    }

    @Test
    public void testAddCustomFoodDishFromEmpty() {
        List<ShoppingCartItemDataFormat> sidfs1 = new ArrayList<ShoppingCartItemDataFormat>();
        ShoppingCartItemDataFormat sidf1 = new ShoppingCartItemDataFormat(1, 3);
        sidfs1.add(sidf1);
        List<FoodDish> dishes = new ArrayList<FoodDish>();
        ShoppingCart cart = new ShoppingCart(3, sidfs1, dishes);

        FoodDish dish2 = new FoodDish(-2, "Test2", 10, 11, "Test1", "Test2", "Test3");
        cart.addCustomFoodDish(dish2);

        assertEquals(1, cart.allCustomDishes().size());
    }

    @Test
    public void testAddNewShoppingCartItem() {
        List<ShoppingCartItemDataFormat> sidfs1 = new ArrayList<ShoppingCartItemDataFormat>();
        ShoppingCartItemDataFormat sidf1 = new ShoppingCartItemDataFormat(1, 3);
        sidfs1.add(sidf1);
        List<FoodDish> dishes = new ArrayList<FoodDish>();
        FoodDish dish = new FoodDish(-1, "Test", 10, 11, "Test1", "Test2", "Test3");
        dishes.add(dish);
        ShoppingCart cart = new ShoppingCart(3, sidfs1, dishes);
        cart.updateShoppingCartItem(2, 4);

        assertEquals(2, cart.allItems().size());
    }

    @Test
    public void testToString() {
        List<ShoppingCartItemDataFormat> sidfs1 = new ArrayList<ShoppingCartItemDataFormat>();
        ShoppingCartItemDataFormat sidf1 = new ShoppingCartItemDataFormat(1, 3);
        sidfs1.add(sidf1);
        List<FoodDish> dishes = new ArrayList<FoodDish>();
        FoodDish dish = new FoodDish(-1, "Test", 10, 11, "Test1", "Test2", "Test3");
        dishes.add(dish);
        ShoppingCart cart = new ShoppingCart(3, sidfs1, dishes);
        cart.setTotalFoodDishes(3);
        String s = cart.toString();

        String str = "";
        for (ShoppingCartItemDataFormat shoppingCartItemDataFormat : sidfs1) {
            str += shoppingCartItemDataFormat.toString() + "\n";
        }
        str += "quantity: " + cart.getTotalFoodDishes();

        assertEquals(s, str);
    }

    @Test
    public void testClone() throws CloneNotSupportedException {
        List<ShoppingCartItemDataFormat> sidfs1 = new ArrayList<ShoppingCartItemDataFormat>();
        ShoppingCartItemDataFormat sidf1 = new ShoppingCartItemDataFormat(1, 3);
        sidfs1.add(sidf1);
        List<FoodDish> dishes = new ArrayList<FoodDish>();
        FoodDish dish = new FoodDish(-1, "Test", 10, 11, "Test1", "Test2", "Test3");
        dishes.add(dish);
        ShoppingCart cart = new ShoppingCart(3, sidfs1, dishes);
        cart.setTotalFoodDishes(14);
        ShoppingCart cart2 = (ShoppingCart) cart.clone();

        assertEquals(cart.allItems().size(), cart2.allItems().size());
        assertEquals(cart.allCustomDishes().size(), cart2.allCustomDishes().size());
    }

    @Test
    public void testClearCart() {
        List<ShoppingCartItemDataFormat> sidfs1 = new ArrayList<ShoppingCartItemDataFormat>();
        ShoppingCartItemDataFormat sidf1 = new ShoppingCartItemDataFormat(1, 3);
        sidfs1.add(sidf1);
        List<FoodDish> dishes = new ArrayList<FoodDish>();
        FoodDish dish = new FoodDish(-1, "Test", 10, 11, "Test1", "Test2", "Test3");
        dishes.add(dish);
        ShoppingCart cart = new ShoppingCart(3, sidfs1, dishes);
        cart.clearAllFoodDish();

        assertEquals(0, cart.allItems().size());
        assertEquals(0, cart.allCustomDishes().size());
    }
}
