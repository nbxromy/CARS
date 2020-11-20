package org.vaadin.examples.form.data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class Customer {
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    @NotNull
    private DateTimeFormat birthdate;
    @NotNull
    private String address;
    @NotNull
    private String zip;
    @NotNull 
    private String city;
    @NotNull
    private String countrycode;
    @NotNull
    private String phonenumber;
    @NotNull
    @Email
    private String email;

    @NotNull
    private String username;
    @NotNull
    private String password;

    public Customer (String firstname, String lastname, DateTimeFormat birthdate, String address, String zip, String city, String countrycode, String phonenumber, String email, String username, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.address = address;
        this.zip = zip;
        this.city = city;
        this.countrycode = countrycode;
        this.phonenumber = phonenumber;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public DateTimeFormat getBirthdate() {
        return birthdate;
    }

    public String getAddress() {
        return address;
    }

    public String getZip() {
        return zip;
    }

    public String getCity() {
        return city;
    } 

    public String getCountrycode() {
        return countrycode;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}