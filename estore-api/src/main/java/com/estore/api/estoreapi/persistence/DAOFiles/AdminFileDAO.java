package com.estore.api.estoreapi.persistence.DAOFiles;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Admin;
import com.estore.api.estoreapi.persistence.DAOs.AdminDao;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implements the functionality for JSON file-based peristance for Admin
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of
 * this
 * class and injects the instance into other classes as needed
 * 
 */
@Component
public class AdminFileDAO implements AdminDao {

    Map<Integer, Admin> admins;

    private ObjectMapper objectMapper;
    private static int nextId;
    private String filename;

    /**
     * Creates a Admin File Data Access Object
     * 
     * @param filename     Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization
     *                     and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public AdminFileDAO(@Value("${admin.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    /**
     * Loads {@linkplain Admin admin} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        this.admins = new TreeMap<>();
        AdminFileDAO.nextId = 0;

        Admin[] adminArray = objectMapper.readValue(new File(filename), Admin[].class);

        for (Admin admin : adminArray) {
            this.admins.put(admin.getId(), admin);
            if (admin.getId() > AdminFileDAO.nextId) {
                AdminFileDAO.nextId = admin.getId();
            }
        }

        ++AdminFileDAO.nextId;
        return true;

    }

    /**
     * Generates an array of {@linkplain Admin admins} from the tree map
     * 
     * @return The array of {@link Admin admins}, may be empty
     */
    private Admin[] getAdminArray() {
        ArrayList<Admin> list = new ArrayList<>();
        for (Admin admin : this.admins.values()) {
            list.add(admin);
        }

        Admin[] arr = new Admin[list.size()];
        list.toArray(arr);
        return arr;
    }

    /**
     * Saves the {@linkplain Admin admin} from the map into the file as an array of
     * JSON objects
     * 
     * @return true if the {@link Admin admins} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Admin[] arr = getAdminArray();

        objectMapper.writeValue(new File(this.filename), arr);
        return true;
    }

    /**
     * Generates the next id for a new {@linkplain Admin admin}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = AdminFileDAO.nextId;
        ++AdminFileDAO.nextId;
        return id;
    }

    /**
     * Creates and saves a Admin
     * The id of admin object is ignored and a new id is created`
     * 
     * @param newAdmin the {@link Admin} object to be created
     * @return new {@link Admin} object if successfully created, else null
     */
    @Override
    public Admin save(Admin newAdmin) throws IOException {

        Admin savedAdmin = null;
        synchronized (this.admins) {
            List<String> usernameList = this.admins.entrySet().stream().map(e -> e.getValue().getUsername())
                    .collect(Collectors.toList());

            boolean isAdminPresent = false;

            for (String username : usernameList) {
                if (username.equals(newAdmin.getUsername()))
                    isAdminPresent = true;
            }

            if (!isAdminPresent) {
                savedAdmin = new Admin(nextId(), newAdmin.getUsername(), newAdmin.getPassword());
                this.admins.put(savedAdmin.getId(), savedAdmin);
                List<Admin> allAdmins = findAll();

                objectMapper.writeValue(new File(this.filename), allAdmins.toArray(new Admin[0]));
            }

            return savedAdmin;
        }

    }

    /**
     * Gets all the {@linkplain Admin admins} from the data
     * 
     * @return An array of {@link Admin} objects
     */
    @Override
    public List<Admin> findAll() {
        synchronized (this.admins) {
            return admins.entrySet().stream().map(e -> e.getValue()).collect(Collectors.toList());
        }
    }

    /**
     * Gets admin according to id
     * 
     * @param id of {@link Admin}
     * @return {@link Admin} with the given id, else null
     */
    @Override
    public Admin findById(Integer id) {
        synchronized (this.admins) {
            if (this.admins.containsKey(id)) {
                return this.admins.get(id);
            } else
                return null;
        }
    }

    @Override
    public boolean existsById(Integer id) {
        return false;
    }

    @Override
    public void delete(Admin entity) {

    }

    @Override
    public boolean deleteById(Integer id) throws IOException {
        synchronized (this.admins) {
            if (this.admins.containsKey(id)) {
                this.admins.remove(id);
                return save();
            } else
                return false;
        }

    }

    @Override
    public Admin update(Admin entitiy) throws IOException {
        synchronized (this.admins) {
            if (this.admins.containsKey(entitiy.getId())) {
                this.admins.put(entitiy.getId(), entitiy);
                save();
                return entitiy;
            } else
                return null;
        }
    }

    /**
     * Gets admin according to the username and password
     * 
     * @param username and @param password of {@link Admin}
     * @return {@link Admin} with the given username and password, else null
     */
    @Override
    public Admin findByUsernameAndPassword(String username, String password) {
        List<Admin> admins = findAll();
        for (Admin admin : admins) {
            if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                return admin;
            }
        }
        return null;
    }

}
