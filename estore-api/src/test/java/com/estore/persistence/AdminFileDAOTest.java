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
import com.estore.api.estoreapi.model.Admin;
import com.estore.api.estoreapi.persistence.DAOFiles.AdminFileDAO;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Persistence-tier")
public class AdminFileDAOTest {
    AdminFileDAO adminFileDAO;
    Admin[] testAdmines;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * 
     * @throws IOException
     */
    @BeforeEach
    public void setupAdminFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testAdmines = new Admin[3];
        testAdmines[0] = new Admin(99, "admin1", "123");
        testAdmines[1] = new Admin(100, "admin2", "234");
        testAdmines[2] = new Admin(101, "admin3", "345");

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the Admin array above
        when(mockObjectMapper
                .readValue(new File("doesnt_matter.txt"), Admin[].class))
                .thenReturn(testAdmines);
        adminFileDAO = new AdminFileDAO("doesnt_matter.txt", mockObjectMapper);
    }

    @Test
    public void testGetAdmines() {

        // Invoke
        List<Admin> admines = adminFileDAO.findAll();

        // Analyze
        assertEquals(admines.size(), testAdmines.length);
        for (int i = 0; i < testAdmines.length; ++i)
            assertEquals(admines.get(i), testAdmines[i]);
    }

    @Test
    public void testFindAdmines() {

        // Invoke
        Admin admin = adminFileDAO.findByUsernameAndPassword("admin1", "123");

        // Analyze
        assertEquals(admin, testAdmines[0]);
    }

    @Test
    public void testFindAdminesFail() {

        // Invoke
        Admin admin = adminFileDAO.findByUsernameAndPassword("adm1", "123");

        // Analyze
        assertEquals(null, admin);
    }

    @Test
    public void testDeleteAdmin() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> adminFileDAO.deleteById(99),
                "Unexpected exception thrown");

        // Analzye
        assertEquals(true, result);
        // We check the internal tree map size against the length
        // of the test Admines array - 1 (because of the delete)
        // Because Admines attribute of AdminFileDAO is package private
        // we can access it directly

        assertEquals(adminFileDAO.findAll().size(), testAdmines.length - 1);
    }

    @Test
    public void testCreateAdmin() {
        // Setup
        Admin admin = new Admin(102, "admin4", "123");
        // Invoke
        Admin result = assertDoesNotThrow(() -> adminFileDAO.save(admin),
                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);

        Admin actual = adminFileDAO.findById(admin.getId());
        assertEquals(actual.getId(), admin.getId());
        assertEquals(actual.getUsername(), admin.getUsername());
        assertEquals(actual.getPassword(), admin.getPassword());
    }

    @Test
    public void testCreateAdminFail() {
        // Setup
        Admin admin = new Admin(102, "admin3", "123");
        // Invoke
        Admin result = assertDoesNotThrow(() -> adminFileDAO.save(admin),
                "Unexpected exception thrown");

        // Analyze
        assertEquals(null, result);
    }

    @Test
    public void testUpdateAdmin() {
        // Setup
        Admin admin = new Admin(99, "admin4", "123");

        // Invoke
        Admin result = assertDoesNotThrow(() -> adminFileDAO.update(admin),
                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);

        Admin actual = adminFileDAO.findById(admin.getId());
        assertEquals(actual, admin);
    }

    @Test
    public void testUpdateAdminFail() {
        // Setup
        Admin admin = new Admin(9999, "admin4", "123");

        // Invoke
        Admin result = assertDoesNotThrow(() -> adminFileDAO.update(admin),
                "Unexpected exception thrown");

        // Analyze
        assertEquals(null, result);
    }

    @Test
    public void testSaveException() throws IOException {
        doThrow(new IOException())
                .when(mockObjectMapper)
                .writeValue(any(File.class), any(Admin[].class));

        Admin admin = new Admin(99, "admin4", "123");

        assertThrows(IOException.class,
                () -> adminFileDAO.save(admin),
                "IOException not thrown");
    }

    @Test
    public void testGetAdminNotFound() {

        // Invoke
        Admin admin = adminFileDAO.findById(98);

        // Analyze
        assertEquals(null, admin);
    }

    @Test
    public void testDeleteAdminNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> adminFileDAO.deleteById(98),
                "Unexpected exception thrown");

        // Analyze
        assertEquals(adminFileDAO.findAll().size(), testAdmines.length);
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
        // from the AdminFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
                .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"), Admin[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                () -> new AdminFileDAO("doesnt_matter.txt", mockObjectMapper),
                "IOException not thrown");
    }

    @Test
    public void testExistsByID() {
        adminFileDAO.delete(null);
        boolean result = adminFileDAO.existsById(10);

        assertEquals(false, result);
    }
}
