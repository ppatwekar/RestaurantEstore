package com.estore.api.estoreapi.model.ShoppingCartFiles;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.estore.api.estoreapi.model.Customer;
import com.estore.api.estoreapi.model.FoodDish;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ShoppingCart implements Cloneable {
    @JsonProperty("customerId")
    private int customerId;
    @JsonProperty("shoppingCartItems")
    private List<ShoppingCartItemDataFormat> shoppingCartItems;
    @JsonProperty("totalFoodDishes")
    private int totalFoodDishes;
    @JsonProperty("customizedDishes")
    private List<FoodDish> customizedDishes;// TO-DO: new property

    private static final Logger LOG = Logger.getLogger(ShoppingCart.class.getName());
    static final String STRING_FORMAT = "Shopping Cart [CustomerId = %d, Total Dishes = %d, Dish Ids = %s]";

    /**
     * Initializes the {@linkplain ShoppingCart} Object
     * 
     * @param customerId        id of the {@link Customer}
     * @param shoppingCartItems list of {@link ShoppingCart} items
     */
    public ShoppingCart(@JsonProperty("customerId") int customerId,
            @JsonProperty("shoppingCartItems") List<ShoppingCartItemDataFormat> shoppingCartItems,
            @JsonProperty("customizedDishes") List<FoodDish> customizedDishes) {
        this.customerId = customerId;
        this.shoppingCartItems = shoppingCartItems;
        this.totalFoodDishes = 0;
        this.customizedDishes = customizedDishes;
    }

    /**
     * @return Returns the customerId associated with this {@linkplain ShoppingCart}
     */
    public int getCustomerId() {
        return this.customerId;
    }

    /**
     * @return Returns the total {@linkplain FoodDish} objects in the
     *         {@link ShoppingCart}
     */
    public int getTotalFoodDishes() {
        return this.totalFoodDishes;
    }

    public List<ShoppingCartItemDataFormat> allItems() {
        return this.shoppingCartItems;
    }

    public List<FoodDish> allCustomDishes() {
        return this.customizedDishes;
    }

    public void setTotalFoodDishes(int totalFoodDishes) {
        this.totalFoodDishes = totalFoodDishes;
    }

    public void setShoppingCartItems(List<ShoppingCartItemDataFormat> shoppingCartItems) {
        this.shoppingCartItems = shoppingCartItems;
    }

    public void setCustomizedDishes(List<FoodDish> customizedDishes) {
        this.customizedDishes = customizedDishes;
    }

    /**
     * Adds removes or updates quantity for a
     * {@linkplain ShoppingCartItemDataFormat} item
     * from the {@linkplain ShoppingCart}
     * 
     * @param foodDishId
     * @param quantity
     * @return
     */
    public ShoppingCart updateShoppingCartItem(int foodDishId, int quantity) {
        if (foodDishId < 0) {
            return this.updateCustomDish(foodDishId, quantity);
        }
        // System.out.println("Change: "+ quantity);
        int index = this.getShoppingCartItemIndex(foodDishId);
        // shoppingCartitem exists
        if (index != -1) {
            if (quantity == 0) {
                this.removeShoppingCartItem(foodDishId);
            } else {
                // You should not create a new item here, it's meaningless
                // ShoppingCartItemDataFormat shoppingCartItem =
                // this.shoppingCartItems.get(index);
                // shoppingCartItem.setQuantity(quantity);
                this.updateShoppingCartItemQuantity(index, quantity);
            }
        } // does not exist
        else {
            if (quantity != 0) {
                this.addShoppingCartItem(foodDishId, quantity);
            }
        }
        return this;
    }

    private ShoppingCart updateCustomDish(int foodDishId, int quantity) {
        FoodDish foodDish = this.getCustomDishById(foodDishId);
        if (foodDish == null) {
            return null;
        }
        if (quantity == 0) {
            this.customizedDishes.remove(foodDish);
        } else {
            foodDish.setQuantity(quantity);
        }
        return this;
    }

    public void addCustomFoodDish(FoodDish customFoodDish) {
        int newId = this.generateNextCustomDishId();
        customFoodDish.setId(newId);
        int index = (-1 * newId) - 1;
        this.customizedDishes.add(index, customFoodDish);
        this.totalFoodDishes += customFoodDish.getQuantity();
    }

    private int generateNextCustomDishId() {
        if (this.customizedDishes.isEmpty()) {
            return -1;
        }

        int prevId = -1;
        for (int i = 1; i < this.customizedDishes.size(); i++) {
            FoodDish foodDish = this.customizedDishes.get(i);
            if (foodDish.getID() != prevId - 1) {
                return prevId - 1;
            }
            prevId = foodDish.getID();
        }

        return (-1) * (this.customizedDishes.size() + 1);
    }

    private FoodDish getCustomDishById(int foodDishId) {
        for (FoodDish foodDish : this.customizedDishes) {
            if (foodDish.getID() == foodDishId) {
                return foodDish;
            }
        }
        return null;
    }

    /**
     * Adds a new {@linkplain ShoppingCartItemDataFormat} item to the ShoppingCart
     * 
     * @param foodDishId
     * @param quantity
     */
    private void addShoppingCartItem(int foodDishId, int quantity) {
        ShoppingCartItemDataFormat shoppingCartItemDataFormat = new ShoppingCartItemDataFormat(foodDishId, quantity);
        this.shoppingCartItems.add(shoppingCartItemDataFormat);
        this.totalFoodDishes += quantity;
    }

    /**
     * Removes a {@linkplain ShoppingCartItemDataFormat} item from the ShoppingCart
     * 
     * @param foodDishId
     */
    private void removeShoppingCartItem(int foodDishId) {
        int index = this.getShoppingCartItemIndex(foodDishId);
        if (index != -1) {
            this.updateShoppingCartItemQuantity(index, 0);
            this.shoppingCartItems.remove(index);
        }
    }

    private void updateShoppingCartItemQuantity(int foodDishIndex, int quantity) {
        ShoppingCartItemDataFormat item = this.shoppingCartItems.get(foodDishIndex);
        int currentQuantity = item.getQuantity();
        this.totalFoodDishes += quantity - currentQuantity;
        item.setQuantity(quantity);
    }

    /**
     * Returns the index of the {@linkplain ShoppingCartItemDataFormat} item in the
     * {@link ShoppingCart} list
     * 
     * @param foodDishId
     * @return
     */
    private int getShoppingCartItemIndex(int foodDishId) {
        for (int i = 0; i < this.shoppingCartItems.size(); i++) {
            if (this.shoppingCartItems.get(i).getFoodDishId() == foodDishId) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 
     * @param foodDishId id of the {@linkplain FoodDish}
     * @return true if the {@link FoodDish} exists in the {@link ShoppingCart} else
     *         false
     */
    public ShoppingCartItemDataFormat containsFoodDish(int foodDishId) {
        for (ShoppingCartItemDataFormat shoppingCartItem : shoppingCartItems) {
            if (shoppingCartItem.getFoodDishId() == foodDishId) {
                return shoppingCartItem;
            }
        }
        return null;
    }

    public ShoppingCart clearAllFoodDish() {
        shoppingCartItems.clear();
        customizedDishes.clear();
        totalFoodDishes = 0;
        return this;
    }

    @Override
    public String toString() {
        String str = "";
        for (ShoppingCartItemDataFormat shoppingCartItemDataFormat : this.shoppingCartItems) {
            str += shoppingCartItemDataFormat.toString() + "\n";
        }
        str += "quantity: " + this.getTotalFoodDishes();
        return str;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        List<ShoppingCartItemDataFormat> lst = new ArrayList<>();
        int totalFoodDishes = 0;
        for (ShoppingCartItemDataFormat shoppingCartItemDataFormat : this.shoppingCartItems) {
            lst.add((ShoppingCartItemDataFormat) shoppingCartItemDataFormat.clone());
            totalFoodDishes += shoppingCartItemDataFormat.getQuantity();
        }

        List<FoodDish> custLst = new ArrayList<>();
        for (FoodDish foodDish : this.customizedDishes) {
            custLst.add(foodDish.clone());
            totalFoodDishes += foodDish.getQuantity();
        }

        ShoppingCart newShoppingCart = new ShoppingCart(customerId, lst, custLst);
        newShoppingCart.setTotalFoodDishes(totalFoodDishes);

        return newShoppingCart;
    }

}
