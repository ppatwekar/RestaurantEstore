package com.estore.api.estoreapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Order extends BaseEntity {
    @JsonProperty("customerId")
    private int customerId;
    @JsonProperty("dishes")
    private FoodDish[] dishes;

    private static final Logger LOG = Logger.getLogger(Order.class.getName());

    /**
     * Create a Order with the given id, customerId and dishes
     * 
     * @param id         id of the order
     * @param customerId id of the customer who owns this order
     * @param dishes     dishes in this order
     */
    public Order(@JsonProperty("id") int id, @JsonProperty("customerId") int customerId,
            @JsonProperty("dishes") FoodDish[] dishes) {
        this.id = id;
        this.customerId = customerId;
        this.dishes = dishes.clone();
    }

    /**
     * 
     * @return gets the id of the order
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * 
     * @param id is the new id of the order
     */
    public void SetId(int id) {
        this.id = id;
    }

    /**
     * 
     * @return gets the customer id of the order
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * 
     * @param customerId is the new customer id of the order
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * 
     * @return gets the dishes in the order
     */
    public FoodDish[] getDishes() {
        return dishes.clone();
    }

    /**
     * 
     * @param dishes is the new dishes in the order
     */
    public void setDishes(FoodDish[] dishes) {
        this.dishes = dishes.clone();
    }

}
