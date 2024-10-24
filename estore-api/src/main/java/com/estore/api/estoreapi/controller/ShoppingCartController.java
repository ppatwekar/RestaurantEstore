package com.estore.api.estoreapi.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estore.api.estoreapi.model.ShoppingCartFiles.ShoppingCartItemDataFormat;
import com.estore.api.estoreapi.model.ShoppingCartFiles.ShoppingCartItemFull;
import com.estore.api.estoreapi.model.ShoppingCartFiles.ShoppingCartResponseObj;
import com.estore.api.estoreapi.services.ShoppingCartService;
import com.estore.api.estoreapi.model.FoodDish;

@RestController
@RequestMapping("shoppingcart")
public class ShoppingCartController {
    private static final Logger LOG = Logger.getLogger(ShoppingCartController.class.getName());

    private ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<ShoppingCartResponseObj> getShoppingCart(@PathVariable int customerId) {
        LOG.info("GET /shoppingcart/customers/" + customerId);

        ShoppingCartResponseObj shoppingCart = this.shoppingCartService.getShoppingCart(customerId);
        if (shoppingCart == null) {
            return new ResponseEntity<ShoppingCartResponseObj>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<ShoppingCartResponseObj>(shoppingCart, HttpStatus.OK);
        }
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<ShoppingCartResponseObj> updateShoppingCart(@RequestBody ShoppingCartItemDataFormat body,
            @PathVariable int customerId) {
        LOG.info("PUT /shoppingcart/customers/" + customerId);

        ShoppingCartResponseObj responseObj = this.shoppingCartService.updateShoppingCart(body, customerId);
        if (responseObj == null) {
            return new ResponseEntity<ShoppingCartResponseObj>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<ShoppingCartResponseObj>(responseObj, HttpStatus.OK);
        }
    }

    @GetMapping("/{customerId}/foodDishes")
    public ResponseEntity<List<ShoppingCartItemFull>> getShoppingCartFoodDishes(@PathVariable int customerId) {
        LOG.info("GET /shoppingcart/customers/" + customerId + "/foodDishes");

        ShoppingCartResponseObj shoppingCart = this.shoppingCartService.getShoppingCart(customerId);
        if (shoppingCart == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<List<ShoppingCartItemFull>>(shoppingCart.getShoppingCartItems(), HttpStatus.OK);
        }
    }

    @PutMapping("/{customerId}/foodDishes")
    public ResponseEntity<List<ShoppingCartItemFull>> updateShoppingCartFoodDishes(
            @RequestBody ShoppingCartItemDataFormat body, @PathVariable int customerId) {
        LOG.info("PUT /shoppingcart/customers/" + customerId + "/foodDishes");

        ShoppingCartResponseObj responseObj = this.shoppingCartService.updateShoppingCart(body, customerId);
        if (responseObj == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<List<ShoppingCartItemFull>>(responseObj.getShoppingCartItems(), HttpStatus.OK);
        }
    }

    @GetMapping("/{customerId}/foodDishesArray")
    public ResponseEntity<FoodDish[]> getShoppingCartFoodDishesArray(@PathVariable int customerId) {
        LOG.info("GET /shoppingcart/customers/" + customerId + "/foodDishesArray");

        ShoppingCartResponseObj shoppingCart = this.shoppingCartService.getShoppingCart(customerId);
        if (shoppingCart == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            List<ShoppingCartItemFull> cartItems = shoppingCart.getShoppingCartItems();
            FoodDish[] result = new FoodDish[cartItems.size()];

            for (int i = 0; i < result.length; i++) {
                try {
                    result[i] = cartItems.get(i).getFoodDish().clone();
                } catch (CloneNotSupportedException cnse) {
                    LOG.info(cnse.getMessage());
                }
                result[i].setQuantity(cartItems.get(i).getQuantity());

            }

            return new ResponseEntity<FoodDish[]>(result, HttpStatus.OK);
        }
    }

    @PutMapping("/{customerId}/foodDishesArray")
    public ResponseEntity<FoodDish[]> updateShoppingCartFoodDishesArray(@RequestBody ShoppingCartItemDataFormat body,
            @PathVariable int customerId) {
        LOG.info("PUT /shoppingcart/customers/" + customerId + "/foodDishesArray:" + body.getQuantity());

        ShoppingCartResponseObj responseObj = this.shoppingCartService.updateShoppingCart(body, customerId);
        if (responseObj == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            List<ShoppingCartItemFull> cartItems = responseObj.getShoppingCartItems();
            FoodDish[] result = new FoodDish[cartItems.size()];

            for (int i = 0; i < result.length; i++) {
                try {
                    result[i] = cartItems.get(i).getFoodDish().clone();
                } catch (CloneNotSupportedException cnse) {
                    LOG.info(cnse.getMessage());
                }
                result[i].setQuantity(cartItems.get(i).getQuantity());
                // LOG.info("Number:"+cartItems.get(i).getQuantity());
            }

            return new ResponseEntity<FoodDish[]>(result, HttpStatus.OK);
        }
    }

    @PutMapping("/{customerId}/foodDishesAdd")
    public ResponseEntity<FoodDish[]> addShoppingCartFoodDishes(@RequestBody ShoppingCartItemDataFormat body,
            @PathVariable int customerId) {
        LOG.info("PUT /shoppingcart/customers/" + customerId + "/foodDishesAdd" + body.getQuantity());
        ShoppingCartResponseObj shoppingCart = this.shoppingCartService.getShoppingCart(customerId);
        ShoppingCartItemDataFormat tempBody = body;
        List<ShoppingCartItemFull> currCartItems = shoppingCart.getShoppingCartItems();
        int currentQuantitiy = tempBody.getQuantity();
        for (int i = 0; i < currCartItems.size(); i++) {
            if (currCartItems.get(i).getFoodDish().getID() == tempBody.getFoodDishId()) {
                currentQuantitiy += currCartItems.get(i).getQuantity();
                break;
            }
        }
        tempBody.setQuantity(currentQuantitiy);
        ShoppingCartResponseObj responseObj = this.shoppingCartService.updateShoppingCart(tempBody, customerId);

        if (responseObj == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            List<ShoppingCartItemFull> cartItems = responseObj.getShoppingCartItems();
            FoodDish[] result = new FoodDish[cartItems.size()];

            for (int i = 0; i < result.length; i++) {
                try {
                    result[i] = cartItems.get(i).getFoodDish().clone();
                } catch (CloneNotSupportedException cnse) {
                    LOG.info(cnse.getMessage());
                }
                result[i].setQuantity(cartItems.get(i).getQuantity());
                // LOG.info("Number:"+cartItems.get(i).getQuantity());
            }

            return new ResponseEntity<FoodDish[]>(result, HttpStatus.OK);
        }
    }

    @PutMapping("/{customerId}/customizedDishesAdd")
    public ResponseEntity<FoodDish[]> addShoppingCartCustomizedDish(@RequestBody FoodDish body,
            @PathVariable int customerId) {
        LOG.info("PUT /shoppingcart/customers/" + customerId + "/customizedDishesAdd" + body.getQuantity());
        // TO-DO

        // Assign negative ID to customized dishes so that we don't need to change the
        // data structure of Food Dish
        FoodDish[] foodDishs = this.shoppingCartService.addCustomFoodDish(body, customerId);
        if (foodDishs == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<FoodDish[]>(foodDishs, HttpStatus.OK);

    }
}
