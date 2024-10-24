package com.estore.api.estoreapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Customer extends BaseEntity{


    @JsonProperty("username")    
    private String username;

    @JsonProperty("password")    
    private String password;

    @JsonProperty("firstName")    
    private String firstName;

    @JsonProperty("lastName")   
    private String lastName;

    @JsonProperty("email")   
    private String email;

    @JsonProperty("phone")   
    private String phone;

    @JsonProperty("address")   
    private String address;

    private static final Logger LOG = Logger.getLogger(Customer.class.getName());

    /**
     * Create a Customer with the given username, password, name, email, phone and address
     * @param username username of the customer
     * @param password password of the customer
     * @param firstName first name of the customer
     * @param lastName last name of the customer
     * @param email email of the customer
     * @param phone phone number of the customer
     * @param address address of the customer
     */
    public Customer(@JsonProperty("id") int id, 
                    @JsonProperty("username") String username,
                    @JsonProperty("password") String password,
                    @JsonProperty("firstName")String firstName, 
                    @JsonProperty("lastName") String lastName, 
                    @JsonProperty("email") String email, 
                    @JsonProperty("phone") String phone, 
                    @JsonProperty("address")String address) {
                        
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    /**
     * Gets the id of the customer
     * @return
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * Gets the username of the customer
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * 
     * @param username is the new username of the customer
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the username of the customer
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * 
     * @param password is the new password of the customer
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the first name of the customer
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * 
     * @param firstName is the new first name of the customer
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the customer
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * 
     * @param lastName is the new last name of the customer
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the email of the customer
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * 
     * @param email is the new email of the customer
     */
    public void setEmail(String email) {
        this.email = email;
    }
    

    /**
     * Gets the phone number of the customer
     * @return
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 
     * @param phone is the new phone number of the customer
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the address of the customer
     * @return
     */
    public String getAddress() {
        return address;
    }
    
    /**
     * 
     * @param address is the new address of the customer
     */
    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return "Customer [username=" + username + ", password=" + password + "firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", phone=" + phone
                + ", address=" + address + "]";
    }


    public static Logger getLog() {
        return LOG;
    }

    
    
}
