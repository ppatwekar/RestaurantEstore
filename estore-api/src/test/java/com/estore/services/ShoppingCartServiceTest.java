package com.estore.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.Customer;
import com.estore.api.estoreapi.model.FoodDish;
import com.estore.api.estoreapi.model.ShoppingCartFiles.*;
import com.estore.api.estoreapi.persistence.DAOs.FoodDishDAO;
import com.estore.api.estoreapi.persistence.DAOs.ShoppingCartDAO;
import com.estore.api.estoreapi.services.CustomerService;
import com.estore.api.estoreapi.services.ShoppingCartService;

@Tag("Service-tier")
public class ShoppingCartServiceTest {
    private ShoppingCartService shoppingCartService;
    private ShoppingCartDAO mockShoppingCartDAO;
    private FoodDishDAO mockFoodDishDAO;
    private CustomerService mockCustomerService;

    @BeforeEach
    public void setupOrderController() {
        this.mockShoppingCartDAO = mock(ShoppingCartDAO.class);
        this.mockFoodDishDAO = mock(FoodDishDAO.class);
        this.mockCustomerService = mock(CustomerService.class);
        this.shoppingCartService = new ShoppingCartService(mockShoppingCartDAO, mockFoodDishDAO, mockCustomerService);
    }

    @Test
    public void testGetShoppingCartCustomerNull() {
        when(mockCustomerService.findCustomerById(3)).thenReturn(null);

        ShoppingCartResponseObj obj = shoppingCartService.getShoppingCart(3);

        assertEquals(null, obj);
    }

    @Test
    public void testGetShoppingCartShoppingCartNull() throws IOException {
        Customer customer = new Customer(1000, "sumit", "123", "sumitkumar", "mahto", "sm5109@gmail.com", "5855009989",
                "191 loden");

        List<ShoppingCartItemDataFormat> sidfs1 = new ArrayList<ShoppingCartItemDataFormat>();
        ShoppingCartItemDataFormat sidf1 = new ShoppingCartItemDataFormat(1, 3);
        sidfs1.add(sidf1);
        ShoppingCart cart = new ShoppingCart(100, sidfs1, null);

        when(mockCustomerService.findCustomerById(3)).thenReturn(customer);
        when(mockShoppingCartDAO.getShoppingCart(3)).thenReturn(null);
        when(mockShoppingCartDAO.createShoppingCart(3)).thenReturn(cart);

        ShoppingCartResponseObj obj = shoppingCartService.getShoppingCart(3);

        assertEquals(null, obj);// failed test
    }

    @Test
    public void testGetShoppingCart() throws IOException {
        Customer customer = new Customer(1000, "sumit", "123", "sumitkumar", "mahto", "sm5109@gmail.com", "5855009989",
                "191 loden");

        List<ShoppingCartItemDataFormat> sidfs1 = new ArrayList<ShoppingCartItemDataFormat>();
        ShoppingCartItemDataFormat sidf1 = new ShoppingCartItemDataFormat(1, 3);
        sidfs1.add(sidf1);
        ShoppingCart cart = new ShoppingCart(1000, sidfs1, new ArrayList<>());

        FoodDish dish = new FoodDish(1, "Test", 10, 11, "Test1", "Test2", "Test3");

        when(mockCustomerService.findCustomerById(1000)).thenReturn(customer);
        when(mockShoppingCartDAO.getShoppingCart(1000)).thenReturn(cart);
        when(mockFoodDishDAO.getFoodDish(1)).thenReturn(dish);
        when(mockShoppingCartDAO.updateShoppingCart(cart)).thenReturn(cart);

        List<ShoppingCartItemFull> lst = new ArrayList<>();
        ShoppingCartItemFull item = new ShoppingCartItemFull(dish, 3);
        lst.add(item);
        ShoppingCartResponseObj expected = new ShoppingCartResponseObj(lst, 3);

        ShoppingCartResponseObj obj = shoppingCartService.getShoppingCart(1000);

        assertEquals(expected, obj);
    }

