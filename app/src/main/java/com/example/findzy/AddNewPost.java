package com.example.findzy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class AddNewPost extends AppCompatActivity {

    TextView servicePersonPhoneNo;
    TextView serviceName;
    TextView serviceDescription;
    private String selectedDistrict, selectedProvince, selectedCity, selectedCategory;
    AutoCompleteTextView provinceDropdown, districtDropdown, cityDropdown, categoryDropdown;
    private ArrayAdapter<CharSequence> provinceAdapter, districtAdapter, cityAdapter;
    DatabaseReference reference;
    FirebaseUser user;
    FirebaseDatabase rootNode;
    private String userID;
    Button btnPost;
    ProgressDialog progressDialog;
    int maxid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_post);

        servicePersonPhoneNo = findViewById(R.id.input_service_person_phone_no);
        serviceName = findViewById(R.id.input_service_name);
        serviceDescription = findViewById(R.id.input_service_description);
        progressDialog = new ProgressDialog(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24));
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        LocationSelector locationSelector = new LocationSelector();
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    List<Integer> postIdList = new ArrayList<>();
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        Integer postId = Integer.valueOf(childSnapshot.getKey());
                        postIdList.add(postId);
                    }

                    maxid = postIdList.isEmpty() ? 0 : Collections.max(postIdList);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // service category dropdown
        categoryDropdown = findViewById(R.id.input_service_category);
        String[] itemsOfServiceCategory = new String[]{"Home Maintenance", "Electronic Repairs", "Vehicle Repairs", "Education", "State Service", "IT Services"};
        ArrayAdapter<String> adapterOfCategory = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                itemsOfServiceCategory);
        categoryDropdown.setAdapter(adapterOfCategory);
        categoryDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = parent.getItemAtPosition(position).toString();
            }
        });

        // ---------------------------------------
        // service location province dropdown
        provinceDropdown = findViewById(R.id.input_service_province);
        provinceAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.array_provinces,
                R.layout.spinner_layout);
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        provinceDropdown.setAdapter(provinceAdapter);
        provinceDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                districtDropdown = findViewById(R.id.input_service_district);
                selectedProvince = parent.getItemAtPosition(position).toString();

                switch (selectedProvince) {
                    case "Western":
                        districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_western_province_districts, R.layout.spinner_layout);
                        break;
                    case "Southern":
                        districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_southern_province_districts, R.layout.spinner_layout);
                        break;
                    case "Central":
                        districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_central_province_districts, R.layout.spinner_layout);
                        break;
                    case "North Western":
                        districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_northwestern_province_districts, R.layout.spinner_layout);
                        break;

                    case "North Central":
                        districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_northcentral_province_districts, R.layout.spinner_layout);
                        break;
                    case "Northern":
                        districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_north_province_districts, R.layout.spinner_layout);
                        break;
                    case "Sabaragamuwa":
                        districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_sabaragamu_province_districts, R.layout.spinner_layout);
                        break;
                    case "Uva":
                        districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_uva_province_districts, R.layout.spinner_layout);
                        break;
                    case "Eastern":
                        districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_eastern_province_districts, R.layout.spinner_layout);
                        break;
                }
                // service location district dropdown
                districtDropdown.setAdapter(districtAdapter);
                districtDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectedDistrict = parent.getItemAtPosition(position).toString();
                        cityDropdown = findViewById(R.id.input_service_city);

                        switch (selectedDistrict) {
                            case "Colombo":
                                cityAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_colombo_cities, R.layout.spinner_layout);
                                break;
                            default:
                                cityAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.default_city, R.layout.spinner_layout);
                                break;
                        }
                        cityDropdown.setAdapter(cityAdapter);
                        cityDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                selectedCity = parent.getItemAtPosition(position).toString();
                            }
                        });
                    }
                });
            }
        });

        btnPost = findViewById(R.id.btn_post);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePost();
            }
        });
    } // end of main method

    private boolean validateServiceName(String name) {
        if (name.isEmpty()) {
            serviceName.setError("Please Fill a Name");
            return false;
        } else {
            return true;
        }
    }

    private boolean validatePhoneNo(String phoneNo) {
        if (phoneNo.isEmpty()) {
            servicePersonPhoneNo.setError("Please Fill a Phone Number");
            return false;
        } else {
            return true;
        }
    }

    private boolean validateDescription(String description) {
        if (description.isEmpty()) {
            serviceDescription.setError("Please Fill a Description");
            return false;
        } else {
            return true;
        }

    }

    private boolean validateCity(String city) {
        if (city.isEmpty()) {
            cityDropdown.setError("Please Select a City");
            return false;
        } else {
            return true;
        }
    }

    private boolean validateDistrict(String district) {
        if (district.isEmpty()) {
            districtDropdown.setError("Please Select a District");
            return false;

        } else {
            return true;
        }
    }

    private boolean validateProvince(String province) {
        if (province.isEmpty()) {
            provinceDropdown.setError("Please Select a Province");
            return false;
        } else {
            return true;
        }
    }

    private boolean validateCategory(String category) {
        if (category.isEmpty()) {
            categoryDropdown.setError("Please Select a Category");
            return false;
        } else {
            return true;
        }
    }


    private void savePost() {
        String name = serviceName.getText().toString().trim();
        String phoneNo = servicePersonPhoneNo.getText().toString().trim();
        String category = selectedCategory;
        String province = selectedProvince;
        String district = selectedDistrict;
        String city = selectedCity;
        String description = serviceDescription.getText().toString().trim();

        if (validateServiceName(name) &&
                validateCategory(category) &&
                validatePhoneNo(phoneNo) &&
                validateProvince(province) &&
                validateDistrict(district) &&
                validateCity(city) &&
                validateDescription(description)
        ) {

            String postId = String.valueOf(maxid + 1);

            HashMap<String, Object> postData = new HashMap<>();
            postData.put("serviceName", name);
            postData.put("category", category);
            postData.put("phoneNo", phoneNo);
            postData.put("province", province);
            postData.put("district", district);
            postData.put("city", city);
            postData.put("description", description);
            postData.put("postedBy", userID);

            progressDialog.setMessage("Saving data, please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            reference.child(postId).setValue(postData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    progressDialog.dismiss();
                    sendNextActivity();
                }
            });
        }
    }

    private void sendNextActivity() {
        Intent intent = new Intent(AddNewPost.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}