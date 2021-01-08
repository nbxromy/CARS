package com.example;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private String orderID;
    private String carName;
    private String licencePlate;
    private String username;
    private String location;
    private LocalDate pickupDate;
    private LocalTime pickupTime;
    private LocalDate dropoffDate;
    private LocalTime dropoffTime;
    private String price;
    private String isPaid;

    private String gps;
    private String insurance;
    private String winterTires;
    private String extraDriver;
    private String childSeat;

    public Reservation(String orderID, String carName, String licencePlate, String username, String location, LocalDate pickupDate, LocalTime pickupTime, LocalDate dropoffDate, LocalTime dropoffTime, String price, String isPaid, String gps, String insurance, String winterTires, String extraDriver, String childSeat) {
        this.orderID = orderID;
        this.carName = carName;
        this.licencePlate = licencePlate;
        this.username = username;
        this.location = location;
        this.pickupDate = pickupDate;
        this.pickupTime = pickupTime;
        this.dropoffDate = dropoffDate;
        this.dropoffTime = dropoffTime;
        this.price = price;
        this.isPaid = isPaid;
        this.gps = gps;
        this.insurance = insurance;
        this.winterTires = winterTires;
        this.extraDriver = extraDriver;
        this.childSeat = childSeat;
    }

    public String getOrderID() {
        return this.orderID;
    }
    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getCarName() {
        return this.carName;
    }
    public void setCarName(String carName) {
        this.carName = carName;
    }
    
    public String getLicencePlate() {
        return this.licencePlate;
    }
    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public String getUsername() { 
        return this.username;
    } 
    public void setUsername(String username) {
        this.username = username;
    }

    public String getLocation() {
        return this.location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getPickupDate() {
        return this.pickupDate;
    }
    public void setPickupDate(LocalDate pickupDate) {
        this.pickupDate = pickupDate;
    }

    public LocalTime getPickupTime() {
        return this.pickupTime;
    }
    public void setPickupTime(LocalTime pickupTime) {
        this.pickupTime = pickupTime;
    }

    public LocalDate getDropoffDate() {
        return this.dropoffDate;
    }
    public void setDropoffDate(LocalDate dropoffDate) {
        this.dropoffDate = dropoffDate;
    }

    public LocalTime getDropoffTime() {
        return this.dropoffTime;
    }
    public void setDropoffTime(LocalTime dropoffTime) {
        this.dropoffTime = dropoffTime;
    }

    public String getPrice() {
        return this.price;
    } 
    public void setPrice(String price) {
        this.price = price;
    }

    public String getIsPaid() {
        return this.isPaid;
    }
    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
    }

    public String getGPS() {
        return this.gps;
    } 
    public void setGPS(String gps) {
        this.gps = gps;
    }

    public String getInsurance() {
        return this.insurance;
    }
    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public String getWinterTires() {
        return this.winterTires;
    }
    public void setWinterTires(String winterTires) {
        this.winterTires = winterTires;
    }

    public String getExtraDriver() {
        return this.extraDriver;
    }
    public void setExtraDriver(String extraDriver) {
        this.extraDriver = extraDriver;
    }

    public String getChildSeat() {
        return this.childSeat;
    }
    public void setChildSeat(String childSeat) {
        this.childSeat = childSeat;
    }
}
