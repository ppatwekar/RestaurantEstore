package com.estore.api.estoreapi.model.ShoppingCartFiles;

import com.estore.api.estoreapi.model.FoodDish;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents the structure for the Item in the ShoppingCart and
 * it contains the entire detail of the item itself. This class is used to form
 * a part of
 * the response object for the Responses to the frontend
 */
public class ShoppingCartItemFull implements Cloneable {
    @JsonProperty("foodDish")
    private FoodDish foodDish;
    @JsonProperty("quantity")
    private int quantity;

    public ShoppingCartItemFull(@JsonProperty("foodDish") FoodDish foodDishId, @JsonProperty("quantity") int quantity) {
        this.foodDish = foodDishId;
        this.quantity = quantity;
    }

    public FoodDish getFoodDish() {
        return foodDish;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int incrementQuantity() {
        return ++this.quantity;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new ShoppingCartItemFull((FoodDish) this.foodDish.clone(), this.quantity);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ShoppingCartItemFull)) {
            return false;
        }

        ShoppingCartItemFull o = (ShoppingCartItemFull) obj;

        return this.foodDish.equals(o.foodDish) && this.quantity == o.quantity;
    }

    @Override
    public int hashCode() {
        return this.foodDish.getID();
    }
}