    @Test
    public void testGetShoppingCartInventoryLowQuantity() throws IOException {
        Customer customer = new Customer(5000, "Camp", "123", "Camp", "ing", "camp@gmail.com", "5556013031",
                "14623, Rochester");

        List<ShoppingCartItemDataFormat> lst = new ArrayList<>();
        ShoppingCartItemDataFormat item = new ShoppingCartItemDataFormat(1, 5);
        lst.add(item);
        ShoppingCart shoppingCart = new ShoppingCart(5000, lst, new ArrayList<>());

        FoodDish foodDish = new FoodDish(1, "test", 6.88, 2, "test", "test", "");

        FoodDish foodDish2 = new FoodDish(1, "test", 6.88, 2, "test", "test", "");
        ShoppingCartItemFull scif = new ShoppingCartItemFull(foodDish2, 2);
        List<ShoppingCartItemFull> list = new ArrayList<>();
        list.add(scif);
        ShoppingCartResponseObj expected = new ShoppingCartResponseObj(list, 2);

        when(this.mockCustomerService.findCustomerById(5000)).thenReturn(customer);
        when(this.mockShoppingCartDAO.getShoppingCart(5000)).thenReturn(shoppingCart);
        when(this.mockFoodDishDAO.getFoodDish(1)).thenReturn(foodDish);
        when(this.mockShoppingCartDAO.updateShoppingCart(shoppingCart)).thenReturn(shoppingCart);

        ShoppingCartResponseObj o = this.shoppingCartService.getShoppingCart(5000);

        assertEquals(expected, o);
    }

    @Test
    public void testUpdateShoppingCartCustomerNull() {
        when(mockCustomerService.findCustomerById(3)).thenReturn(null);

        ShoppingCartResponseObj obj = shoppingCartService.updateShoppingCart(null, 3);

        assertEquals(null, obj);
    }

    @Test
    public void testUpdateShoppingCart() throws IOException {
        int customerId = 400;
        ShoppingCartItemDataFormat item1 = new ShoppingCartItemDataFormat(1, 4); // increase quant
        ShoppingCartItemDataFormat item1Updated = new ShoppingCartItemDataFormat(1, 12);

        List<ShoppingCartItemDataFormat> lst = new ArrayList<>();
        lst.add(item1);

        List<ShoppingCartItemDataFormat> lstUpdated = new ArrayList();
        lstUpdated.add(item1Updated);

        ShoppingCart sh = new ShoppingCart(customerId, lst, new ArrayList<>());
        ShoppingCart shUpdated = new ShoppingCart(customerId, lstUpdated, new ArrayList<>());

        FoodDish foodDish = new FoodDish(1, "", 0, 12, "", "", "");
        FoodDish foodDishInvent = new FoodDish(1, "", 0, 12, "", "", "");
        ShoppingCartItemFull itemFull = new ShoppingCartItemFull(foodDish, 12);

        List<ShoppingCartItemFull> scifLst = new ArrayList<>();
        scifLst.add(itemFull);

        ShoppingCartResponseObj scro = new ShoppingCartResponseObj(scifLst, 12);

        when(this.mockCustomerService.findCustomerById(customerId))
                .thenReturn(new Customer(customerId, null, null, null, null, null, null, null));
        when(this.mockShoppingCartDAO.getShoppingCart(customerId)).thenReturn(shUpdated);
        when(this.mockShoppingCartDAO.updateShoppingCart(shUpdated)).thenReturn(shUpdated); // means that shopping cart
                                                                                            // was updated as expected
        when(this.mockFoodDishDAO.getFoodDish(1)).thenReturn(foodDishInvent);

        ShoppingCartResponseObj obj = this.shoppingCartService.updateShoppingCart(item1Updated, customerId);
        assertEquals(scro, obj);
    }

    @Test
    public void testAddCustomFoodDishCustomerNull() {
        when(mockCustomerService.findCustomerById(3)).thenReturn(null);

        FoodDish[] obj = shoppingCartService.addCustomFoodDish(null, 3);

        assertEquals(null, obj);
    }

    @Test
    public void testAddCustomFoodDish() throws IOException {
        FoodDish customFoodDish = new FoodDish(-1, "", 9.99, 3, "", "", "");
        int customerId = 1000;

        List<FoodDish> lst = new ArrayList<>();
        lst.add(customFoodDish);

        ShoppingCart shoppingCart = new ShoppingCart(customerId, new ArrayList<>(), new ArrayList<>());
        ShoppingCart shoppingCart2 = new ShoppingCart(customerId, new ArrayList<>(), lst);

        when(this.mockCustomerService.findCustomerById(customerId))
                .thenReturn(new Customer(customerId, "", null, "", "", null, "", ""));
        when(this.mockShoppingCartDAO.getShoppingCart(customerId)).thenReturn(shoppingCart2);
        when(this.mockShoppingCartDAO.updateShoppingCart(shoppingCart2)).thenReturn(shoppingCart2);
        when(this.mockShoppingCartDAO.updateShoppingCart(shoppingCart2)).thenReturn(shoppingCart2);

        FoodDish[] foodDishs = this.shoppingCartService.addCustomFoodDish(customFoodDish, customerId);

        assertEquals(customFoodDish, foodDishs[0]);
    }

}
