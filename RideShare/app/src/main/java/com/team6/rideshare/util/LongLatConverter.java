package com.team6.rideshare.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Pair;

import java.io.IOException;
import java.util.List;

public class LongLatConverter {

    private Geocoder mGeocoder;

    public LongLatConverter(Context context) {
        mGeocoder = new Geocoder(context);
    }

    public Address getCoordinates(String address) {
        List<Address> addresses;
        try {
            addresses = mGeocoder.getFromLocationName(address, 10);
            if(addresses == null || addresses.size()==0) {
                return null;
            }
            // For now, just return the first result.
            return addresses.get(0);
        } catch(IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
