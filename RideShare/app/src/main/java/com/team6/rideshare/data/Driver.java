package com.team6.rideshare.data;

import android.location.Address;

public class Driver {

    private Address mAddress;
    private Address mPollingLocation;
    private int mCapacity;
    private String mName;

    public Driver(Address address, Address pollingLocation, String name, int capacity) {
        mAddress = address;
        mPollingLocation = pollingLocation;
        mCapacity = capacity;
        mName = name;
    }

    public Address getAddress() {
        return mAddress;
    }

    public Address getPollingLocation() {
        return mPollingLocation;
    }

    public int getCapacity() {
        return mCapacity;
    }

    public String getName() {
        return mName;
    }
}
