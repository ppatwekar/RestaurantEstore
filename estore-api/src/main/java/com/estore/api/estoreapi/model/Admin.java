package com.estore.api.estoreapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Admin extends BaseEntity{


    @JsonProperty("username")    
    private String username;

    @JsonProperty("password")    
    private String password;

    private static final Logger LOG = Logger.getLogger(Admin.class.getName());

    /**
     * Create a Admin with the given username and password
     * @param username username of the admin
     * @param password password of the admin
     */
    public Admin(@JsonProperty("id") int id, 
                    @JsonProperty("username") String username,
                    @JsonProperty("password") String password) {
                        
        this.id = id;
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the id of the admin
     * @return
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * Gets the username of the admin
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * 
     * @param username is the new username of the admin
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the username of the admin
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * 
     * @param password is the new password of the admin
     */
    public void setPassword(String password) {
        this.password = password;
    }

    

    @Override
    public String toString() {
        return "Admin [username=" + username + ", password=" + password + "]";
    }


    public static Logger getLog() {
        return LOG;
    }

    
    
}
