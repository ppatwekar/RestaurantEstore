package com.estore.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.FoodDish;

@Tag("Model-tier")
public class FoodDishTest {
    @Test
    public void testConstructor() {
        // Setup
        int expected_id = 99;
        String expected_name = "Fried Rice";
        double expected_cost = 1.23;
        int expected_quantity = 10;
        String expected_recipe = "Recipe: Rice, Oil";
        String expected_description = "Tranditional Fried Rice";
        String expected_imageurl = "./assets/dishImages/test-image.jpg";

        // Invoke
        FoodDish dish = new FoodDish(expected_id, expected_name, expected_cost, expected_quantity, expected_recipe,
                expected_description, expected_imageurl);

        // Analyze
        assertEquals(expected_id, dish.getID());
        assertEquals(expected_name, dish.getName());
        assertEquals(expected_cost, dish.getCost());
        assertEquals(expected_quantity, dish.getQuantity());
        assertEquals(expected_recipe, dish.getRecipe());
        assertEquals(expected_description, dish.getDescription());
        assertEquals(expected_imageurl, dish.getImageurl());
    }

    @Test
    public void testID() {
        // Setup
        int id = 99;
        String name = "Fried Rice";
        double cost = 1.23;
        int quantity = 10;
        String recipe = "Recipe: Rice, Oil";
        String description = "Tranditional Fried Rice";
        String imageurl = "./assets/dishImages/test-image.jpg";
        FoodDish dish = new FoodDish(id, name, cost, quantity, recipe, description, imageurl);

        int expected_id = 100;

        // Invoke
        dish.setId(expected_id);

        // Analyze
        assertEquals(expected_id, dish.getID());
    }

    @Test
    public void testName() {
        // Setup
        int id = 99;
        String name = "Fried Rice";
        double cost = 1.23;
        int quantity = 10;
        String recipe = "Recipe: Rice, Oil";
        String description = "Tranditional Fried Rice";
        String imageurl = "./assets/dishImages/test-image.jpg";
        FoodDish dish = new FoodDish(id, name, cost, quantity, recipe, description, imageurl);

        String expected_name = "Yangzhou Fried Rice";

        // Invoke
        dish.setName(expected_name);

        // Analyze
        assertEquals(expected_name, dish.getName());
    }

    @Test
    public void testCost() {
        // Setup
        int id = 99;
        String name = "Fried Rice";
        double cost = 1.23;
        int quantity = 10;
        String recipe = "Recipe: Rice, Oil";
        String description = "Tranditional Fried Rice";
        String imageurl = "./assets/dishImages/test-image.jpg";
        FoodDish dish = new FoodDish(id, name, cost, quantity, recipe, description, imageurl);

        double expected_cost = 33.3;

        // Invoke
        dish.setCost(expected_cost);

        // Analyze
        assertEquals(expected_cost, dish.getCost());
    }

    @Test
    public void testQuantity() {
        // Setup
        int id = 99;
        String name = "Fried Rice";
        double cost = 1.23;
        int quantity = 10;
        String recipe = "Recipe: Rice, Oil";
        String description = "Tranditional Fried Rice";
        String imageurl = "./assets/dishImages/test-image.jpg";
        FoodDish dish = new FoodDish(id, name, cost, quantity, recipe, description, imageurl);

        int expected_quantity = 14;

        // Invoke
        dish.setQuantity(expected_quantity);

        // Analyze
        assertEquals(expected_quantity, dish.getQuantity());
    }

    @Test
    public void testIncreaseQuantity() {
        // Setup
        int id = 99;
        String name = "Fried Rice";
        double cost = 1.23;
        int quantity = 10;
        String recipe = "Recipe: Rice, Oil";
        String description = "Tranditional Fried Rice";
        String imageurl = "./assets/dishImages/test-image.jpg";
        FoodDish dish = new FoodDish(id, name, cost, quantity, recipe, description, imageurl);

        int expected_quantity = 14;

        // Invoke
        dish.increaseQuantityBy(expected_quantity);

        // Analyze
        assertEquals(expected_quantity + quantity, dish.getQuantity());
    }

