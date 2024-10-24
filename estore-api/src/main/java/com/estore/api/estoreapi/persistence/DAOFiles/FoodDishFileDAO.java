package com.estore.api.estoreapi.persistence.DAOFiles;

import java.util.logging.Logger;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.FoodDish;
import com.estore.api.estoreapi.persistence.DAOs.FoodDishDAO;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implements the functionality for JSON file-based peristance for Food Dishes
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of
 * this
 * class and injects the instance into other classes as needed
 * 
 */
@Component
public class FoodDishFileDAO implements FoodDishDAO {
    private static final Logger LOG = Logger.getLogger(FoodDishFileDAO.class.getName());

    Map<Integer, FoodDish> foodDishes;

    private ObjectMapper objectMapper;
    private static int nextId;
    private String filename;

    /**
     * Creates a FoodDish File Data Access Object
     * 
     * @param filename     Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization
     *                     and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public FoodDishFileDAO(@Value("${foodDish.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    /**
     * Loads {@linkplain FoodDish foodDishes} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        this.foodDishes = new TreeMap<>();
        FoodDishFileDAO.nextId = 0;

        FoodDish[] foodDishArray = objectMapper.readValue(new File(filename), FoodDish[].class);

        for (FoodDish foodDish : foodDishArray) {
            this.foodDishes.put(foodDish.getID(), foodDish);
            if (foodDish.getID() > FoodDishFileDAO.nextId) {
                FoodDishFileDAO.nextId = foodDish.getID();
            }
        }

        ++FoodDishFileDAO.nextId;
        return true;
    }

    /**
     * Generates the next id for a new {@linkplain FoodDish food dishes}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = FoodDishFileDAO.nextId;
        ++FoodDishFileDAO.nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain FoodDish Food dish} from the tree map
     * 
     * @return The array of {@link FoodDish food dishes}, may be empty
     */
    private FoodDish[] getFoodDishesArray() {
        return getFoodDishesArray(null);
    }

    /**
     * Generates an array of {@linkplain FoodDish food dishes} from the tree map for
     * any
     * {@linkplain FoodDish food dish} that contains the text specified by
     * containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain FoodDish
     * Food dishes}
     * in the tree map
     * 
     * @return The array of {@link FoodDish food dishes}, may be empty
     */
    private FoodDish[] getFoodDishesArray(String containsText) {
        ArrayList<FoodDish> list = new ArrayList<>();
        for (FoodDish foodDish : this.foodDishes.values()) {
            if (containsText == null || foodDish.getName().toLowerCase().contains(containsText.toLowerCase())) {
                list.add(foodDish);
            }
        }

        FoodDish[] arr = new FoodDish[list.size()];
        list.toArray(arr);
        return arr;
    }

    /**
     * Saves the {@linkplain FoodDish food dish} from the map into the file as an
     * array of JSON objects
     * 
     * @return true if the {@link FoodDish food dishes} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    public boolean save() throws IOException {
        FoodDish[] arr = getFoodDishesArray();

        objectMapper.writeValue(new File(this.filename), arr);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FoodDish[] getFoodDishes() {
        synchronized (this.foodDishes) {
            return getFoodDishesArray(null);
        }
    }

    public Map<Integer, FoodDish> getFoodDishesMap() {
        return this.foodDishes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FoodDish[] findFoodDish(String containsText) throws IOException {
        synchronized (this.foodDishes) {
            return getFoodDishesArray(containsText);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FoodDish getFoodDish(int id) throws IOException {
        synchronized (this.foodDishes) {
            if (this.foodDishes.containsKey(id)) {
                return this.foodDishes.get(id);
            } else
                return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FoodDish createFoodDish(FoodDish foodDish) throws IOException {
        synchronized (this.foodDishes) {
            FoodDish foodDish2 = new FoodDish(nextId(), foodDish.getName(), foodDish.getCost(), foodDish.getQuantity(),
                    foodDish.getRecipe(), foodDish.getDescription(), foodDish.getImageurl());
            this.foodDishes.put(foodDish2.getID(), foodDish2);
            save();
            return foodDish2;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FoodDish updateFoodDish(FoodDish foodDish) throws IOException {
        synchronized (this.foodDishes) {
            if (this.foodDishes.containsKey(foodDish.getID())) {
                this.foodDishes.put(foodDish.getID(), foodDish);
                save();
                return foodDish;
            } else
                return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteFoodDish(int id) throws IOException {
        synchronized (this.foodDishes) {
            if (this.foodDishes.containsKey(id)) {
                this.foodDishes.remove(id);
                return save();
            } else
                return false;
        }
    }

}
