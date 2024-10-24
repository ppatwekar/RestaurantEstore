package com.estore.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.DataBindingException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.Customer;
import com.estore.api.estoreapi.persistence.DAOs.CustomerDao;
import com.estore.api.estoreapi.services.CustomerService;
import com.fasterxml.jackson.core.exc.StreamWriteException;

@Tag("Service-tier")
public class CustomerServiceTest {
    private CustomerDao mockCustomerDao;
    private CustomerService customerService;

    @BeforeEach
    public void setupOrderController() {
        mockCustomerDao = mock(CustomerDao.class);
        customerService = new CustomerService(mockCustomerDao);
    }

    @Test
    public void testAddCustomer() throws StreamWriteException, DataBindingException, IOException {
        Customer customer = new Customer(1000, "sumit", "123", "sumitkumar", "mahto", "sm5109@gmail.com", "5855009989",
                "191 loden");

        when(mockCustomerDao.save(customer)).thenReturn(customer);

        Customer cus = customerService.addCustomer(customer);

        assertEquals(cus, customer);
    }

    @Test
    public void testFindAllCustomers() {
        List<Customer> customers = new ArrayList<Customer>();
        Customer customer = new Customer(1000, "sumit", "123", "sumitkumar", "mahto", "sm5109@gmail.com", "5855009989",
                "191 loden");

        customers.add(customer);
        when(mockCustomerDao.findAll()).thenReturn(customers);

        List<Customer> cuss = customerService.findAllCustomers();

        assertEquals(cuss, customers);
    }

    @Test
    public void testFindCustomerById() {
        Customer customer = new Customer(1000, "sumit", "123", "sumitkumar", "mahto", "sm5109@gmail.com", "5855009989",
                "191 loden");

        when(mockCustomerDao.findById(1000)).thenReturn(customer);

        Customer cus = customerService.findCustomerById(1000);

        assertEquals(cus, customer);
    }

    @Test
    public void testFindCustomerByUsernamePassword() {
        Customer customer = new Customer(1000, "sumit", "123", "sumitkumar", "mahto", "sm5109@gmail.com", "5855009989",
                "191 loden");

        when(mockCustomerDao.findByUsernameAndPassword(customer.getUsername(), customer.getPassword()))
                .thenReturn(customer);

        Customer cus = customerService.findCustomerByUsernamePassword(customer.getUsername(), customer.getPassword());

        assertEquals(cus, customer);
    }

    @Test
    public void testUpdateCustomer() throws IOException {
        Customer customer = new Customer(1000, "sumit", "123", "sumitkumar", "mahto", "sm5109@gmail.com", "5855009989",
                "191 loden");

        when(mockCustomerDao.update(customer)).thenReturn(customer);

        Customer cus = customerService.updateCustomer(customer);

        assertEquals(cus, customer);
    }

    @Test
    public void testDeleteCustomerById() throws IOException {
        when(mockCustomerDao.deleteById(1000)).thenReturn(true);

        boolean result = customerService.deleteCustomerById(1000);

        assertEquals(true, result);
    }
}
