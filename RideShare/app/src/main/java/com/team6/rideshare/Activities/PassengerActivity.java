package com.team6.rideshare.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.team6.rideshare.R;
import com.team6.rideshare.data.Passenger;
import com.team6.rideshare.network.RideShareREST;
import com.team6.rideshare.util.LongLatConverter;


import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.rest.spring.annotations.RestService;

@EActivity
public class PassengerActivity extends AppCompatActivity {

    @RestService
    RideShareREST rideShareREST;

    private LongLatConverter longLatConverter;
    private String pollLocation;
    private int timeStart;
    private int timeEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger);

        Spinner pollSpinner = (Spinner) findViewById(R.id.poll_loc_spinner);
        ArrayAdapter<CharSequence> poll_adapter = ArrayAdapter.createFromResource(this,
                R.array.polling_locations, android.R.layout.simple_spinner_item);
        poll_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pollSpinner.setAdapter(poll_adapter);
        pollSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                String item = adapterView.getItemAtPosition(pos).toString();
                pollLocation = item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final Spinner timeStartSpinner = (Spinner) findViewById(R.id.time_spinner_start);
        ArrayAdapter<CharSequence> time_adapter = ArrayAdapter.createFromResource(this,
                R.array.driver_leave_times, android.R.layout.simple_spinner_item);
        time_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeStartSpinner.setAdapter(time_adapter);
        timeStartSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                String item = adapterView.getItemAtPosition(pos).toString();
                timeStart = pos;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Spinner timeEndSpinner = (Spinner) findViewById(R.id.time_spinner_end);
        ArrayAdapter<CharSequence> time_adapter2 = ArrayAdapter.createFromResource(this,
                R.array.driver_leave_times, android.R.layout.simple_spinner_item);
        time_adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeEndSpinner.setAdapter(time_adapter2);
        timeEndSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                String item = adapterView.getItemAtPosition(pos).toString();
                timeEnd = pos;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Background
    @Click(R.id.submit_button)
    public void handleSubmit(){
        EditText editName = (EditText) findViewById(R.id.passenger_name);
        EditText editPass = (EditText) findViewById(R.id.num_passengers);
        EditText editLoc = (EditText) findViewById(R.id.pick_up_loc_input);
        String name = editName.getText().toString();
        String passString = editPass.getText().toString();
        String loc = editLoc.getText().toString();
        Address leaveAddress = longLatConverter.getCoordinates(loc);
        if(leaveAddress == null) {
            Toast.makeText(this, "Address not found", Toast.LENGTH_SHORT).show();
            return;
        }
        if(passString.equals("")) {
            Toast.makeText(this, "Please enter a number of passengers!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(name.equals("")) {
            Toast.makeText(this, "Please enter your name!", Toast.LENGTH_SHORT).show();
            return;
        }
        int numPass = Integer.parseInt(editPass.getText().toString());
        rideShareREST.registerNewPassenger(new Passenger(leaveAddress, pollLocation, name, numPass, timeStart, timeEnd));

    }
}
