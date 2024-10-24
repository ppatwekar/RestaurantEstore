package com.estore.api.estoreapi.persistence.DAOFiles;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.ShoppingCartFiles.ShoppingCart;
import com.estore.api.estoreapi.persistence.DAOs.ShoppingCartDAO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ShoppingCartFileDAO implements ShoppingCartDAO {

    private static final Logger LOG = Logger.getLogger(ShoppingCartDAO.class.getName());

    Map<Integer, ShoppingCart> shoppingCarts;
    private ObjectMapper objectMapper;
    private String filename;

    public ShoppingCartFileDAO(@Value("${shoppingCart.file}") String filename, ObjectMapper objectMapper)
            throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        this.load();
    }

    /**
     * Loads the shopping cart from persisted data into the cache for dynamic use
     * 
     * @return
     * @throws IOException
     */
    private boolean load() throws IOException {
        this.shoppingCarts = new TreeMap<>();
        ShoppingCart[] shoppingCarts = this.objectMapper.readValue(new File(this.filename), ShoppingCart[].class);

        for (ShoppingCart shoppingCart : shoppingCarts) {
            this.shoppingCarts.put(shoppingCart.getCustomerId(), shoppingCart);
        }
        return true;
    }

    /**
     * 
     * @return all the {@linkplain ShoppingCart} contained in the persisted data
     */
    private ShoppingCart[] getShoppingCartsArray() {
        ArrayList<ShoppingCart> list = new ArrayList<>();
        for (Integer customerId : this.shoppingCarts.keySet()) {
            list.add(this.shoppingCarts.get(customerId));
        }
        ShoppingCart[] carts = new ShoppingCart[this.shoppingCarts.size()];
        list.toArray(carts);
        return carts;
    }

    /**
     * Overwrites the ShoppingCart.json file with data in the local cache
     * 
     * @return true if data is saved successfully
     * @throws IOException
     */
    public boolean save() throws IOException {
        ShoppingCart[] arr = getShoppingCartsArray();
        objectMapper.writeValue(new File(this.filename), arr);
        return true;
    }

    /**
     * @return All shopping carts
     */
    @Override
    public ShoppingCart[] getShoppingCarts() throws IOException {
        synchronized (this.shoppingCarts) {
            return this.getShoppingCartsArray();
        }
    }

    /**
     * @param foodDishId is the id of the {@linkplain FoodDish} object
     * @return all {@link ShoppingCart} objects which contain the foodDishId
     */
    @Override
    public ShoppingCart[] getShoppingCartsWithFoodDishId(int foodDishId) throws IOException {
        ArrayList<ShoppingCart> list = new ArrayList<>();
        synchronized (this.shoppingCarts) {

            for (ShoppingCart shoppingCart : this.shoppingCarts.values()) {
                if (shoppingCart.containsFoodDish(foodDishId) != null) {
                    list.add(shoppingCart);
                }
            }
            ShoppingCart[] ret = new ShoppingCart[list.size()];
            ret = list.toArray(ret);

            return ret;
        }

    }

    /**
     * @param customerId is the id of the {@linkplain Customer}
     * @return the {@link ShoppingCart} for the given customerId
     */
    @Override
    public ShoppingCart getShoppingCart(int customerId) throws IOException {
        synchronized (this.shoppingCarts) {
            return this.shoppingCarts.get(customerId);
        }
    }

    /**
     * Creates a new {@linkplain ShoppingCart} for the {@link Customer}
     * 
     * @param customerId is the id of the {@link Customer}
     * @return new {@link ShoppingCart} for the {@link Customer}
     */
    @Override
    public ShoppingCart createShoppingCart(int customerId) throws IOException {
        synchronized (this.shoppingCarts) {
            if (this.shoppingCarts.containsKey(customerId)) {
                return null;
            }
            ShoppingCart shoppingCart = new ShoppingCart(customerId, new ArrayList<>(), new ArrayList<>());
            this.shoppingCarts.put(customerId, shoppingCart);
            this.save();
            return shoppingCart;
        }
    }

    /**
     * Updates the existing {@linkplain ShoppingCart}
     */
    @Override
    public ShoppingCart updateShoppingCart(ShoppingCart shoppingCart) throws IOException {
        synchronized (this.shoppingCarts) {
            if (this.shoppingCarts.containsKey(shoppingCart.getCustomerId())) {
                this.shoppingCarts.put(shoppingCart.getCustomerId(), shoppingCart);
                this.save();
                return shoppingCart;
            } else
                return null;
        }
    }

    /**
     * @param customerId id of the customer
     *                   Deletes the {@linkplain ShoppingCart} associated with the
     *                   customerId
     */
    @Override
    public ShoppingCart deleteShoppingCart(int customerId) throws IOException {
        synchronized (this.shoppingCarts) {
            if (this.shoppingCarts.containsKey(customerId)) {
                ShoppingCart shoppingCart = this.shoppingCarts.remove(customerId);
                this.save();
                return shoppingCart;
            } else
                return null;
        }
    }

    @Override
    public ShoppingCart clearShoppingCart(int customerId) throws IOException {
        synchronized (this.shoppingCarts) {
            if (this.shoppingCarts.containsKey(customerId)) {
                ShoppingCart shoppingCart = this.shoppingCarts.get(customerId).clearAllFoodDish();
                this.save();
                return shoppingCart;
            } else
                return null;
        }
    }
}
