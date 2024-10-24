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

import java.util.List;
import com.estore.api.estoreapi.model.Customer;
import com.estore.api.estoreapi.persistence.DAOFiles.CustomerFileDAO;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Persistence-tier")
public class CustomerFileDAOTest {
    CustomerFileDAO customerFileDAO;
    Customer[] testCustomers;
    ObjectMapper mockObjectMapper;

    @BeforeEach
    public void setupCustomerFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testCustomers = new Customer[3];
        testCustomers[0] = new Customer(1, "cus1", "123", "t1", "t2", "t3", "t4", "t5");
        testCustomers[1] = new Customer(2, "cus2", "1234", "t6", "t7", "t8", "t9", "t10");
        testCustomers[2] = new Customer(3, "cus3", "12345", "t11", "t12", "t13", "t14", "t15");

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the Customer array above
        when(mockObjectMapper
                .readValue(new File("doesnt_matter.txt"), Customer[].class))
                .thenReturn(testCustomers);
        customerFileDAO = new CustomerFileDAO("doesnt_matter.txt", mockObjectMapper);
    }

    @Test
    public void testGetCustomers() {

        // Invoke
        List<Customer> customers = customerFileDAO.findAll();

        // Analyze
        assertEquals(customers.size(), testCustomers.length);
        for (int i = 0; i < testCustomers.length; ++i)
            assertEquals(customers.get(i), testCustomers[i]);
    }

    @Test
    public void testFindCustomers() {

        // Invoke
        Customer customer = customerFileDAO.findByUsernameAndPassword("cus1", "123");

        // Analyze
        assertEquals(customer, testCustomers[0]);
    }

    @Test
    public void testFindCustomersFail() {

        // Invoke
        Customer customer = customerFileDAO.findByUsernameAndPassword("cs1", "123");

        // Analyze
        assertEquals(null, customer);
    }

    @Test
    public void testDeleteCustomer() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> customerFileDAO.deleteById(1),
                "Unexpected exception thrown");

        // Analzye
        assertEquals(true, result);
        // We check the internal tree map size against the length
        // of the test Customers array - 1 (because of the delete)
        // Because Customers attribute of CustomerFileDAO is package private
        // we can access it directly

        assertEquals(customerFileDAO.findAll().size(), testCustomers.length - 1);
    }

    @Test
    public void testCreateCustomer() {
        // Setup
        Customer customer = new Customer(4, "cus4", "123", "t1", "t2", "t3", "t4", "t5");
        // Invoke
        Customer result = assertDoesNotThrow(() -> customerFileDAO.save(customer),
                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);

        Customer actual = customerFileDAO.findById(customer.getId());
        assertEquals(actual.getId(), customer.getId());
        assertEquals(actual.getUsername(), customer.getUsername());
        assertEquals(actual.getPassword(), customer.getPassword());
        assertEquals(actual.getFirstName(), customer.getFirstName());
        assertEquals(actual.getLastName(), customer.getLastName());
        assertEquals(actual.getAddress(), customer.getAddress());
        assertEquals(actual.getPhone(), customer.getPhone());
        assertEquals(actual.getEmail(), customer.getEmail());
    }

    @Test
    public void testCreateCustomerFail() {
        // Setup
        Customer customer = new Customer(4, "cus3", "123", "t1", "t2", "t3", "t4", "t5");
        // Invoke
        Customer result = assertDoesNotThrow(() -> customerFileDAO.save(customer),
                "Unexpected exception thrown");

        // Analyze
        assertEquals(null, result);
    }

    @Test
    public void testUpdateCustomer() {
        // Setup
        Customer customer = new Customer(1, "cus4", "123", "t1", "t2", "t3", "t4", "t5");

        // Invoke
        Customer result = assertDoesNotThrow(() -> customerFileDAO.update(customer),
                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);

        Customer actual = customerFileDAO.findById(customer.getId());
        assertEquals(actual, customer);
    }

    @Test
    public void testUpdateCustomerFail() {
        // Setup
        Customer customer = new Customer(100, "cus4", "123", "t1", "t2", "t3", "t4", "t5");

        // Invoke
        Customer result = assertDoesNotThrow(() -> customerFileDAO.update(customer),
                "Unexpected exception thrown");

        // Analyze
        assertEquals(null, result);
    }

    @Test
    public void testSaveException() throws IOException {
        doThrow(new IOException())
                .when(mockObjectMapper)
                .writeValue(any(File.class), any(Customer[].class));

        Customer customer = new Customer(1, "cus4", "123", "t1", "t2", "t3", "t4", "t5");

        assertThrows(IOException.class,
                () -> customerFileDAO.save(customer),
                "IOException not thrown");
    }

    @Test
    public void testGetCustomerNotFound() {

        // Invoke
        Customer customer = customerFileDAO.findById(98);

        // Analyze
        assertEquals(null, customer);
    }

    @Test
    public void testDeleteCustomerNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> customerFileDAO.deleteById(98),
                "Unexpected exception thrown");

        // Analyze
        assertEquals(customerFileDAO.findAll().size(), testCustomers.length);
        assertEquals(false, result);
    }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        // We want to simulate with a Mock Object Mapper that an
        // exception was raised during JSON object deseerialization
        // into Java objects
        // When the Mock Object Mapper readValue method is called
        // from the CustomerFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
                .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"), Customer[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                () -> new CustomerFileDAO("doesnt_matter.txt", mockObjectMapper),
                "IOException not thrown");
    }

    @Test
    public void testExistsByID() {
        customerFileDAO.delete(null);
        boolean result = customerFileDAO.existsById(10);

        assertEquals(false, result);
    }
}
