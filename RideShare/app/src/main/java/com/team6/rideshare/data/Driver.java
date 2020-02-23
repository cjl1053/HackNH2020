package com.team6.rideshare.data;

import android.location.Address;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Driver {

    private Address mAddress;
    private double longitude;
    private double latitude;
    private String pollingLocation;
    private int capacity;
    private String name;

    private int leaveTime;

    public Driver(Address address, String pollingLocation, String name, int capacity, int leaveTime) {
        mAddress = address;
        longitude = address.getLongitude();
        latitude = address.getLatitude();
        this.pollingLocation = pollingLocation;
        this.capacity = capacity;
        this.name = name;
        this.leaveTime = leaveTime;
    }

    @JsonIgnore
    public Address getAddress() {
        return mAddress;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getPollingLocation() {
        return pollingLocation;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getName() {
        return name;
    }

    public int getLeaveTime() {
        return leaveTime;
    }
}
