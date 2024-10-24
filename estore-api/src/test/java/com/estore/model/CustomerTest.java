package com.estore.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.Customer;

@Tag("Model-tier")
public class CustomerTest {
    
    @Test
    public void testConstructorAndGetters() {
        
        int expectedId = 500;
        String expectedUsername = "sm5109 ";
        String expectedPassword = "1010";
        String expectedFirstName = "Sumit";
        String expectedLastName = "Mahto";
        String expectedEmail = "sm5109@gmail.com";
        String expectedPhone = "5855003184";
        String expectedAddress = "191 loden lane";

        Customer customer = new Customer(expectedId, expectedUsername, expectedPassword, expectedFirstName, expectedLastName, expectedEmail, expectedPhone, expectedAddress);

        assertEquals(expectedId, customer.getId());
        assertEquals(expectedUsername, customer.getUsername());
        assertEquals(expectedPassword, customer.getPassword());
        assertEquals(expectedFirstName, customer.getFirstName());
        assertEquals(expectedLastName, customer.getLastName());
        assertEquals(expectedEmail, customer.getEmail());
        assertEquals(expectedPhone, customer.getPhone());
        assertEquals(expectedAddress, customer.getAddress());
    }



    @Test
    public void testSetters() {
        
        int id = 500;
        String username = "sm5109";
        String password = "1010";
        String firstName = "Sumit";
        String lastName = "Mahto";
        String email = "sm5109@gmail.com";
        String phone = "5855003184";
        String address = "191 loden lane";

        Customer customer = new Customer(id, username, password, firstName, lastName, email, phone, address);
        
        String expectedUsername = "sm5110";
        String expectedPassword = "1011";
        String expectedFirstName = "SumitKumar";
        String expectedLastName = "Smith";
        String expectedEmail = "sm5110@gmail.com";
        String expectedPhone = "5855009999";
        String expectedAddress = "195 loden lane";
        
        customer.setUsername(expectedUsername);
        customer.setPassword(expectedPassword);
        customer.setFirstName(expectedFirstName);
        customer.setLastName(expectedLastName);
        customer.setEmail(expectedEmail);
        customer.setPhone(expectedPhone);
        customer.setAddress(expectedAddress);
        
        assertEquals(expectedUsername, customer.getUsername());
        assertEquals(expectedPassword, customer.getPassword());
        assertEquals(expectedFirstName, customer.getFirstName());
        assertEquals(expectedLastName, customer.getLastName());
        assertEquals(expectedEmail, customer.getEmail());
        assertEquals(expectedPhone, customer.getPhone());
        assertEquals(expectedAddress, customer.getAddress());
    }

}
