package com.estore.api.estoreapi.model.ShoppingCartFiles;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents the Response object body for the API responses
 */
public class ShoppingCartResponseObj implements Cloneable {
    @JsonProperty("foodDishes")
    private List<ShoppingCartItemFull> shoppingCartItems;
    @JsonProperty("totalQuantity")
    private int totalQuantity;

    public ShoppingCartResponseObj(@JsonProperty("foodDishes") List<ShoppingCartItemFull> shoppingCartItems,
            @JsonProperty("totalQuantity") int totalQuantity) {
        this.shoppingCartItems = shoppingCartItems;
        this.totalQuantity = totalQuantity;
    }

    public List<ShoppingCartItemFull> getShoppingCartItems() {
        return shoppingCartItems;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        List<ShoppingCartItemFull> lst = new ArrayList<>(this.shoppingCartItems.size());

        for (ShoppingCartItemFull shoppingCartItemFull : this.shoppingCartItems) {
            lst.add((ShoppingCartItemFull) shoppingCartItemFull.clone());
        }

        return new ShoppingCartResponseObj(lst, this.totalQuantity);
    }

    private boolean contains(ShoppingCartItemFull shoppingCartItemFull) {
        for (ShoppingCartItemFull sh : this.shoppingCartItems) {
            if (sh.equals(shoppingCartItemFull)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ShoppingCartResponseObj)) {
            return false;
        }

        ShoppingCartResponseObj o = (ShoppingCartResponseObj) obj;

        if (o.shoppingCartItems.size() == this.shoppingCartItems.size()) {
            for (ShoppingCartItemFull shoppingCartItemFull : this.shoppingCartItems) {
                if (!o.contains(shoppingCartItemFull)) {
                    return false;
                }
            }
        }
        return o.totalQuantity == this.totalQuantity;
    }

    @Override
    public int hashCode() {
        return this.shoppingCartItems.hashCode();
    }
}
