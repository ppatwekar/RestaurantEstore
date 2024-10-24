package com.estore.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import com.estore.api.estoreapi.controller.AdminController;
import com.estore.api.estoreapi.model.Admin;
import com.estore.api.estoreapi.persistence.DAOs.AdminDao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Tag("Controller-tier")
public class AdminControllerTest {
    private AdminController adminController;
    private AdminDao mockadminDAO;

    @BeforeEach
    public void setupadminController() {
        mockadminDAO = mock(AdminDao.class);
        adminController = new AdminController(mockadminDAO);
    }

    @Test
    public void testGetAdmin() throws IOException {
        // Setup
        Admin admin = new Admin(99, "admin1", "123");

        when(mockadminDAO.findById(admin.getId())).thenReturn(admin);

        // Invoke
        ResponseEntity<Admin> response = adminController.getAdmin(admin.getId());

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(admin, response.getBody());
    }

    @Test
    public void testGetAdminByUsernameAndPassword() {
        // Setup
        Admin admin = new Admin(1000, "admin3", "123");

        when(mockadminDAO.findByUsernameAndPassword(admin.getUsername(), admin.getPassword())).thenReturn(admin);

        // Invoke
        ResponseEntity<Admin> response = adminController.getAdminByUsernameAndPassword(admin.getUsername(),
                admin.getPassword());

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(admin, response.getBody());
    }

    @Test
    public void testGetAdminByUsernameAndPasswordNotFound() {
        // Setup
        Admin admin = new Admin(1000, "admin3", "123");

        when(mockadminDAO.findByUsernameAndPassword(admin.getUsername(), admin.getPassword())).thenReturn(null);

        // Invoke
        ResponseEntity<Admin> response = adminController.getAdminByUsernameAndPassword(admin.getUsername(),
                admin.getPassword());

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetAdminByUsernameAndPasswordHandleException() throws Exception {
        doThrow(new RuntimeException())
                .when(mockadminDAO).findByUsernameAndPassword("admin3", "123");

        // Invoke
        ResponseEntity<Admin> response = adminController.getAdminByUsernameAndPassword("admin3", "123");

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetAdminNotFound() throws Exception {
        // Setup
        int adminId = 99;

        // no admin found
        when(mockadminDAO.findById(adminId)).thenReturn(null);

        // Invoke
        ResponseEntity<Admin> response = adminController.getAdmin(adminId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetAdminHandleException() throws Exception {
        // Setup
        int adminId = 98;

        doThrow(new RuntimeException()).when(mockadminDAO).findById(adminId);

        // Invoke
        ResponseEntity<Admin> response = adminController.getAdmin(adminId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testCreateAdmin() throws IOException { // createAdmin may throw IOException
        // Setup
        Admin admin = new Admin(99, "admin1", "123");

        // creation and save
        when(mockadminDAO.save(admin)).thenReturn(admin);

        // Invoke
        ResponseEntity<Admin> response = adminController.createAdmin(admin);

        // Analyze
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(admin, response.getBody());
    }

    @Test
    public void testCreateAdminFailed() throws IOException { // createAdmin may throw IOException
        // Setup
        Admin admin = new Admin(99, "admin1", "123");
        // when createAdmin is called, return false simulating failed
        // creation and save
        when(mockadminDAO.save(admin)).thenReturn(null);

        // Invoke
        ResponseEntity<Admin> response = adminController.createAdmin(admin);

        // Analyze
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testCreateAdminHandleException() throws IOException { // createAdmin may throw IOException
        // Setup
        Admin admin = new Admin(99, "admin1", "123");

        // When createAdmin is called on the Mock Admin DAO, throw an IOException
        doThrow(new RuntimeException()).when(mockadminDAO).save(admin);

        // Invoke
        ResponseEntity<Admin> response = adminController.createAdmin(admin);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testUpdateAdmin() throws IOException { // updateAdmin may throw IOException
        // Setup
        Admin admin = new Admin(99, "admin1", "123");
        // when updateAdmin is called, return true simulating successful
        // update and save
        when(mockadminDAO.update(admin)).thenReturn(admin);
        ResponseEntity<Admin> response = adminController.updateAdmin(admin);
        admin.setUsername("admin2");

        // Invoke
        response = adminController.updateAdmin(admin);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(admin, response.getBody());
    }

    @Test
    public void testUpdateAdminFailed() throws IOException { // updateAdmin may throw IOException
        // Setup
        Admin admin = new Admin(99, "admin1", "123");
        // when updateAdmin is called, return true simulating successful
        // update and save
        when(mockadminDAO.update(admin)).thenReturn(null);

        // Invoke
        ResponseEntity<Admin> response = adminController.updateAdmin(admin);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateAdminHandleException() throws IOException { // updateAdmin may throw IOException
        // Setup
        Admin admin = new Admin(99, "admin1", "123");
        // When updateAdmin is called on the Mock Admin DAO, throw an IOException
        doThrow(new IOException()).when(mockadminDAO).update(admin);

        // Invoke
        ResponseEntity<Admin> response = adminController.updateAdmin(admin);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetAdmines() throws IOException { // getAdmines may throw IOException
        // Setup
        List<Admin> admines = new ArrayList<Admin>();
        admines.add(new Admin(99, "admin1", "123"));
        admines.add(new Admin(100, "admin2", "234"));
        // When getAdmines is called return the admines created above
        when(mockadminDAO.findAll()).thenReturn(admines);

        // Invoke
        ResponseEntity<List<Admin>> response = adminController.getAllAdmins();

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(admines, response.getBody());
    }

    @Test
    public void testGetAdminesHandleException() throws IOException { // getAdmines may throw IOException
        // Setup
        // When getAdmines is called on the Mock Admin DAO, throw an IOException
        doThrow(new RuntimeException()).when(mockadminDAO).findAll();

        // Invoke
        ResponseEntity<List<Admin>> response = adminController.getAllAdmins();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteAdmin() throws IOException { // deleteAdmin may throw IOException
        // Setup
        int adminId = 99;
        // when deleteAdmin is called return true, simulating successful deletion
        when(mockadminDAO.deleteById(adminId)).thenReturn(true);

        // Invoke
        ResponseEntity<Admin> response = adminController.deleteAdmin(adminId);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteAdminNotFound() throws IOException { // deleteAdmin may throw IOException
        // Setup
        int adminId = 99;
        // when deleteAdmin is called return false, simulating failed deletion
        when(mockadminDAO.deleteById(adminId)).thenReturn(false);

        // Invoke
        ResponseEntity<Admin> response = adminController.deleteAdmin(adminId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteAdminHandleException() throws IOException { // deleteAdmin may throw IOException
        // Setup
        int adminId = 99;
        // When deleteAdmin is called on the Mock Admin DAO, throw an IOException
        doThrow(new IOException()).when(mockadminDAO).deleteById(adminId);

        // Invoke
        ResponseEntity<Admin> response = adminController.deleteAdmin(adminId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
