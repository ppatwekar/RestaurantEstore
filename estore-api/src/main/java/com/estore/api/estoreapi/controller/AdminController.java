package com.estore.api.estoreapi.controller;

import java.io.IOException;
import java.util.List;
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

import com.estore.api.estoreapi.model.Admin;
import com.estore.api.estoreapi.persistence.DAOs.AdminDao;

/**
 * Handles the REST API requests for the Admin resource
 * <p>
 * {@literal RestController} Spring annotation identifies this class as a REST
 * API
 * method handler to the Spring framework
 * 
 * @author Huadong Zhang
 */

@RestController
@RequestMapping("admin")
public class AdminController {

    private AdminDao adminDao;

    private static final Logger LOG = Logger.getLogger(AdminController.class.getName());

    /**
     * Creates the REST API controller to allow request-response cycle.
     * 
     * @param AdminDao is the {@link AdminDao} data access object for CRUD
     *                 operations on the data
     */
    public AdminController(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    /**
     * Responds to the GET request for the {@linkplain Admin} for the given id
     * 
     * @param id is the id used to locate the {@link Admin}
     * @return Response entity with {@link Admin} object and HTTP status of OK
     *         or HTTP status of NOT_FOUND if the resource is not found. If an
     *         exception occured,
     *         a status of INTERNAL_SERVER_ERROR is returned.
     */

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdmin(@PathVariable int id) {
        LOG.info("GET /admin/" + id);
        try {
            Admin admin = adminDao.findById(id);
            if (admin != null)
                return new ResponseEntity<Admin>(admin, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Responds to the GET request for the {@linkplain Admin} for the given username
     * and password
     * 
     * @param username and @param password are the username and password used to
     *                 locate the {@link Admin}
     * @return Response entity with {@link Admin} object and HTTP status of OK
     *         or HTTP status of NOT_FOUND if the resource is not found. If an
     *         exception occured,
     *         a status of INTERNAL_SERVER_ERROR is returned.
     */

    @GetMapping("/")
    public ResponseEntity<Admin> getAdminByUsernameAndPassword(@RequestParam("username") String username,
            @RequestParam("password") String password) {
        LOG.info("GET /admin/?username=" + username + "&password=" + password);
        try {
            Admin admin = adminDao.findByUsernameAndPassword(username, password);
            if (admin != null)
                return new ResponseEntity<Admin>(admin, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Responds to the GET request for all {@linkplain Admin admins}
     * 
     * @return Response entity with array of {@link Admin} objects (may be empty)
     *         and
     *         HTTP status of OK<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     */

    @GetMapping("")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        LOG.info("GET /admins");
        try {
            List<Admin> admins = adminDao.findAll();
            return new ResponseEntity<List<Admin>>(admins, HttpStatus.OK);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Admin admin} with the provided admin object
     * 
     * @param newAdmin - The {@link Admin admin} to create
     * 
     * @return ResponseEntity with created {@link Admin admin} object and HTTP
     *         status of CREATED<br>
     *         ResponseEntity with HTTP status of CONFLICT if {@link Admin
     *         Admin} object already exists<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin newAdmin) {
        LOG.info("POST /admin " + newAdmin);

        try {
            Admin savedAdmin = adminDao.save(newAdmin);
            if (savedAdmin != null) {
                return new ResponseEntity<Admin>(savedAdmin, HttpStatus.CREATED);
            } else
                return new ResponseEntity<>(HttpStatus.CONFLICT);

        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain Admin admin} with the provided
     * {@linkplain Admin admin}
     * object, if it exists
     * 
     * @param fooddish The {@link Admin admin} to update
     * 
     * @return ResponseEntity with updated {@link Admin admin} object and HTTP
     *         status
     *         of OK if updated<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Admin> updateAdmin(@RequestBody Admin admin) {
        LOG.info("PUT /admin " + admin);

        try {
            Admin result = adminDao.update(admin);

            if (result == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<Admin>(result, HttpStatus.OK);
            }

        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Admin admin} with the given id
     * 
     * @param id The id of the {@link Admin admin} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Admin> deleteAdmin(@PathVariable int id) {
        LOG.info("DELETE /dish/" + id);

        try {
            boolean found = adminDao.deleteById(id);
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
