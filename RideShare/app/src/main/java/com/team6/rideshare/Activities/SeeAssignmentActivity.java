package com.team6.rideshare.Activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.team6.rideshare.R;
import com.team6.rideshare.network.RideShareREST;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.rest.spring.annotations.RestService;

@EActivity
public class SeeAssignmentActivity extends AppCompatActivity {

    @RestService
    RideShareREST rideShareREST;

    private boolean isDriver=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_assignment);
    }

    public void radioChecked(View view){
        switch (view.getId()){
            case R.id.driver_radio:
                isDriver = true;
            case R.id.passenger_radio:
                isDriver = false;
        }

    }

    private void showToast(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }

    @Background
    @Click(R.id.see_assignment)
    public void handleClick(){
        EditText editName = (EditText) findViewById(R.id.name);
        String name = editName.getText().toString();
        final Activity act = this;
        if(name.equals("")) {
            act.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showToast(act,"Please enter your name!" );
                }
            });
            return;
        }
        Log.i("Driver", isDriver+"");
    }
}
