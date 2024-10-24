package com.estore.api.estoreapi.services;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.FoodDish;
import com.estore.api.estoreapi.model.Order;
import com.estore.api.estoreapi.persistence.DAOFiles.FoodDishFileDAO;
import com.estore.api.estoreapi.persistence.DAOFiles.OrderFileDAO;
import com.estore.api.estoreapi.persistence.DAOFiles.ShoppingCartFileDAO;

@Component
public class OrderService {
    private OrderFileDAO orderFileDAO;
    private ShoppingCartFileDAO shoppingCartFileDAO;
    private FoodDishFileDAO foodDishFileDAO;

    public OrderService(OrderFileDAO orderFileDAO, ShoppingCartFileDAO shoppingCartFileDAO,
            FoodDishFileDAO foodDishFileDAO) {
        this.orderFileDAO = orderFileDAO;
        this.shoppingCartFileDAO = shoppingCartFileDAO;
        this.foodDishFileDAO = foodDishFileDAO;
    }

    public Order findById(int id) {
        return this.orderFileDAO.findById(id);
    }

    public Order[] findByCustomerId(int customerId) {
        return this.orderFileDAO.findByCustomerId(customerId);
    }

    public Order createOrder(int customerId, FoodDish[] dishes) throws IOException {
        try {
            // check food item quantity before creating a new order
            for (int i = 0; i < dishes.length; i++) {
                if (dishes[i].getID() < 0) {
                    continue;
                }
                if (this.foodDishFileDAO.getFoodDish(dishes[i].getID()).getQuantity() < dishes[i].getQuantity()) {
                    return null;
                }
            }

            for (int i = 0; i < dishes.length; i++) {
                if (dishes[i].getID() < 0) {
                    continue;
                }
                int newQuantity = this.foodDishFileDAO.getFoodDish(dishes[i].getID()).getQuantity()
                        - dishes[i].getQuantity();
                FoodDish newDish = this.foodDishFileDAO.getFoodDish(dishes[i].getID());
                newDish.setQuantity(newQuantity);
                this.foodDishFileDAO.updateFoodDish(newDish);
            }

            // clearing the shopping cart after order is completed
            this.shoppingCartFileDAO.clearShoppingCart(customerId);

            return this.orderFileDAO.createOrder(customerId, dishes);
        } catch (IOException e) {
            return null;
        }
    }

    public boolean deleteById(int id) throws IOException {
        try {
            return this.orderFileDAO.deleteById(id);
        } catch (IOException e) {
            return false;
        }
    }
}
