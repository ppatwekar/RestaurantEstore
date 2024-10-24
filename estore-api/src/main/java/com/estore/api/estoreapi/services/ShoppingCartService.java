package com.estore.api.estoreapi.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.FoodDish;
import com.estore.api.estoreapi.model.ShoppingCartFiles.ShoppingCart;
import com.estore.api.estoreapi.model.ShoppingCartFiles.ShoppingCartResponseObj;
import com.estore.api.estoreapi.persistence.DAOs.FoodDishDAO;
import com.estore.api.estoreapi.persistence.DAOs.ShoppingCartDAO;
import com.estore.api.estoreapi.model.ShoppingCartFiles.ShoppingCartItemDataFormat;
import com.estore.api.estoreapi.model.ShoppingCartFiles.ShoppingCartItemFull;

/**
 * This is a service class that perform all operations related to the shopping
 * cart and
 * serves to the controller class for the shoppingCart
 */
@Component
public class ShoppingCartService {
    private ShoppingCartDAO shoppingCartDAO;
    private FoodDishDAO foodDishDAO;
    private CustomerService customerService;

    public ShoppingCartService(ShoppingCartDAO shoppingCartDAO, FoodDishDAO foodDishDAO,
            CustomerService customerService) {
        this.foodDishDAO = foodDishDAO;
        this.shoppingCartDAO = shoppingCartDAO;
        this.customerService = customerService;
    }

    /**
     * 
     * @param customerId id of the customer
     * @return the response body that contains the shoppingcart of the customer
     */
    public ShoppingCartResponseObj getShoppingCart(int customerId) {
        // check if customerId exists in data persistance
        if (this.customerService.findCustomerById(customerId) == null) {
            return null;
        }
        ShoppingCartResponseObj requestObj = null;
        try {
            // gets shoppingCart Stored in current data persistance
            ShoppingCart shoppingCart = this.shoppingCartDAO.getShoppingCart(customerId);
            ShoppingCart shoppingCartUpdated;
            // if customer shopping cart exists in persistance
            if (shoppingCart != null) {
                // checks for any reductions in quantity of current shoppingCart items that were
                // in persistance with inventory
                shoppingCartUpdated = this.checkUpdatesWithInventory(shoppingCart);
            } else { // if shopping cart does not exist for customer, create one
                shoppingCartUpdated = this.shoppingCartDAO.createShoppingCart(customerId);
            }

            List<ShoppingCartItemFull> items = this.getItemsListInResponseFormat(shoppingCartUpdated);

            requestObj = new ShoppingCartResponseObj(items, shoppingCartUpdated.getTotalFoodDishes());
        } catch (Exception e) {
            System.out.println("Exception thrown in getShoppingCart");
        }
        return requestObj;
    }

    /**
     * Updates the shopping cart in for change in a single product
     * 
     * @param shoppingCartItemDataFormat Request body of the change
     * @param customerId                 id of the customer
     * @return updated shoppingcart in response format
     */
    public ShoppingCartResponseObj updateShoppingCart(ShoppingCartItemDataFormat shoppingCartItemDataFormat,
            int customerId) {
        // check if customer exists
        if (this.customerService.findCustomerById(customerId) == null) {
            return null;
        }

        ShoppingCartResponseObj shoppingCartResponseObj = null;
        try {
            // gets the shoppingCart object from persistance
            ShoppingCart shoppingCart = this.shoppingCartDAO.getShoppingCart(customerId);

            // get foodDishId of item for which the request is made i.e
            // shoppingCartItemDataFormat
            int foodDishId = shoppingCartItemDataFormat.getFoodDishId();
            // get quantity of item for which the request is made i.e
            // shoppingCartItemDataFormat
            int quantity = shoppingCartItemDataFormat.getQuantity();
            // updates the foodDish in shopping cart with given quantity.
            shoppingCart = shoppingCart.updateShoppingCartItem(foodDishId, quantity);

            ShoppingCart newShoppingCart = (ShoppingCart) shoppingCart.clone();
            this.shoppingCartDAO.updateShoppingCart(newShoppingCart);

            // gets updated shopping cart from persistance (also checks for changes in
            // inventory)
            shoppingCartResponseObj = this.getShoppingCart(customerId);
        } catch (Exception e) {
            System.out.println("Exception thrown in updateShoppingCart");
        }
        return shoppingCartResponseObj;
    }

