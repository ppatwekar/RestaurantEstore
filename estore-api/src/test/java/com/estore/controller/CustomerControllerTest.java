package com.estore.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.estore.api.estoreapi.controller.CustomerController;
import com.estore.api.estoreapi.model.Customer;
import com.estore.api.estoreapi.services.CustomerService;

@Tag("Controller-tier")
public class CustomerControllerTest {

    private CustomerController customerController;
    private CustomerService mockCustomerService;

    @BeforeEach
    public void setupCustomerController() {
        mockCustomerService = mock(CustomerService.class);
        customerController = new CustomerController(mockCustomerService);
    }

    @Test
    public void testGetCustomer() {
        // Setup
        Customer customer = new Customer(1000, "sumit", "123", "sumitkumar", "mahto", "sm5109@gmail.com", "5855009989",
                "191 loden");

        when(mockCustomerService.findCustomerById(customer.getId())).thenReturn(customer);

        // Invoke
        ResponseEntity<Customer> response = customerController.getCustomer(customer.getId());

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
    }

    @Test
    public void testGetCustomerByUsernameAndPassword() {
        // Setup
        Customer customer = new Customer(1000, "sumit", "123", "sumitkumar", "mahto", "sm5109@gmail.com", "5855009989",
                "191 loden");

        when(mockCustomerService.findCustomerByUsernamePassword(customer.getUsername(), customer.getPassword()))
                .thenReturn(customer);

        // Invoke
        ResponseEntity<Customer> response = customerController.getCustomerByUsernameAndPassword(customer.getUsername(),
                customer.getPassword());

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
    }

