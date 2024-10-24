package com.estore.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.estore.api.estoreapi.model.FoodDish;
import com.estore.api.estoreapi.persistence.DAOFiles.FoodDishFileDAO;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Persistence-tier")
public class FoodDishFileDAOTest {
    FoodDishFileDAO FoodDishFileDAO;
    FoodDish[] testFoodDishes;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * 
     * @throws IOException
     */
    @BeforeEach
    public void setupFoodDishFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testFoodDishes = new FoodDish[3];
        testFoodDishes[0] = new FoodDish(99, "Fried Rice", 1.23, 10, "Recipe", "Description", "Imageurl");
        testFoodDishes[1] = new FoodDish(100, "Oil Rice", 2.23, 11, "Recipe1", "Description1", "Imageurl1");
        testFoodDishes[2] = new FoodDish(101, "Water Rice", 3.23, 12, "Recipe2", "Description2", "Imageurl2");

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the FoodDish array above
        when(mockObjectMapper
                .readValue(new File("doesnt_matter.txt"), FoodDish[].class))
                .thenReturn(testFoodDishes);
        FoodDishFileDAO = new FoodDishFileDAO("doesnt_matter.txt", mockObjectMapper);
    }

    @Test
    public void testGetFoodDishes() {
        // Invoke
        FoodDish[] FoodDishes = FoodDishFileDAO.getFoodDishes();

        // Analyze
        assertEquals(FoodDishes.length, testFoodDishes.length);
        for (int i = 0; i < testFoodDishes.length; ++i)
            assertEquals(FoodDishes[i], testFoodDishes[i]);
    }

    @Test
    public void testGetFoodDishesMap() {
        Map<Integer, FoodDish> map = FoodDishFileDAO.getFoodDishesMap();

        assertEquals(testFoodDishes.length, map.size());
    }

    @Test
    public void testFindFoodDishes() {
        try {
            // Invoke
            FoodDish[] FoodDishes = FoodDishFileDAO.findFoodDish("il");

            // Analyze
            assertEquals(1, FoodDishes.length);
            assertEquals(FoodDishes[0], testFoodDishes[1]);
        } catch (IOException e) {

        }
    }

    @Test
    public void testDeleteFoodDish() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> FoodDishFileDAO.deleteFoodDish(99),
                "Unexpected exception thrown");

        // Analzye
        assertEquals(true, result);
        // We check the internal tree map size against the length
        // of the test FoodDishes array - 1 (because of the delete)
        // Because FoodDishes attribute of FoodDishFileDAO is package private
        // we can access it directly
        assertEquals(FoodDishFileDAO.getFoodDishes().length, testFoodDishes.length - 1);
    }

    @Test
    public void testCreateFoodDish() {
        // Setup
        FoodDish FoodDish = new FoodDish(99, "Fried Rice", 1.23, 10, "Recipe", "Description", "Imageurl");

        // Invoke
        FoodDish result = assertDoesNotThrow(() -> FoodDishFileDAO.createFoodDish(FoodDish),
                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);

        try {
            FoodDish actual = FoodDishFileDAO.getFoodDish(FoodDish.getID());
            assertEquals(actual.getID(), FoodDish.getID());
            assertEquals(actual.getName(), FoodDish.getName());
            assertEquals(actual.getCost(), FoodDish.getCost());
            assertEquals(actual.getQuantity(), FoodDish.getQuantity());
            assertEquals(actual.getRecipe(), FoodDish.getRecipe());
            assertEquals(actual.getDescription(), FoodDish.getDescription());
            assertEquals(actual.getImageurl(), FoodDish.getImageurl());
        } catch (IOException e) {

        }
    }

    @Test
    public void testUpdateFoodDish() {
        // Setup
        FoodDish FoodDish = new FoodDish(99, "Fried Rice", 1.23, 10, "Recipe", "Description", "Imageurl");

        // Invoke
        FoodDish result = assertDoesNotThrow(() -> FoodDishFileDAO.updateFoodDish(FoodDish),
                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        try {
            FoodDish actual = FoodDishFileDAO.getFoodDish(FoodDish.getID());
            assertEquals(actual, FoodDish);
        } catch (IOException e) {

        }
    }

    @Test
    public void testUpdateFoodDishFail() {
        // Setup
        FoodDish FoodDish = new FoodDish(9, "Test", 1.23, 10, "Recipe", "Description", "Imageurl");

        // Invoke
        FoodDish result = assertDoesNotThrow(() -> FoodDishFileDAO.updateFoodDish(FoodDish),
                "Unexpected exception thrown");

        // Analyze
        assertEquals(null, result);
    }

    @Test
    public void testSaveException() throws IOException {
        doThrow(new IOException())
                .when(mockObjectMapper)
                .writeValue(any(File.class), any(FoodDish[].class));

        FoodDish FoodDish = new FoodDish(99, "Fried Rice", 1.23, 10, "Recipe", "Description", "Imageurl");

        assertThrows(IOException.class,
                () -> FoodDishFileDAO.createFoodDish(FoodDish),
                "IOException not thrown");
    }

    @Test
    public void testGetFoodDishNotFound() {
        try {
            // Invoke
            FoodDish FoodDish = FoodDishFileDAO.getFoodDish(98);

            // Analyze
            assertEquals(null, FoodDish);
        } catch (IOException e) {

        }
    }

    @Test
    public void testDeleteFoodDishNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> FoodDishFileDAO.deleteFoodDish(98),
                "Unexpected exception thrown");

        // Analyze
        assertEquals(FoodDishFileDAO.getFoodDishes().length, testFoodDishes.length);
    }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        // We want to simulate with a Mock Object Mapper that an
        // exception was raised during JSON object deseerialization
        // into Java objects
        // When the Mock Object Mapper readValue method is called
        // from the FoodDishFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
                .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"), FoodDish[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                () -> new FoodDishFileDAO("doesnt_matter.txt", mockObjectMapper),
                "IOException not thrown");
    }
}
