package com.gymproject.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gymproject.app.utils.SessionUtils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(SessionUtils.getInstance(getApplicationContext()).isLoggedIn()) {
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, InicioActivity.class);
            startActivity(intent);
            finish();
        }

    }
}