package com.team6.rideshare.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.team6.rideshare.R;
import com.team6.rideshare.network.RideShareREST;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.rest.spring.annotations.RestService;

@EActivity
public class SignUpSuccess extends AppCompatActivity {

    @RestService
    RideShareREST rideShareREST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_succ);
    }

    /**
     * handles the on Click of the driver.
     * @param view View
     */
    public void pressDriver(View view) {
        startActivity(new Intent(this, DriverActivity_.class));
    }

    /**
     * handles the on click of the passenger.
     * @param view View
     */
    public void pressPass(View view) {
        startActivity(new Intent(this, PassengerActivity_.class));
    }

}
