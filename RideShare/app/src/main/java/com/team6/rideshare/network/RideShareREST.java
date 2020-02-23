package com.team6.rideshare.network;

import com.team6.rideshare.data.Driver;
import com.team6.rideshare.data.Passenger;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.RestClientErrorHandling;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;

@Rest(rootUrl = "http://10.0.2.2:8000",
    converters = {StringHttpMessageConverter.class, MappingJackson2HttpMessageConverter.class})
public interface RideShareREST extends RestClientErrorHandling {

    @Get("/drivers/{name}")
    DriverRoute getDriverRoute(@Path String name);

    @Get("/passengers/{name}")
    PassengerAssignment getPassengerDriver(@Path String name);

    @Get("/login/{user}/{pass}")
    BooleanWrapper login(@Path String user, @Path String pass);

    @Get("/register/{user}/{pass}")
    BooleanWrapper register(@Path String user, @Path String pass);

    @Get("/maps/{driver}")
    String getMapURI(@Path String driver);

    @Post("/new/driver")
    BooleanWrapper registerNewDriver(@Body Driver driver);

    @Post("/new/passenger")
    BooleanWrapper registerNewPassenger(@Body Passenger passenger);

}
