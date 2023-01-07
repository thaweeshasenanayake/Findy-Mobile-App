package com.example.findzy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton addNewPostButton;
    BottomNavigationView bottomNavigationView;
    TextView userName;
    ProgressDialog progressDialog;
    DatabaseReference reference;

    ImageButton btnHomeMaintenance, btnElectronicRepairs, btnVehicleRepairs, btnEducation, btnStateServices, btnITServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = findViewById(R.id.lbl_hello_user);
        progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("Loading");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            reference = FirebaseDatabase.getInstance().getReference("users");
            reference.child(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.isSuccessful()){
                        DataSnapshot dataSnapshot = task.getResult();
                        String displayName = String.valueOf(dataSnapshot.child("firstName").getValue());
                        userName.setText("Hello, " + displayName);
                        progressDialog.dismiss();
                    }
                }
            });
        } else {
            progressDialog.dismiss();
            startActivity(new Intent(MainActivity.this, SignInActivity.class));
        }

        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        return true;
                    case R.id.notification:
                        startActivity(new Intent(getApplicationContext(), AddNewPost.class));
                        return true;

                    case R.id.posts:
                        startActivity(new Intent(getApplicationContext(), MyPostsActivity.class));
                        return true;
                }
                return false;
            }
        });

        addNewPostButton = findViewById(R.id.add_new_post_button);
        addNewPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddNewPost.class));
            }
        });

        btnHomeMaintenance = findViewById(R.id.btn_home_repair);
        btnElectronicRepairs = findViewById(R.id.btn_electric_repair);
        btnVehicleRepairs = findViewById(R.id.btn_vehicle_repair);
        btnEducation = findViewById(R.id.btn_education);
        btnITServices = findViewById(R.id.btn_it_services);
        btnStateServices = findViewById(R.id.btn_government);

        btnHomeMaintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = "Home Maintenance";
                Intent intent = new Intent(MainActivity.this, PostsActivity.class);
                intent.putExtra("category", category);
                startActivity(intent);
            }
        });

        btnElectronicRepairs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = "Electronic Repairs";
                Intent intent = new Intent(MainActivity.this, PostsActivity.class);
                intent.putExtra("category", category);
                startActivity(intent);
            }
        });

        btnVehicleRepairs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = "Vehicle Repairs";
                Intent intent = new Intent(MainActivity.this, PostsActivity.class);
                intent.putExtra("category", category);
                startActivity(intent);
            }
        });

        btnEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = "Education";
                Intent intent = new Intent(MainActivity.this, PostsActivity.class);
                intent.putExtra("category", category);
                startActivity(intent);
            }
        });

        btnStateServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = "State Service";
                Intent intent = new Intent(MainActivity.this, PostsActivity.class);
                intent.putExtra("category", category);
                startActivity(intent);
            }
        });

        btnITServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = "IT Services";
                Intent intent = new Intent(MainActivity.this, PostsActivity.class);
                intent.putExtra("category", category);
                startActivity(intent);
            }
        });


    }
}