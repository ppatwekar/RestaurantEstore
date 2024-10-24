package com.estore.api.estoreapi.persistence.DAOs;

import java.io.IOException;

import com.estore.api.estoreapi.model.ShoppingCartFiles.ShoppingCart;

public interface ShoppingCartDAO {
    /**
     * Can be used by admin to access all shopping carts
     * Gets all shopping carts persisted for all existing customers
     * 
     * @return array of {@link ShoppingCart} objects
     * @throws IOException
     */
    ShoppingCart[] getShoppingCarts() throws IOException;

    /**
     * Important for deletion. Say a particular FoodDish is deleted from inventory
     * by admin
     * or is made unavailable by admin, it must be removed from customer's Shopping
     * Carts, for
     * that purpose we need a list of shopping carts that contain the
     * FoodDish ids
     * 
     * @param foodDishId id of the {@linkplain FoodDish}
     * @return array of {@link ShoppingCart} containing the product id
     */
    ShoppingCart[] getShoppingCartsWithFoodDishId(int foodDishId) throws IOException;

    /**
     * Creates a new {@linkplain ShoppingCart} for a new {@link Customer}
     * 
     * @param customerId id of the customer
     * @return new {@link ShoppingCart} object
     * @throws IOException
     */
    ShoppingCart createShoppingCart(int customerId) throws IOException;

    /**
     * Returns the {@linkplain ShoppingCart} object for a given customer Id
     * 
     * @param customerId id of the customer
     * @return {@link ShoppingCart} for the customer
     */
    ShoppingCart getShoppingCart(int customerId) throws IOException;

    /**
     * Updates the {@linkplain ShoppingCart} of the user
     * 
     * @param shoppingCart updated {@link ShoppingCart}
     * @return updated {@link ShoppingCart}
     * @throws IOException
     */
    ShoppingCart updateShoppingCart(ShoppingCart shoppingCart) throws IOException;

    /**
     * Deletes the {@linkplain ShoppingCart} for the given customer
     * 
     * @param customerId id of the {@link Customer}
     * @return deleted {@link ShoppingCart}
     * @throws IOException
     */
    ShoppingCart deleteShoppingCart(int customerId) throws IOException;

    ShoppingCart clearShoppingCart(int customerId) throws IOException;

}
