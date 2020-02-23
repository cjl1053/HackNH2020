package com.team6.rideshare.data;

import android.location.Address;

public class Passenger {

    private Address mAddress;
    private Address mPollingLocation;
    private int mAmount;
    private String mName;

    public Passenger(Address address, Address pollingLocation, String name, int amount) {
        mAddress = address;
        mPollingLocation = pollingLocation;
        mAmount = amount;
        mName = name;
    }

    public Address getAddress() {
        return mAddress;
    }

    public Address getPollingLocation() {
        return mPollingLocation;
    }

    public int getAmount() {
        return mAmount;
    }

    public String getName() {
        return mName;
    }
}