    /**
     * This method compares the current FoodDishes' quantity in the shoppingcart to
     * the quantities
     * in the inventory. Following changes occur to FoodDishes in shopping cart
     * 
     * 1) shoppingCartQuantity > inventoryQuantity => make shoppingCartQuantity =
     * inventoryQuantity
     * 2) shoppingCartQuantity <= inventoryQuantity => do nothing
     * 3) inventoryQuantity == 0 => remove item from shoppingCart
     * 
     * @param shoppingCart shoppingCart to check updates for
     * @return updatedShoppingCart
     */
    private ShoppingCart checkUpdatesWithInventory(ShoppingCart shoppingCart) {
        boolean shoppingCartChanged = false;

        ShoppingCart newShoppingCart = null;
        // new quantity
        int newTotalQuantity = 0;
        try {
            newShoppingCart = (ShoppingCart) shoppingCart.clone();
            // get all SHopping cart Items
            List<ShoppingCartItemDataFormat> items = shoppingCart.allItems();
            List<FoodDish> custItems = shoppingCart.allCustomDishes();

            // note: iteration occuring over original shoppingCart items
            for (ShoppingCartItemDataFormat shoppingCartItemDataFormat : items) {
                // get foodDish from persistance using foodDishId in shoppingCart
                FoodDish inventFoodDish = this.foodDishDAO.getFoodDish(shoppingCartItemDataFormat.getFoodDishId());
                // if shoppingCart quantity of foodDish is greater than available in inventory
                if (shoppingCartItemDataFormat.getQuantity() >= inventFoodDish.getQuantity()) {
                    // get the minimun quantity between shoppingCart and inventory quantity
                    int updateQuantity = Math.min(shoppingCartItemDataFormat.getQuantity(),
                            inventFoodDish.getQuantity());
                    // make necessary updates (update quantity, remove foodDish) to cloned
                    // shoppingCart
                    newShoppingCart.updateShoppingCartItem(shoppingCartItemDataFormat.getFoodDishId(), updateQuantity);
                    // newQuantity is only incremented because initially it was 0 and only updated
                    // quantity is added.
                    newTotalQuantity += updateQuantity;
                    // changes have been made to shoppingCart
                    shoppingCartChanged = true;
                } else {
                    newTotalQuantity += shoppingCartItemDataFormat.getQuantity();
                }
            }

            for (FoodDish foodDish : custItems) {
                newTotalQuantity += foodDish.getQuantity();
            }
            // set total food dishes
            newShoppingCart.setTotalFoodDishes(newTotalQuantity);

            // if changes were there in shoppingCart
            if (shoppingCartChanged) {
                // update the shopping cart in persistance
                this.shoppingCartDAO.updateShoppingCart(newShoppingCart);
            }

        } catch (Exception e) {
            System.out.println("Exception thrown in checkUpdatesWithInventory");
            System.out.println(e.getMessage());
        }

        return newShoppingCart;
    }

    public FoodDish[] addCustomFoodDish(FoodDish foodDish, int customerId) {
        if (this.customerService.findCustomerById(customerId) == null) {
            return null;
        }

        ShoppingCart shoppingCartUpdated = null;

        try {
            ShoppingCart shoppingCart = this.shoppingCartDAO.getShoppingCart(customerId);
            shoppingCartUpdated = (ShoppingCart) shoppingCart.clone();

            shoppingCartUpdated.addCustomFoodDish(foodDish);
            shoppingCartUpdated = this.shoppingCartDAO.updateShoppingCart(shoppingCartUpdated);

        } catch (Exception e) {
            System.out.println("Exception thrown in addCustomFoodDish");
            return null;
        }

        ShoppingCartResponseObj scro = this.getShoppingCart(customerId);
        return this.shoppingCartResponseToFoodDishArray(scro);

    }

    /**
     * Gets shoppingcart items
     * 
     * @param shoppingCart
     * @return
     */
    private List<ShoppingCartItemFull> getItemsListInResponseFormat(ShoppingCart shoppingCart) {
        List<ShoppingCartItemFull> lst = new ArrayList<>();
        List<ShoppingCartItemDataFormat> allItems = shoppingCart.allItems();
        List<FoodDish> custItems = shoppingCart.allCustomDishes();

        for (ShoppingCartItemDataFormat shoppingCartItemDataFormat : allItems) {
            ShoppingCartItemFull item = this.convertToFullShoppingCartItem(shoppingCartItemDataFormat);
            lst.add(item);
        }

        for (FoodDish foodDish : custItems) {
            ShoppingCartItemFull tempItem = new ShoppingCartItemFull(foodDish, foodDish.getQuantity());
            lst.add(tempItem);
        }

        return lst;
    }

    /**
     * Converts shoppingcart stored in persistance to a fully detailed ShoppingCart
     * item. Needed for frontend.
     * 
     * @param shoppingCartItemDataFormat
     * @return
     */
    private ShoppingCartItemFull convertToFullShoppingCartItem(ShoppingCartItemDataFormat shoppingCartItemDataFormat) {
        ShoppingCartItemFull item = null;
        try {
            FoodDish foodDish = this.foodDishDAO.getFoodDish(shoppingCartItemDataFormat.getFoodDishId());
            item = new ShoppingCartItemFull(foodDish, shoppingCartItemDataFormat.getQuantity());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return item;
    }

    private FoodDish[] shoppingCartResponseToFoodDishArray(ShoppingCartResponseObj sh) {
        List<ShoppingCartItemFull> items = sh.getShoppingCartItems();
        FoodDish[] ret = new FoodDish[items.size()];
        int index = 0;
        for (ShoppingCartItemFull scit : items) {
            ret[index++] = scit.getFoodDish();
        }
        return ret;
    }

}