    @Test
    public void testGetCustomerByUsernameAndPasswordNotFound() {
        // Setup
        Customer customer = new Customer(1000, "sumit", "123", "sumitkumar", "mahto", "sm5109@gmail.com", "5855009989",
                "191 loden");

        when(mockCustomerService.findCustomerByUsernamePassword(customer.getUsername(), customer.getPassword()))
                .thenReturn(null);

        // Invoke
        ResponseEntity<Customer> response = customerController.getCustomerByUsernameAndPassword(customer.getUsername(),
                customer.getPassword());

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetCustomerByUsernameAndPasswordHandleException() throws Exception {

        doThrow(new RuntimeException()).when(
                mockCustomerService).findCustomerByUsernamePassword("sumit", "123");

        // Invoke
        ResponseEntity<Customer> response = customerController.getCustomerByUsernameAndPassword("sumit", "123");

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testCustomerDoesNotExists() throws Exception {
        // Setup
        int customerId = 101;

        // no admin found
        when(mockCustomerService.findCustomerById(customerId)).thenReturn(null);

        // Invoke
        ResponseEntity<Customer> response = customerController.getCustomer(101);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetCustomerExceptionHandler() throws Exception {
        // Setup
        int customerId = 98;

        doThrow(new RuntimeException()).when(mockCustomerService).findCustomerById(customerId);

        // Invoke
        ResponseEntity<Customer> response = customerController.getCustomer(customerId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testCreateCustomer() throws IOException { // createAdmin may
                                                          // throw IOException
        // Setup
        Customer customer = new Customer(7000, "sumit", "123", "sumitkumar", "mahto", "sm5109@gmail.com", "5855009989",
                "191 loden");

        // creation and save
        when(mockCustomerService.addCustomer(customer)).thenReturn(customer);

        // Invoke
        ResponseEntity<Customer> response = customerController.createCustomer(customer);

        // Analyze
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(customer, response.getBody());
    }

    @Test
    public void testCreateCustomerFailed() throws IOException { // createAdmin may throw IOException
        // Setup
        Customer customer = new Customer(1000, "sumit", "123", "sumitkumar", "mahto", "sm5109@gmail.com", "5855009989",
                "191 loden");
        when(mockCustomerService.addCustomer(customer)).thenReturn(null);

        // Invoke
        ResponseEntity<Customer> response = customerController.createCustomer(customer);

        // Analyze
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testCreateCustomerExceptionHandler() throws IOException { // createAdmin may throw IOException

        Customer customer = new Customer(1000, "sumit", "123", "sumitkumar", "mahto", "sm5109@gmail.com", "5855009989",
                "191 loden");

        // When createAdmin is called on the Mock Admin DAO, throw an IOException
        doThrow(new RuntimeException()).when(mockCustomerService).addCustomer(customer);

        // Invoke
        ResponseEntity<Customer> response = customerController.createCustomer(customer);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testUpdateCustomer() throws IOException { // updateAdmin may throw IOException
        // Setup
        Customer customer = new Customer(1000, "sumit", "123", "sumitkumar", "mahto", "sm5109@gmail.com", "5855009989",
                "191 loden");

        when(mockCustomerService.updateCustomer(customer)).thenReturn(customer);
        ResponseEntity<Customer> response = customerController.updateCustomer(customer);
        customer.setUsername("amit");

        // Invoke
        response = customerController.updateCustomer(customer);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
    }

    @Test
    public void testUpdateCustomerFailed() throws IOException { // updateAdmin may throw IOException
        // Setup
        Customer customer = new Customer(1000, "sumit", "123", "sumitkumar", "mahto", "sm5109@gmail.com", "5855009989",
                "191 loden");

        when(mockCustomerService.updateCustomer(customer)).thenReturn(null);

        // Invoke
        ResponseEntity<Customer> response = customerController.updateCustomer(customer);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateCustomerExceptionHandler() throws IOException { // updateAdmin may throw IOException
        Customer customer = new Customer(1000, "sumit", "123", "sumitkumar", "mahto", "sm5109@gmail.com", "5855009989",
                "191 loden");

        doThrow(new IOException()).when(mockCustomerService).updateCustomer(customer);

        // Invoke
        ResponseEntity<Customer> response = customerController.updateCustomer(customer);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetCustomers() throws IOException { // getAdmines may throw IOException
        // Setup
        List<Customer> customers = new ArrayList<Customer>();

        customers.add(new Customer(1000, "sumit", "123", "sumitkumar", "mahto", "sm5109@gmail.com", "5855009989",
                "191 loden"));
        customers.add(new Customer(1001, "amit", "1234", "amitkumar", "mendy", "am5109@gmail.com", "5855009909",
                "195 loden"));
        // When getAdmines is called return the admines created above
        when(mockCustomerService.findAllCustomers()).thenReturn(customers);

        // Invoke
        ResponseEntity<List<Customer>> response = customerController.getAllCustomers();

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customers, response.getBody());
    }

    @Test
    public void testGetCustomerHandleException() throws IOException { // getAdmines may throw IOException
        // Setup
        // When getAdmines is called on the Mock Admin DAO, throw an IOException
        doThrow(new RuntimeException()).when(mockCustomerService).findAllCustomers();

        // Invoke
        ResponseEntity<List<Customer>> response = customerController.getAllCustomers();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteCustomer() throws IOException { // deleteAdmin may throw IOException
        // Setup
        int customerId = 99;
        // when deleteAdmin is called return true, simulating successful deletion
        when(mockCustomerService.deleteCustomerById(customerId)).thenReturn(true);

        // Invoke
        ResponseEntity<Customer> response = customerController.deleteCustomer(customerId);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteCustomerNotFound() throws IOException { // deleteAdmin may throw IOException
        // Setup
        int customerId = 99;
        // when deleteAdmin is called return false, simulating failed deletion
        when(mockCustomerService.deleteCustomerById(customerId)).thenReturn(false);

        // Invoke
        ResponseEntity<Customer> response = customerController.deleteCustomer(customerId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteCustomerHandleException() throws IOException { // deleteAdmin may throw IOException
        // Setup
        int customerId = 99;
        // When deleteAdmin is called on the Mock Admin DAO, throw an IOException
        doThrow(new IOException()).when(mockCustomerService).deleteCustomerById(customerId);

        // Invoke
        ResponseEntity<Customer> response = customerController.deleteCustomer(customerId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
