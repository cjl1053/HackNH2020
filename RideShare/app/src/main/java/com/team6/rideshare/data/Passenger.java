package com.team6.rideshare.data;

import android.location.Address;

public class Passenger {

    private Address mAddress;
    private Address mPollingLocation;
    private int mAmount;

    public Passenger(Address address, Address pollingLocation, int amount) {
        mAddress = address;
        mPollingLocation = pollingLocation;
        mAmount = amount;
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
}
