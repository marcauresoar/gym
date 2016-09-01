package com.gymproject.app.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.gymproject.app.R;
import com.gymproject.app.utils.SessionUtils;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!SessionUtils.getInstance(getApplicationContext()).isLoggedIn()) {
            finish();
        }
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);

        TextView drawerNome = (TextView) header.findViewById(R.id.drawerNome);
        TextView drawerEmail = (TextView) header.findViewById(R.id.drawerEmail);

        drawerNome.setText(SessionUtils.getInstance(this).getNomeUsuario());
        drawerEmail.setText(SessionUtils.getInstance(this).getEmailUsuario());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_ficha) {
            Intent intent = new Intent(this, RecyclerViewDemoActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_treino) {

        } else if (id == R.id.nav_aval) {

        } else if (id == R.id.nav_sair) {
            new AlertDialog.Builder(this)
                    .setTitle("Atenção")
                    .setMessage("Você realmente deseja sair?")
                    .setIcon(R.drawable.ic_sair)
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            SessionUtils.getInstance(getApplicationContext()).logoutUser();
                            Intent intent = new Intent(getApplicationContext(), InicioActivity.class);
                            startActivity(intent);
                            finish();
                        }})
                    .setNegativeButton("Não", null).show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
