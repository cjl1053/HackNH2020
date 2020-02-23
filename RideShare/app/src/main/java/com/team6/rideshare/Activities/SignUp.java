package com.team6.rideshare.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.team6.rideshare.R;
import com.team6.rideshare.network.RideShareREST;
import com.team6.rideshare.util.CurrentLogin;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.rest.spring.annotations.RestService;

@EActivity
public class SignUp extends AppCompatActivity {

    @RestService
    RideShareREST rideShareREST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    private void showToast(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }

    @Background
    @Click(R.id.signup_button)
    public void onSignUp() {
        EditText editName = findViewById(R.id.name);
        EditText editPass = findViewById(R.id.password);
        String name = editName.getText().toString();
        String password = editPass.getText().toString();
        final Activity act = this;
        if(name.equals("")){
            act.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showToast(act,"Please enter a username!" );
                }
            });
            return;
        }
        if(password.equals("")){
            act.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showToast(act,"Please enter a password!" );
                }
            });
            return;
        }
        boolean success = rideShareREST.register(name, password).result;
        if(success){
            CurrentLogin.getInstance().setUsername(name);
            startActivity(new Intent( this, SignUpSuccess_.class));
        } else {
            act.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showToast(act,"Unable to create account!" );
                }
            });
            return;
        }
    }


}
