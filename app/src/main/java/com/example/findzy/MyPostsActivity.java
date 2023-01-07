package com.example.findzy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyPostsActivity extends AppCompatActivity {

    FloatingActionButton addNewPostButton;
    BottomNavigationView bottomNavigationView;

    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    List<PostModelClass> myPosts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);

        recyclerView = findViewById(R.id.my_posts_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyPostsActivity.this));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24));
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setSelectedItemId(R.id.posts);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        return true;
                    case R.id.notification:
                        startActivity(new Intent(getApplicationContext(), AddNewPost.class));
                        return true;
                    case R.id.posts:
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


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        Query userPostsQuery = databaseReference.child("posts").orderByChild("postedBy").equalTo(userId);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait while your data loads....");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        userPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                myPosts.clear();
                for (DataSnapshot posts : snapshot.getChildren()){
                    String getServiceName = posts.child("serviceName").getValue(String.class);
                    String getServiceCategory = posts.child("category").getValue(String.class);
                    String getServiceCity = posts.child("city").getValue(String.class);

                    PostModelClass post = new PostModelClass(getServiceName, getServiceCity, getServiceCategory);

                    myPosts.add(post);
                }

                recyclerView.setAdapter(new MyPostsAdapter(myPosts, MyPostsActivity.this));
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}