    @Test
    public void testReduceQuantity() {
        // Setup
        int id = 99;
        String name = "Fried Rice";
        double cost = 1.23;
        int quantity = 10;
        String recipe = "Recipe: Rice, Oil";
        String description = "Tranditional Fried Rice";
        String imageurl = "./assets/dishImages/test-image.jpg";
        FoodDish dish = new FoodDish(id, name, cost, quantity, recipe, description, imageurl);

        int expected_quantity = 4;

        // Invoke
        dish.reduceQuantityBy(expected_quantity);

        // Analyze
        assertEquals(quantity - expected_quantity, dish.getQuantity());
    }

    @Test
    public void testRecipe() {
        // Setup
        int id = 99;
        String name = "Fried Rice";
        double cost = 1.23;
        int quantity = 10;
        String recipe = "Recipe: Rice, Oil";
        String description = "Tranditional Fried Rice";
        String imageurl = "./assets/dishImages/test-image.jpg";
        FoodDish dish = new FoodDish(id, name, cost, quantity, recipe, description, imageurl);

        String expected_Recipe = "New Recipe";

        // Invoke
        dish.setRecipe(expected_Recipe);

        // Analyze
        assertEquals(expected_Recipe, dish.getRecipe());
    }

    @Test
    public void testDescription() {
        // Setup
        int id = 99;
        String name = "Fried Rice";
        double cost = 1.23;
        int quantity = 10;
        String recipe = "Recipe: Rice, Oil";
        String description = "Tranditional Fried Rice";
        String imageurl = "./assets/dishImages/test-image.jpg";
        FoodDish dish = new FoodDish(id, name, cost, quantity, recipe, description, imageurl);

        String expected_description = "New Description";

        // Invoke
        dish.setDescription(expected_description);

        // Analyze
        assertEquals(expected_description, dish.getDescription());
    }

    @Test
    public void testImageurl() {
        // Setup
        int id = 99;
        String name = "Fried Rice";
        double cost = 1.23;
        int quantity = 10;
        String recipe = "Recipe: Rice, Oil";
        String description = "Tranditional Fried Rice";
        String imageurl = "./assets/dishImages/test-image.jpg";
        FoodDish dish = new FoodDish(id, name, cost, quantity, recipe, description, imageurl);

        String expected_imageurl = "New url";

        // Invoke
        dish.setImageurl(expected_imageurl);

        // Analyze
        assertEquals(expected_imageurl, dish.getImageurl());
    }

    @Test
    public void testClone() throws CloneNotSupportedException {
        // Setup
        int id = 99;
        String name = "Fried Rice";
        double cost = 1.23;
        int quantity = 10;
        String recipe = "Recipe: Rice, Oil";
        String description = "Tranditional Fried Rice";
        String imageurl = "./assets/dishImages/test-image.jpg";
        FoodDish dish = new FoodDish(id, name, cost, quantity, recipe, description, imageurl);

        FoodDish dish2 = dish.clone();

        // Analyze
        assertEquals(dish2.getID(), dish.getID());
        assertEquals(dish2.getCost(), dish.getCost());
        assertEquals(dish2.getDescription(), dish.getDescription());
        assertEquals(dish2.getImageurl(), dish.getImageurl());
        assertEquals(dish2.getName(), dish.getName());
        assertEquals(dish2.getQuantity(), dish.getQuantity());
        assertEquals(dish2.getRecipe(), dish.getRecipe());

    }

    @Test
    public void testEqualsFalse() {
        int id = 99;
        String name = "Fried Rice";
        double cost = 1.23;
        int quantity = 10;
        String recipe = "Recipe: Rice, Oil";
        String description = "Tranditional Fried Rice";
        String imageurl = "./assets/dishImages/test-image.jpg";
        FoodDish dish = new FoodDish(id, name, cost, quantity, recipe, description, imageurl);
        boolean result = dish.equals(null);

        assertEquals(false, result);
    }

    @Test
    public void testEqualsFalseCost() {
        int id = 99;
        String name = "Fried Rice";
        double cost = 1.23;
        int quantity = 10;
        String recipe = "Recipe: Rice, Oil";
        String description = "Tranditional Fried Rice";
        String imageurl = "./assets/dishImages/test-image.jpg";
        FoodDish dish = new FoodDish(id, name, cost, quantity, recipe, description, imageurl);
        FoodDish dish2 = new FoodDish(id, name, 0, quantity, recipe, description, imageurl);
        boolean result = dish.equals(dish2);

        assertEquals(false, result);
    }

