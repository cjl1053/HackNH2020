package com.team6.rideshare.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.team6.rideshare.R;

public class DriverActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private NumberPicker passPicker;
    //private Spinner pollSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        passPicker = findViewById(R.id.pass_picker);
        passPicker.setMinValue(1);
        passPicker.setMaxValue(20);

        Spinner pollSpinner = (Spinner) findViewById(R.id.poll_loc_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.polling_locations, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pollSpinner.setAdapter(adapter);
        pollSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        String item = adapterView.getItemAtPosition(pos).toString();
        Log.i("Selected", item);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
