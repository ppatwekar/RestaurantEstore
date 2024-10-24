package com.estore.api.estoreapi.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estore.api.estoreapi.model.FoodDish;
import com.estore.api.estoreapi.persistence.DAOs.FoodDishDAO;

/**
 * Handles the REST API requests for the FoodDish resource
 * <p>
 * {@literal RestController} Spring annotation identifies this class as a REST
 * API
 * method handler to the Spring framework
 * 
 * @author Prathamesh Patwekar
 */
@RestController
@RequestMapping("dish")
public class FoodDishController {
    private static final Logger LOG = Logger.getLogger(FoodDishController.class.getName());
    private FoodDishDAO foodDishDao;

    /**
     * Creates the REST API controller to allow request-response cycle.
     * 
     * @param foodDishDAO is the {@link FoodDishDAO} data access object for CRUD
     *                    operations on the data
     */
    public FoodDishController(FoodDishDAO foodDishDAO) {
        this.foodDishDao = foodDishDAO;
    }

    /**
     * Responds to the GET request for the {@linkplain FoodDish} for the given id
     * 
     * @param id is the id used to locate the {@link FoodDish}
     * @return Response entity with {@link FoodDish} object and HTTP status of OK
     *         or HTTP status of NOT_FOUND if the resource is not found. If an
     *         exception occured,
     *         a status of INTERNAL_SERVER_ERROR is returned
     */
    @GetMapping("/{id}")
    public ResponseEntity<FoodDish> getFoodDish(@PathVariable int id) {
        LOG.info("GET /dish/" + id);
        try {
            FoodDish foodDish = foodDishDao.getFoodDish(id);
            if (foodDish != null)
                return new ResponseEntity<FoodDish>(foodDish, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Responds to the GET request for all {@linkplain FoodDish fooddishes}
     * 
     * @return Response entity with array of {@link FoodDish} objects (may be empty)
     *         and
     *         HTTP status of OK<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     */
    @GetMapping("")
    public ResponseEntity<FoodDish[]> getFoodDishes() {
        LOG.info("GET /dish");
        try {
            FoodDish[] foodDishes = foodDishDao.getFoodDishes();
            return new ResponseEntity<FoodDish[]>(foodDishes, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain FoodDish fooddishes} whose
     * name
     * contains
     * the text in name
     * 
     * @param name The name parameter which contains the text used to find the
     *             {@link FoodDish fooddishes}
     * 
     * @return ResponseEntity with array of {@link FoodDish fooddish} objects (may
     *         be empty)
     *         and
     *         HTTP status of OK<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     *         <p>
     *         Example: Find all fooddishes that contain the text "ma"
     *         GET http://localhost:8080/dishes/?name=ma
     */
    @GetMapping("/")
    public ResponseEntity<FoodDish[]> searchFoodDishes(@RequestParam String name) {
        LOG.info("GET /dish/?name =" + name);
        try {
            FoodDish[] foodDishes = foodDishDao.findFoodDish(name);

            return new ResponseEntity<FoodDish[]>(foodDishes, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain FoodDish fooddish} with the provided fooddish object
     * 
     * @param fooddish - The {@link FoodDish fooddish} to create
     * 
     * @return ResponseEntity with created {@link FoodDish fooddish} object and HTTP
     *         status
     *         of CREATED<br>
     *         ResponseEntity with HTTP status of CONFLICT if {@link FoodDish
     *         fooddish}
     *         object already exists<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<FoodDish> createFoodDish(@RequestBody FoodDish foodDish) {
        LOG.info("POST /dish " + foodDish);
        try {
            FoodDish h = foodDishDao.createFoodDish(foodDish);
            if (h != null) {
                return new ResponseEntity<FoodDish>(h, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain FoodDish fooddish} with the provided
     * {@linkplain FoodDish fooddish}
     * object, if it exists
     * 
     * @param fooddish The {@link FoodDish fooddish} to update
     * 
     * @return ResponseEntity with updated {@link FoodDish fooddish} object and HTTP
     *         status
     *         of OK if updated<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<FoodDish> updateFoodDish(@RequestBody FoodDish foodDish) {
        LOG.info("PUT /dish " + foodDish);

        try {
            FoodDish result = foodDishDao.updateFoodDish(foodDish);

            if (result == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<FoodDish>(foodDish, HttpStatus.OK);
            }

        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain FoodDish fooddish} with the given id
     * 
     * @param id The id of the {@link FoodDish fooddish} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<FoodDish> deleteFoodDish(@PathVariable int id) {
        LOG.info("DELETE /dish/" + id);

        try {
            boolean found = foodDishDao.deleteFoodDish(id);
            if (found) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