    @Test
    public void testEqualsFalseName() {
        int id = 99;
        String name = "Fried Rice";
        double cost = 1.23;
        int quantity = 10;
        String recipe = "Recipe: Rice, Oil";
        String description = "Tranditional Fried Rice";
        String imageurl = "./assets/dishImages/test-image.jpg";
        FoodDish dish = new FoodDish(id, name, cost, quantity, recipe, description, imageurl);
        FoodDish dish2 = new FoodDish(id, "Test", cost, quantity, recipe, description, imageurl);
        boolean result = dish.equals(dish2);

        assertEquals(false, result);
    }

    @Test
    public void testEqualsFalseImageUrl() {
        int id = 99;
        String name = "Fried Rice";
        double cost = 1.23;
        int quantity = 10;
        String recipe = "Recipe: Rice, Oil";
        String description = "Tranditional Fried Rice";
        String imageurl = "./assets/dishImages/test-image.jpg";
        FoodDish dish = new FoodDish(id, name, cost, quantity, recipe, description, imageurl);
        FoodDish dish2 = new FoodDish(id, name, cost, quantity, recipe, description, "");
        boolean result = dish.equals(dish2);

        assertEquals(false, result);
    }

    @Test
    public void testEqualsFalseDescription() {
        int id = 99;
        String name = "Fried Rice";
        double cost = 1.23;
        int quantity = 10;
        String recipe = "Recipe: Rice, Oil";
        String description = "Tranditional Fried Rice";
        String imageurl = "./assets/dishImages/test-image.jpg";
        FoodDish dish = new FoodDish(id, name, cost, quantity, recipe, description, imageurl);
        FoodDish dish2 = new FoodDish(id, name, cost, quantity, recipe, "", imageurl);
        boolean result = dish.equals(dish2);

        assertEquals(false, result);
    }

    @Test
    public void testEqualsFalseID() {
        int id = 99;
        String name = "Fried Rice";
        double cost = 1.23;
        int quantity = 10;
        String recipe = "Recipe: Rice, Oil";
        String description = "Tranditional Fried Rice";
        String imageurl = "./assets/dishImages/test-image.jpg";
        FoodDish dish = new FoodDish(id, name, cost, quantity, recipe, description, imageurl);
        FoodDish dish2 = new FoodDish(956, name, cost, quantity, recipe, description, imageurl);
        boolean result = dish.equals(dish2);

        assertEquals(false, result);
    }

    @Test
    public void testEqualsFalseQuantity() {
        int id = 99;
        String name = "Fried Rice";
        double cost = 1.23;
        int quantity = 10;
        String recipe = "Recipe: Rice, Oil";
        String description = "Tranditional Fried Rice";
        String imageurl = "./assets/dishImages/test-image.jpg";
        FoodDish dish = new FoodDish(id, name, cost, quantity, recipe, description, imageurl);
        FoodDish dish2 = new FoodDish(id, name, cost, 2, recipe, description, imageurl);
        boolean result = dish.equals(dish2);

        assertEquals(false, result);
    }

    @Test
    public void testEqualsFalseRecipe() {
        int id = 99;
        String name = "Fried Rice";
        double cost = 1.23;
        int quantity = 10;
        String recipe = "Recipe: Rice, Oil";
        String description = "Tranditional Fried Rice";
        String imageurl = "./assets/dishImages/test-image.jpg";
        FoodDish dish = new FoodDish(id, name, cost, quantity, recipe, description, imageurl);
        FoodDish dish2 = new FoodDish(id, name, cost, quantity, "", description, imageurl);
        boolean result = dish.equals(dish2);

        assertEquals(false, result);
    }

    @Test
    public void testHashCode() {
        // Setup
        int id = 99;
        String name = "Fried Rice";
        double cost = 1.23;
        int quantity = 10;
        String recipe = "Recipe: Rice, Oil";
        String description = "Tranditional Fried Rice";
        String imageurl = "./assets/dishImages/test-image.jpg";
        FoodDish dish = new FoodDish(id, name, cost, quantity, recipe, description, imageurl);

        assertEquals(dish.getID(), dish.hashCode());
    }
}
