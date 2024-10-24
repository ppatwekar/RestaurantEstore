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

import com.estore.api.estoreapi.model.Customer;
import com.estore.api.estoreapi.persistence.DAOs.CustomerDao;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implements the functionality for JSON file-based peristance for Customer
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of
 * this
 * class and injects the instance into other classes as needed
 * 
 */
@Component
public class CustomerFileDAO implements CustomerDao {

    Map<Integer, Customer> customers;

    private ObjectMapper objectMapper;
    private static int nextId;
    private String filename;

    /**
     * Creates a Customer File Data Access Object
     * 
     * @param filename     Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization
     *                     and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public CustomerFileDAO(@Value("${customer.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    /**
     * Loads {@linkplain Customer customer} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        this.customers = new TreeMap<>();
        CustomerFileDAO.nextId = 0;

        Customer[] customerArray = objectMapper.readValue(new File(filename), Customer[].class);

        for (Customer customer : customerArray) {
            this.customers.put(customer.getId(), customer);
            if (customer.getId() > CustomerFileDAO.nextId) {
                CustomerFileDAO.nextId = customer.getId();
            }
        }

        ++CustomerFileDAO.nextId;
        return true;

    }

    /**
     * Generates an array of {@linkplain Customer customers} from the tree map
     * 
     * @return The array of {@link Customer customers}, may be empty
     */
    private Customer[] getCustomerArray() {
        ArrayList<Customer> list = new ArrayList<>();
        for (Customer customer : this.customers.values()) {
            list.add(customer);
        }

        Customer[] arr = new Customer[list.size()];
        list.toArray(arr);
        return arr;
    }

    /**
     * Saves the {@linkplain Customer customer} from the map into the file as an
     * array of JSON objects
     * 
     * @return true if the {@link Customer customers} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Customer[] arr = getCustomerArray();

        objectMapper.writeValue(new File(this.filename), arr);
        return true;
    }

    /**
     * Generates the next id for a new {@linkplain Customer customer}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = CustomerFileDAO.nextId;
        ++CustomerFileDAO.nextId;
        return id;
    }

    /**
     * Creates and saves a Customer
     * The id of customer object is ignored and a new id is created`
     * 
     * @param newCustomer the {@link Customer} object to be created
     * @return new {@link Customer} object if successfully created, else null
     */
    @Override
    public Customer save(Customer newCustomer) throws IOException {

        Customer savedCustomer = null;
        synchronized (this.customers) {
            List<String> usernameList = this.customers.entrySet().stream().map(e -> e.getValue().getUsername())
                    .collect(Collectors.toList());

            boolean isCustomerPresent = false;

            for (String username : usernameList) {
                if (username.equals(newCustomer.getUsername()))
                    isCustomerPresent = true;
            }

            if (!isCustomerPresent) {
                savedCustomer = new Customer(nextId(), newCustomer.getUsername(), newCustomer.getPassword(),
                        newCustomer.getFirstName(), newCustomer.getLastName(),
                        newCustomer.getEmail(), newCustomer.getPhone(), newCustomer.getAddress());
                this.customers.put(savedCustomer.getId(), savedCustomer);
                List<Customer> allCustomers = findAll();

                objectMapper.writeValue(new File(this.filename), allCustomers.toArray(new Customer[0]));
            }

            return savedCustomer;
        }

    }

    /**
     * Gets all the {@linkplain Customer customers} from the data
     * 
     * @return An array of {@link Customer} objects
     */
    @Override
    public List<Customer> findAll() {
        synchronized (this.customers) {
            return customers.entrySet().stream().map(e -> e.getValue()).collect(Collectors.toList());
        }
    }

    /**
     * Gets customer according to id
     * 
     * @param id of {@link Customer}
     * @return {@link Customer} with the given id, else null
     */
    @Override
    public Customer findById(Integer id) {
        synchronized (this.customers) {
            if (this.customers.containsKey(id)) {
                return this.customers.get(id);
            } else
                return null;
        }
    }

    @Override
    public boolean existsById(Integer id) {
        return false;
    }

    @Override
    public void delete(Customer entity) {

    }

    @Override
    public boolean deleteById(Integer id) throws IOException {
        synchronized (this.customers) {
            if (this.customers.containsKey(id)) {
                this.customers.remove(id);
                return save();
            } else
                return false;
        }

    }

    @Override
    public Customer update(Customer entitiy) throws IOException {
        synchronized (this.customers) {
            if (this.customers.containsKey(entitiy.getId())) {
                this.customers.put(entitiy.getId(), entitiy);
                save();
                return entitiy;
            } else
                return null;
        }
    }

    /**
     * Gets customer according to the username and password
     * 
     * @param username and @param password of {@link Customer}
     * @return {@link Customer} with the given username and password, else null
     */
    @Override
    public Customer findByUsernameAndPassword(String username, String password) {
        List<Customer> customers = findAll();
        for (Customer customer : customers) {
            if (customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                return customer;
            }
        }
        return null;
    }

}
