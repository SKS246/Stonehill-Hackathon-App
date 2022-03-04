package com.test.jobapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private RelativeLayout Electrician, Carpenter, Plumber, Mechanic, Driver, Cook, HouseHelp;

    private TextView navhead_email;

    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.homeToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("JobApp");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        NavigationView navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navhead_email = navigationView.getHeaderView(0).findViewById(R.id.nav_header_email);
        userRef = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                navhead_email.setText(snapshot.child("username").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Home2Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Electrician = findViewById(R.id.Electrician);
        Carpenter = findViewById(R.id.Carpenter);
        Mechanic = findViewById(R.id.Mechanic);
        Plumber = findViewById(R.id.Plumber);
        Cook = findViewById(R.id.Cook);
        HouseHelp = findViewById(R.id.HouseHelp);
        Driver = findViewById(R.id.Driver);

        Electrician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home2Activity.this, RecyclerActivity.class);
                intent.putExtra("prof", "Electrician");
                startActivity(intent);
                finish();
            }
        });

        Carpenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home2Activity.this, RecyclerActivity.class);
                intent.putExtra("prof", "Carpenter");
                startActivity(intent);
                finish();
            }
        });

        Plumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home2Activity.this, RecyclerActivity.class);
                intent.putExtra("prof", "Plumber");
                startActivity(intent);
                finish();
            }
        });

        Driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home2Activity.this, RecyclerActivity.class);
                intent.putExtra("prof", "Driver");
                startActivity(intent);
                finish();
            }
        });

        Mechanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home2Activity.this, RecyclerActivity.class);
                intent.putExtra("prof", "Mechanic");
                startActivity(intent);
                finish();
            }
        });

        Cook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home2Activity.this, RecyclerActivity.class);
                intent.putExtra("prof", "Cook");
                startActivity(intent);
                finish();
            }
        });

        HouseHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home2Activity.this, RecyclerActivity.class);
                intent.putExtra("prof", "House-Help");
                startActivity(intent);
                finish();
            }
        });
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
//            case R.id.nav_search:
//                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
//                startActivity(intent);
//                break;

            case R.id.nav_post:
                Intent intent2 = new Intent(Home2Activity.this, JobProfActivity.class);
                startActivity(intent2);
                break;

            case R.id.nav_account:
                Intent intent3 = new Intent(Home2Activity.this, HomeActivity.class);
                startActivity(intent3);
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
}