package com.team6.rideshare.network;

import java.util.List;

public class DriverRoute {

    public class RouteStop {
        public String name;
        public double longitude;
        public double latitude;
        public int order;
    }

    public List<RouteStop> passengers;

}
