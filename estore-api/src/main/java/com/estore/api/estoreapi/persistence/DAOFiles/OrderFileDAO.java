package com.estore.api.estoreapi.persistence.DAOFiles;

import java.util.logging.Logger;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.FoodDish;
import com.estore.api.estoreapi.model.Order;
import com.estore.api.estoreapi.persistence.DAOs.OrderDAO;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implements the functionality for JSON file-based peristance for Orders
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of
 * this
 * class and injects the instance into other classes as needed
 * 
 */
@Component
public class OrderFileDAO implements OrderDAO {
    private static final Logger LOG = Logger.getLogger(OrderFileDAO.class.getName());

    Map<Integer, Order> orders;

    private ObjectMapper objectMapper;
    private static int nextId;
    private String filename;

    @Autowired
    private ShoppingCartFileDAO shoppingCartFileDAO;

    @Autowired
    private FoodDishFileDAO foodDishFileDAO;

    /**
     * Creates a Order File Data Access Object
     * 
     * @param filename     Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization
     *                     and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public OrderFileDAO(@Value("${order.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    /**
     * Loads {@linkplain Order orders} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        this.orders = new TreeMap<>();
        OrderFileDAO.nextId = 0;

        Order[] orderArray = objectMapper.readValue(new File(filename), Order[].class);

        for (Order order : orderArray) {
            this.orders.put(order.getId(), order);
            if (order.getId() > OrderFileDAO.nextId) {
                OrderFileDAO.nextId = order.getId();
            }
        }

        ++OrderFileDAO.nextId;
        return true;
    }

    /**
     * Generates the next id for a new {@linkplain Order order}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = OrderFileDAO.nextId;
        ++OrderFileDAO.nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain Order order} from the tree map
     * 
     * @return The array of {@link Order order}, may be empty
     */
    private Order[] getOrderArray() {
        ArrayList<Order> list = new ArrayList<>();
        for (Order order : this.orders.values()) {
            list.add(order);
        }

        Order[] arr = new Order[list.size()];
        list.toArray(arr);
        return arr;
    }

    /**
     * Generates an array of {@linkplain Order order} from the tree map for any
     * {@linkplain Order order} that belong to a specific customer
     * <br>
     * 
     * @return The array of {@link Order order}, may be empty
     */
    private Order[] getOrderArrayByCustomerId(int customerId) {
        ArrayList<Order> list = new ArrayList<>();
        for (Order order : this.orders.values()) {
            if (order.getCustomerId() == customerId) {
                list.add(order);
            }
        }

        Order[] arr = new Order[list.size()];
        list.toArray(arr);
        return arr;
    }

    /**
     * Saves the {@linkplain Order order} from the map into the file as an array of
     * JSON objects
     * 
     * @return true if the {@link Order order} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Order[] arr = getOrderArray();

        objectMapper.writeValue(new File(this.filename), arr);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Order findById(int id) {
        synchronized (this.orders) {
            if (this.orders.containsKey(id)) {
                return this.orders.get(id);
            } else
                return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Order[] findByCustomerId(int customerId) {
        synchronized (this.orders) {
            return getOrderArrayByCustomerId(customerId);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Order createOrder(int customerId, FoodDish[] orderDishes) throws IOException {
        // TO-DO
        synchronized (this.orders) {

            Order newOrder;
            newOrder = new Order(nextId(), customerId, orderDishes);

            orders.put(newOrder.getId(), newOrder);
            save();
            return newOrder;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteById(int id) throws IOException {
        synchronized (this.orders) {
            if (this.orders.containsKey(id)) {
                this.orders.remove(id);
                return save();
            } else
                return false;
        }
    }

}
