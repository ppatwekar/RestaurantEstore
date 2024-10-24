package com.estore.api.estoreapi.services;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estore.api.estoreapi.model.Customer;
import com.estore.api.estoreapi.persistence.DAOs.CustomerDao;

//Instead of using the Dao layer directly we need to introduce a Service Layer
@Service
public class CustomerService {

    private CustomerDao customerDao;

    private static final Logger LOG = Logger.getLogger(CustomerService.class.getName());

    @Autowired
    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Customer addCustomer(Customer newCustomer) throws IOException {
        return customerDao.save(newCustomer);
    }

    public List<Customer> findAllCustomers() {
        return customerDao.findAll();
    }

    public Customer findCustomerById(int id) {
        return customerDao.findById(id);
    }

    public Customer findCustomerByUsernamePassword(String username, String password) {
        return customerDao.findByUsernameAndPassword(username, password);
    }

    public Customer updateCustomer(Customer inputCustomer) throws IOException {
        return customerDao.update(inputCustomer);
    }

    public boolean deleteCustomerById(int id) throws IOException {
        return customerDao.deleteById(id);
    }

}
