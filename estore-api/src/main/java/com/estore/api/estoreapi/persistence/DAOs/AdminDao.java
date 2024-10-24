package com.estore.api.estoreapi.persistence.DAOs;

import com.estore.api.estoreapi.model.Admin;

public interface AdminDao extends DAO<Admin, Integer> {
    public Admin findByUsernameAndPassword(String username, String password);

}
