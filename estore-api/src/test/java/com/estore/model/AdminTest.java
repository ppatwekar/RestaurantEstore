package com.estore.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.Admin;

@Tag("Model-tier")
public class AdminTest{
    @Test
    public void testCtor() {
        // Setup
        int expected_id = 99;
        String expected_username = "admin";
        String expected_password = "1234";

        // Invoke
        Admin admin = new Admin(expected_id, expected_username, expected_password);

        // Analyze
        assertEquals(expected_id,admin.getId());
        assertEquals(expected_username,admin.getUsername());
        assertEquals(expected_password,admin.getPassword());
    }

    @Test
    public void testUsername() {
        // Setup
        int id = 99;
        String username = "admin";
        String password = "1234";
        Admin admin = new Admin(id, username, password);

        String expected_username = "RiceAdmin";

        // Invoke
        admin.setUsername(expected_username);

        // Analyze
        assertEquals(expected_username,admin.getUsername());
    }

    @Test
    public void testPassword() {
         // Setup
         int id = 99;
         String username = "admin";
         String password = "1234";
         Admin admin = new Admin(id, username, password);
 
         String expected_password = "2345";
 
         // Invoke
         admin.setPassword(expected_password);
 
         // Analyze
         assertEquals(expected_password,admin.getPassword());
    }


    
}