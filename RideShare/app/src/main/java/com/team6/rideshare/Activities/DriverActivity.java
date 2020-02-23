package com.team6.rideshare.Activities;

import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.team6.rideshare.R;
import com.team6.rideshare.data.Driver;
import com.team6.rideshare.network.RideShareREST;
import com.team6.rideshare.util.LongLatConverter;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

@EActivity
public class DriverActivity extends AppCompatActivity {

    @RestService
    RideShareREST rideShareREST;

    public LongLatConverter longLatConverter;
    public int timePos;

    private String pollLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        longLatConverter = new LongLatConverter(this.getApplicationContext());


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

        Spinner timeSpinner = (Spinner) findViewById(R.id.time_spinner);
        ArrayAdapter<CharSequence> time_adapter = ArrayAdapter.createFromResource(this,
                R.array.driver_leave_times, android.R.layout.simple_spinner_item);
        time_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(time_adapter);
        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                String item = adapterView.getItemAtPosition(pos).toString();
                //Log.i("Time", item);
                timePos = pos;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Background
    @Click(R.id.submit_button)
    public void handleSubmit(){
        EditText editName = findViewById(R.id.edit_text_driver_name);
        EditText editPass = findViewById(R.id.num_passengers);
        EditText editLoc = findViewById(R.id.leave_loc_input);
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
        int numPass = Integer.parseInt(editPass.getText().toString());

        rideShareREST.registerNewDriver(new Driver(leaveAddress, pollLocation, name, numPass, timePos));
    }



}
