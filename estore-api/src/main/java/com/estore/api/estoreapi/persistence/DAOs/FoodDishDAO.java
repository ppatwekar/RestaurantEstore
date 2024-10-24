package com.estore.api.estoreapi.persistence.DAOs;

import java.io.IOException;

import com.estore.api.estoreapi.model.FoodDish;

public interface FoodDishDAO {
    /**
     * Gets all the {@linkplain FoodDish dishes} from the inventory
     * 
     * @return An array of {@link FoodDish} objects
     */
    FoodDish[] getFoodDishes() throws IOException;

    /**
     * Gets all the {@linkplain FoodDish dishes} from the inventory, whoose names
     * contain the given text
     * 
     * @param containsText the text to match the food dish name to
     * @return An array of {@link FoodDish} objects
     */
    FoodDish[] findFoodDish(String containsText) throws IOException;

    /**
     * Gets food dish according to id
     * 
     * @param id of {@link FoodDish}
     * @return {@link FoodDish} with the given id, else null
     */
    FoodDish getFoodDish(int id) throws IOException;

    /**
     * Creates and saves a Food Dish
     * The id of foodDish object is ignored and a new id is created`
     * 
     * @param foodDish the {@link FoodDish} object to be created
     * @return new {@link FoodDish} object if successfully created, else null
     */
    FoodDish createFoodDish(FoodDish foodDish) throws IOException;

    /**
     * @param foodDish the updated {@link FoodDish} object
     *                 Updates and saves a FoodDish object
     * @return updated {@link FoodDish} if successfully updated else, null
     * @throws IOException if data cannot be accessed
     */
    FoodDish updateFoodDish(FoodDish foodDish) throws IOException;

    /**
     * Deletes a {@link FoodDish} with given id
     * 
     * @return true if {@link FoodDish} with given id was delete else, false
     * @throws IOException if data cannot be accessed
     */
    boolean deleteFoodDish(int id) throws IOException;
}
// end of class