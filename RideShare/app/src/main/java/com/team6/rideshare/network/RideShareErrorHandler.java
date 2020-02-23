package com.team6.rideshare.network;

import org.androidannotations.rest.spring.api.RestErrorHandler;
import org.springframework.core.NestedRuntimeException;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

public class RideShareErrorHandler implements RestErrorHandler {
    @Override
    public void onRestClientExceptionThrown(NestedRuntimeException e) {
        e.printStackTrace();
    }
}
