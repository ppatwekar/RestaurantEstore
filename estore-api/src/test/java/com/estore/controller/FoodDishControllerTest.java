package com.estore.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.estore.api.estoreapi.controller.FoodDishController;
import com.estore.api.estoreapi.model.FoodDish;
import com.estore.api.estoreapi.persistence.DAOs.FoodDishDAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Tag("Controller-tier")
public class FoodDishControllerTest {
    private FoodDishController dishController;
    private FoodDishDAO mockDishDAO;
    
    @BeforeEach
    public void setupDishController() {
        mockDishDAO = mock(FoodDishDAO.class);
        dishController = new FoodDishController(mockDishDAO);
    }

    @Test
    public void testGetFoodDish() throws IOException {
        // Setup
        FoodDish dish = new FoodDish(99, "Fried Rice", 1.23, 10, "Recipe", "Description", "Imageurl");
        
        when(mockDishDAO.getFoodDish(dish.getID())).thenReturn(dish);

        // Invoke
        ResponseEntity<FoodDish> response = dishController.getFoodDish(dish.getID());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(dish,response.getBody());
    }

    @Test
    public void testGetFoodDishNotFound() throws Exception { 
        // Setup
        int dishId = 99;
        
        // no dish found
        when(mockDishDAO.getFoodDish(dishId)).thenReturn(null);

        // Invoke
        ResponseEntity<FoodDish> response = dishController.getFoodDish(dishId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetFoodDishHandleException() throws Exception { 
        // Setup
        int dishId = 99;
        
        doThrow(new IOException()).when(mockDishDAO).getFoodDish(dishId);

        // Invoke
        ResponseEntity<FoodDish> response = dishController.getFoodDish(dishId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testCreateFoodDish() throws IOException {  // createFoodDish may throw IOException
        // Setup
        FoodDish dish = new FoodDish(99, "Fried Rice", 1.23, 10, "Recipe", "Description", "Imageurl");
        
        // creation and save
        when(mockDishDAO.createFoodDish(dish)).thenReturn(dish);

        // Invoke
        ResponseEntity<FoodDish> response = dishController.createFoodDish(dish);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(dish,response.getBody());
    }

    @Test
    public void testCreateFoodDishFailed() throws IOException {  // createFoodDish may throw IOException
        // Setup
        FoodDish dish = new FoodDish(99, "Fried Rice", 1.23, 10, "Recipe", "Description", "Imageurl");
        // when createFoodDish is called, return false simulating failed
        // creation and save
        when(mockDishDAO.createFoodDish(dish)).thenReturn(null);

        // Invoke
        ResponseEntity<FoodDish> response = dishController.createFoodDish(dish);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateFoodDishHandleException() throws IOException {  // createFoodDish may throw IOException
        // Setup
        FoodDish dish = new FoodDish(99, "Fried Rice", 1.23, 10, "Recipe", "Description", "Imageurl");

        // When createFoodDish is called on the Mock FoodDish DAO, throw an IOException
        doThrow(new IOException()).when(mockDishDAO).createFoodDish(dish);

        // Invoke
        ResponseEntity<FoodDish> response = dishController.createFoodDish(dish);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testUpdateFoodDish() throws IOException { // updateFoodDish may throw IOException
        // Setup
        FoodDish dish = new FoodDish(99, "Fried Rice", 1.23, 10, "Recipe", "Description", "Imageurl");
        // when updateFoodDish is called, return true simulating successful
        // update and save
        when(mockDishDAO.updateFoodDish(dish)).thenReturn(dish);
        ResponseEntity<FoodDish> response = dishController.updateFoodDish(dish);
        dish.setName("Super Rice");

        // Invoke
        response = dishController.updateFoodDish(dish);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(dish,response.getBody());
    }

    @Test
    public void testUpdateFoodDishFailed() throws IOException { // updateFoodDish may throw IOException
        // Setup
        FoodDish dish = new FoodDish(99, "Fried Rice", 1.23, 10, "Recipe", "Description", "Imageurl");
        // when updateFoodDish is called, return true simulating successful
        // update and save
        when(mockDishDAO.updateFoodDish(dish)).thenReturn(null);

        // Invoke
        ResponseEntity<FoodDish> response = dishController.updateFoodDish(dish);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateFoodDishHandleException() throws IOException { // updateFoodDish may throw IOException
        // Setup
        FoodDish dish = new FoodDish(99, "Fried Rice", 1.23, 10, "Recipe", "Description", "Imageurl");
        // When updateFoodDish is called on the Mock FoodDish DAO, throw an IOException
        doThrow(new IOException()).when(mockDishDAO).updateFoodDish(dish);

        // Invoke
        ResponseEntity<FoodDish> response = dishController.updateFoodDish(dish);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetFoodDishes() throws IOException { // getFoodDishes may throw IOException
        // Setup
        FoodDish[] dishes = new FoodDish[2];
        dishes[0] = new FoodDish(99, "Fried Rice", 1.23, 10, "Recipe", "Description", "Imageurl");
        dishes[1] = new FoodDish(100, "Chicken Rice", 4.23, 30, "Recipe1", "Description1", "Imageurl1");
        // When getFoodDishes is called return the dishes created above
        when(mockDishDAO.getFoodDishes()).thenReturn(dishes);

        // Invoke
        ResponseEntity<FoodDish[]> response = dishController.getFoodDishes();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(dishes,response.getBody());
    }

    @Test
    public void testGetFoodDishesHandleException() throws IOException { // getFoodDishes may throw IOException
        // Setup
        // When getFoodDishes is called on the Mock FoodDish DAO, throw an IOException
        doThrow(new IOException()).when(mockDishDAO).getFoodDishes();

        // Invoke
        ResponseEntity<FoodDish[]> response = dishController.getFoodDishes();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testSearchFoodDishes() throws IOException { // findFoodDishes may throw IOException
        // Setup
        String searchString = "hi";
        FoodDish[] dishes = new FoodDish[2];
        dishes[0] = new FoodDish(99, "Fried Rice", 1.23, 10, "Recipe", "Description", "Imageurl");
        dishes[1] = new FoodDish(100, "Chicken Rice", 4.23, 30, "Recipe1", "Description1", "Imageurl1");
        // When findFoodDishes is called with the search string, return the two
        /// dishes above
        when(mockDishDAO.findFoodDish(searchString)).thenReturn(dishes);

        // Invoke
        ResponseEntity<FoodDish[]> response = dishController.searchFoodDishes(searchString);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(dishes,response.getBody());
    }

    @Test
    public void testSearchFoodDishesHandleException() throws IOException { // findFoodDishes may throw IOException
        // Setup
        String searchString = "hi";
        // When createFoodDish is called on the Mock FoodDish DAO, throw an IOException
        doThrow(new IOException()).when(mockDishDAO).findFoodDish(searchString);

        // Invoke
        ResponseEntity<FoodDish[]> response = dishController.searchFoodDishes(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteFoodDish() throws IOException { // deleteFoodDish may throw IOException
        // Setup
        int dishId = 99;
        // when deleteFoodDish is called return true, simulating successful deletion
        when(mockDishDAO.deleteFoodDish(dishId)).thenReturn(true);

        // Invoke
        ResponseEntity<FoodDish> response = dishController.deleteFoodDish(dishId);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteFoodDishNotFound() throws IOException { // deleteFoodDish may throw IOException
        // Setup
        int dishId = 99;
        // when deleteFoodDish is called return false, simulating failed deletion
        when(mockDishDAO.deleteFoodDish(dishId)).thenReturn(false);

        // Invoke
        ResponseEntity<FoodDish> response = dishController.deleteFoodDish(dishId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteFoodDishHandleException() throws IOException { // deleteFoodDish may throw IOException
        // Setup
        int dishId = 99;
        // When deleteFoodDish is called on the Mock FoodDish DAO, throw an IOException
        doThrow(new IOException()).when(mockDishDAO).deleteFoodDish(dishId);

        // Invoke
        ResponseEntity<FoodDish> response = dishController.deleteFoodDish(dishId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}
