package com.estore.api.estoreapi.persistence.DAOs;

import java.io.IOException;

import com.estore.api.estoreapi.model.FoodDish;
import com.estore.api.estoreapi.model.Order;

public interface OrderDAO {

    /**
     * Gets order according to id
     * 
     * @param id of {@link Order}
     * @return {@link Order} with the given id, else null
     */
    public Order findById(int id);

    /**
     * Gets all the {@linkplain Order order} belongs to a customer
     * 
     * @param customerId the id of the customer
     * @return An array of {@link Order} objects
     */
    public Order[] findByCustomerId(int customerId);

    /**
     * Creates and saves a Order
     * The id of order object is ignored and a new id is created`
     * 
     * @param customerId the id of the customer who owns the created order
     * @param dishs      the {@link FoodDish} objects in the created order
     * @return new {@link Order} object if successfully created, else null
     */
    public Order createOrder(int customerId, FoodDish[] dishs) throws IOException;

    /**
     * Deletes a {@link Order} with given id
     * 
     * @return true if {@link Order} with given id was delete else, false
     * @throws IOException if data cannot be accessed
     */
    public boolean deleteById(int id) throws IOException;

}
