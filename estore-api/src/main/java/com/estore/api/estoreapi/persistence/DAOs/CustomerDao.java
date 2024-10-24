package com.estore.api.estoreapi.persistence.DAOs;

import com.estore.api.estoreapi.model.Customer;

public interface CustomerDao extends DAO<Customer, Integer> {
    public Customer findByUsernameAndPassword(String username, String password);

}
