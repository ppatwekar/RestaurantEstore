package com.estore.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.ShoppingCartFiles.ShoppingCartItemDataFormat;

@Tag("Model-tier")
public class ShoppingCartItemDataFormatTest {

    @Test
    public void testCtor() {

        int expected_quantity = 25, expected_id = 55;

        ShoppingCartItemDataFormat sh = new ShoppingCartItemDataFormat(55, 25);

        assertEquals(expected_quantity, sh.getQuantity());
        assertEquals(expected_id, sh.getFoodDishId());
    }

    @Test
    public void testQuantity() {

        ShoppingCartItemDataFormat sh = new ShoppingCartItemDataFormat(55, 25);
        int expected_quantity = 30;
        sh.setQuantity(expected_quantity);
        assertEquals(expected_quantity, sh.getQuantity());
    }

    @Test
    public void testClone() {
        ShoppingCartItemDataFormat sh = new ShoppingCartItemDataFormat(55, 25);

        try {
            ShoppingCartItemDataFormat sh2 = (ShoppingCartItemDataFormat) sh.clone();
            assertEquals(sh.getQuantity(), sh2.getQuantity());
            assertEquals(sh.getFoodDishId(), sh2.getFoodDishId());
        } catch (CloneNotSupportedException e) {
            System.out.println("Clone was not supported");
        }

    }

    @Test
    public void testToString() {
        ShoppingCartItemDataFormat sh = new ShoppingCartItemDataFormat(55, 25);
        String s = sh.toString();
        String expecting_s = "{\"foodDishId\": " + sh.getFoodDishId() + ",\n\"quantity\": " + sh.getQuantity();
        assertEquals(s, expecting_s);
    }
}
