package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.logging.Logger;

public class FoodDish implements Cloneable {
    @JsonProperty("name")
    private String name;
    @JsonProperty("cost")
    private double cost;
    @JsonProperty("quantity")
    private int quantity;
    @JsonProperty("recipe")
    private String recipe;
    @JsonProperty("description")
    private String description;
    @JsonProperty("imageurl")
    private String imageurl;
    @JsonProperty("id")
    private int id;

    private static final Logger LOG = Logger.getLogger(FoodDish.class.getName());

    static final String STRING_FORMAT = "FoodDish [id= %d, name=%s, cost=%s, quantity=%s, recipe=%s, description=%s, imageurl=%s";

    /**
     * Create a Food Dish with the given name, cost and quantity in inventory
     * 
     * @param name        name of the dish
     * @param cost        cost of the dish
     * @param quantity    quantity of the dish in the inventory
     * @param recipe      recipe of the dish
     * @param description description of the dish
     * @param imageurl    imageurl of the dish
     */
    public FoodDish(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("cost") double cost,
            @JsonProperty("quantity") int quantity, @JsonProperty("recipe") String recipe,
            @JsonProperty("description") String description, @JsonProperty("imageurl") String imageurl) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.cost = cost;
        this.recipe = recipe;
        this.description = description;
        this.imageurl = imageurl;
    }

    /**
     * 
     * @return gets the name of the food dish
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the cost of the food dish
     * 
     * @return
     */
    public double getCost() {
        return this.cost;
    }

    /**
     * 
     * @return Gets the quantity of the food dish in the inventory
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * 
     * @return gets the recipe of the food dish
     */
    public String getRecipe() {
        return this.recipe;
    }

    /**
     * 
     * @return gets the description of the food dish
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * 
     * @return gets the imageurl of the food dish
     */
    public String getImageurl() {
        return this.imageurl;
    }

    /**
     * Sets the name of the dish
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @param cost is the new cost of the dish
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * 
     * @param quantity is the new quantity of the dish
     */
    public void setQuantity(int quantity) {
        this.quantity = Math.max(0, quantity);
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * 
     * @param quantity is the incremental quantity of the dish
     */
    public void increaseQuantityBy(int quantity) {
        this.quantity += quantity;
    }

    /**
     * 
     * @param quantity is the decremental quantity of the dish
     */
    public void reduceQuantityBy(int quantity) {
        this.quantity = Math.max(this.quantity - quantity, 0);
    }

    /**
     * Sets the recipe of the dish
     */
    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    /**
     * Sets the description of the dish
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the imageurl of the dish
     */
    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    /**
     * Gets the id of the food dish
     * 
     * @return
     */
    public int getID() {
        return this.id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(FoodDish.STRING_FORMAT, this.id, this.name, this.cost, this.quantity, this.recipe,
                this.description, this.imageurl);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FoodDish clone() throws CloneNotSupportedException {
        return new FoodDish(id, name, cost, quantity, recipe, description, imageurl);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FoodDish)) {
            return false;
        }

        FoodDish o = (FoodDish) obj;

        return this.cost == o.cost && this.name == o.name && this.imageurl == o.imageurl
                && this.description == o.description && this.id == o.id && this.quantity == o.quantity
                && this.recipe.equals(o.recipe);
    }

    @Override
    public int hashCode() {
        return this.id;
    }

}
