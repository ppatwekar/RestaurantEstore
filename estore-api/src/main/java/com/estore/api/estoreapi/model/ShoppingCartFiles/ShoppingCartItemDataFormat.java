package com.estore.api.estoreapi.model.ShoppingCartFiles;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class is used by data persistance to store data belonging to the shoppingcart 
 * into the data store
 */
public class ShoppingCartItemDataFormat implements Cloneable{
    @JsonProperty("foodDishId") private int foodDishId;
    @JsonProperty("quantity") private int quantity;

    /**
     * 
     * @param foodDishId id of the {@link FoodDish}
     * @param quantity quantity of the {@link FoodDish}
     */
    public ShoppingCartItemDataFormat(@JsonProperty("foodDishId") int foodDishId, @JsonProperty("quantity")  int quantity){
        this.foodDishId = foodDishId;
        this.quantity = quantity;
    }

    /**
     * Sets the quantity of the foodDish
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * 
     * @return quantity of the {@link FoodDish}
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * 
     * @return id of the {@linkplain FoodDish}
     */
    public int getFoodDishId() {
        return foodDishId;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new ShoppingCartItemDataFormat(this.foodDishId, this.quantity);
    }

    @Override
    public String toString() {
        return "{\"foodDishId\": "+this.foodDishId+",\n\"quantity\": "+this.quantity;
    }

}
