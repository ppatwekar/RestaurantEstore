package com.estore.api.estoreapi.controller;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.estore.api.estoreapi.model.Customer;
import com.estore.api.estoreapi.persistence.DAOs.CustomerDao;
import com.estore.api.estoreapi.services.CustomerService;

/**
 * Handles the REST API requests for the Customer resource
 * <p>
 * {@literal RestController} Spring annotation identifies this class as a REST
 * API
 * method handler to the Spring framework
 * 
 * @author Sumit Mahto
 */

@RestController
@RequestMapping("customer")
public class CustomerController {

    // Use Service Layer instead of DAO layer directly.

    private CustomerService customerService;

    private static final Logger LOG = Logger.getLogger(CustomerController.class.getName());

    /**
     * Creates the REST API controller to allow request-response cycle.
     * 
     * @param CustomerDao is the {@link CustomerDao} data access object for CRUD
     *                    operations on the data
     */
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Responds to the GET request for the {@linkplain Customer} for the given id
     * 
     * @param id is the id used to locate the {@link Customer}
     * @return Response entity with {@link Customer} object and HTTP status of OK
     *         or HTTP status of NOT_FOUND if the resource is not found. If an
     *         exception occured,
     *         a status of INTERNAL_SERVER_ERROR is returned.
     */

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable int id) {
        LOG.info("GET /customer/" + id);
        try {
            Customer customer = customerService.findCustomerById(id);
            if (customer != null)
                return new ResponseEntity<Customer>(customer, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Responds to the GET request for the {@linkplain Customer} for the given
     * username and password
     * 
     * @param username and @param password are the username and password used to
     *                 locate the {@link Customer}
     * @return Response entity with {@link Customer} object and HTTP status of OK
     *         or HTTP status of NOT_FOUND if the resource is not found. If an
     *         exception occured,
     *         a status of INTERNAL_SERVER_ERROR is returned.
     */

    @GetMapping("/")
    public ResponseEntity<Customer> getCustomerByUsernameAndPassword(@RequestParam("username") String username,
            @RequestParam("password") String password) {
        LOG.info("GET /customer/?username=" + username + "&password=" + password);
        try {
            Customer customer = customerService.findCustomerByUsernamePassword(username, password);
            if (customer != null)
                return new ResponseEntity<Customer>(customer, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Responds to the GET request for all {@linkplain Customer customers}
     * 
     * @return Response entity with array of {@link Customer} objects (may be empty)
     *         and
     *         HTTP status of OK<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     */

    @GetMapping("")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        LOG.info("GET /customers");
        try {
            List<Customer> customers = customerService.findAllCustomers();
            return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Customer customer} with the provided customer object
     * 
     * @param newCustomer - The {@link Customer customer} to create
     * 
     * @return ResponseEntity with created {@link Customer customer} object and HTTP
     *         status of CREATED<br>
     *         ResponseEntity with HTTP status of CONFLICT if {@link Customer
     *         Customer} object already exists<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer newCustomer) {
        LOG.info("POST /customer " + newCustomer);

        try {
            Customer savedCustomer = customerService.addCustomer(newCustomer);
            if (savedCustomer != null) {
                return new ResponseEntity<Customer>(savedCustomer, HttpStatus.CREATED);
            } else
                return new ResponseEntity<>(HttpStatus.CONFLICT);

        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain Customer customer} with the provided
     * {@linkplain Customer customer}
     * object, if it exists
     * 
     * @param fooddish The {@link Customer customer} to update
     * 
     * @return ResponseEntity with updated {@link Customer customer} object and HTTP
     *         status
     *         of OK if updated<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
        LOG.info("PUT /customer " + customer);

        try {
            Customer result = customerService.updateCustomer(customer);

            if (result == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<Customer>(customer, HttpStatus.OK);
            }

        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Customer customer} with the given id
     * 
     * @param id The id of the {@link Customer customer} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable int id) {
        LOG.info("DELETE /customer/" + id);

        try {
            boolean found = customerService.deleteCustomerById(id);
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
