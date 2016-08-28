package com.gymproject.app.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gymproject.app.R;
import com.gymproject.app.utils.SessionUtils;

public class DashboardActivity extends AppCompatActivity {

    Button btnSair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!SessionUtils.getInstance(getApplicationContext()).isLoggedIn()) {
            finish();
        }
        setContentView(R.layout.activity_dashboard);

        btnSair = (Button) findViewById(R.id.btnSair);
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionUtils.getInstance(getApplicationContext()).logoutUser();
                Intent intent = new Intent(getApplicationContext(), InicioActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
