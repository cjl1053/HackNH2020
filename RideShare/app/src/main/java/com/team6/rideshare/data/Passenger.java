package com.team6.rideshare.data;

import android.location.Address;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Passenger {

    private Address address;
    private Address pollingLocation;
    private int amount;
    private String name;
    private int startTime;
    private int endTime;

    public Passenger(Address address, Address pollingLocation, String name, int amount,
                     int startTime, int endTime) {
        this.address = address;
        this.pollingLocation = pollingLocation;
        this.amount = amount;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @JsonIgnore
    public Address getAddress() {
        return address;
    }

    public Address getPollingLocation() {
        return pollingLocation;
    }

    public int getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }
}
