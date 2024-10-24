package com.estore.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.estore.api.estoreapi.model.FoodDish;
import com.estore.api.estoreapi.model.ShoppingCartFiles.*;
import com.estore.api.estoreapi.persistence.DAOFiles.ShoppingCartFileDAO;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Persistence-tier")
public class ShoppingCartFileDAOTest {
    ShoppingCartFileDAO shoppingCartFileDAO;
    ShoppingCart[] testShoppingCarts;
    ObjectMapper mockObjectMapper;

    @BeforeEach
    public void setupShoppingCartFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testShoppingCarts = new ShoppingCart[2];

        List<ShoppingCartItemDataFormat> sidfs1 = new ArrayList<ShoppingCartItemDataFormat>();
        ShoppingCartItemDataFormat sidf1 = new ShoppingCartItemDataFormat(1, 3);
        sidfs1.add(sidf1);
        List<FoodDish> dishes = new ArrayList<FoodDish>();
        FoodDish dish = new FoodDish(-1, "Test", 10, 11, "Test1", "Test2", "Test3");
        dishes.add(dish);
        testShoppingCarts[0] = new ShoppingCart(3, sidfs1, dishes);

        List<ShoppingCartItemDataFormat> sidfs2 = new ArrayList<ShoppingCartItemDataFormat>();
        ShoppingCartItemDataFormat sidf2 = new ShoppingCartItemDataFormat(2, 4);
        sidfs2.add(sidf2);
        List<FoodDish> dishes2 = new ArrayList<FoodDish>();
        FoodDish dish2 = new FoodDish(-2, "Test2", 10, 11, "Test1", "Test2", "Test3");
        dishes2.add(dish2);
        testShoppingCarts[1] = new ShoppingCart(4, sidfs2, dishes2);

        when(mockObjectMapper
                .readValue(new File("doesnt_matter.txt"), ShoppingCart[].class))
                .thenReturn(testShoppingCarts);
        shoppingCartFileDAO = new ShoppingCartFileDAO("doesnt_matter.txt", mockObjectMapper);
    }

    @Test
    public void testGetShoppingCart() throws IOException {
        ShoppingCart cart = shoppingCartFileDAO.getShoppingCart(3);

        assertEquals(cart, testShoppingCarts[0]);
    }

    @Test
    public void testGetShoppingCartWithFoodID() throws IOException {
        ShoppingCart[] carts = shoppingCartFileDAO.getShoppingCartsWithFoodDishId(1);

        assertEquals(carts[0], testShoppingCarts[0]);
    }

    @Test
    public void testGetShoppingCarts() throws IOException {
        ShoppingCart[] carts = shoppingCartFileDAO.getShoppingCarts();

        assertEquals(carts[0], testShoppingCarts[0]);
        assertEquals(carts[1], testShoppingCarts[1]);

    }

    @Test
    public void testCreateShoppingCart() throws IOException {
        ShoppingCart cart = shoppingCartFileDAO.createShoppingCart(5);

        assertEquals(5, cart.getCustomerId());
    }

    @Test
    public void testCreateShoppingCartFail() throws IOException {
        ShoppingCart cart = shoppingCartFileDAO.createShoppingCart(3);

        assertEquals(null, cart);
    }

    @Test
    public void testUpdateShoppingCart() throws IOException {
        List<ShoppingCartItemDataFormat> sidfs1 = new ArrayList<ShoppingCartItemDataFormat>();
        ShoppingCartItemDataFormat sidf1 = new ShoppingCartItemDataFormat(1, 3);
        sidfs1.add(sidf1);
        ShoppingCart cart = new ShoppingCart(3, sidfs1, null);
        ShoppingCart newCart = shoppingCartFileDAO.updateShoppingCart(cart);

        assertEquals(newCart, shoppingCartFileDAO.getShoppingCart(3));
    }

    @Test
    public void testDeleteShoppingCart() throws IOException {
        shoppingCartFileDAO.deleteShoppingCart(3);

        assertEquals(testShoppingCarts.length - 1, shoppingCartFileDAO.getShoppingCarts().length);
    }

    @Test
    public void testGetShoppingCartNotFound() throws IOException {
        ShoppingCart cart = shoppingCartFileDAO.getShoppingCart(100);

        assertEquals(null, cart);
    }

    @Test
    public void testUpdateShoppingCartNotFound() throws IOException {
        List<ShoppingCartItemDataFormat> sidfs1 = new ArrayList<ShoppingCartItemDataFormat>();
        ShoppingCartItemDataFormat sidf1 = new ShoppingCartItemDataFormat(1, 3);
        sidfs1.add(sidf1);
        ShoppingCart cart = new ShoppingCart(100, sidfs1, null);
        ShoppingCart newCart = shoppingCartFileDAO.updateShoppingCart(cart);

        assertEquals(null, newCart);
    }

    @Test
    public void testDeleteShoppingCartNotFound() throws IOException {
        shoppingCartFileDAO.deleteShoppingCart(100);

        assertEquals(testShoppingCarts.length, shoppingCartFileDAO.getShoppingCarts().length);
    }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        doThrow(new IOException())
                .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"), ShoppingCart[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                () -> new ShoppingCartFileDAO("doesnt_matter.txt", mockObjectMapper),
                "IOException not thrown");
    }

    @Test
    public void testClearShoppingCart() throws IOException {
        ShoppingCart cart = shoppingCartFileDAO.clearShoppingCart(3);

        assertEquals(0, cart.allItems().size());
        assertEquals(0, cart.allCustomDishes().size());
    }

    @Test
    public void testClearShoppingCartNotFound() throws IOException {
        ShoppingCart cart = shoppingCartFileDAO.clearShoppingCart(1001);

        assertEquals(null, cart);
    }
}
