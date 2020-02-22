package com.team6.rideshare.data;

import android.location.Address;

public class Driver {

    private Address mAddress;
    private Address mPollingLocation;
    private int mCapacity;

    public Driver(Address address, Address pollingLocation, int capacity) {
        mAddress = address;
        mPollingLocation = pollingLocation;
        mCapacity = capacity;
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
}
