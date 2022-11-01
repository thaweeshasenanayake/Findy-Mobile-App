package com.example.findzy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

public class AddNewPost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_post);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24));
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        // service category dropdown
        String[] itemsOfServiceCategory = new String[]{"Home Maintenance", "Electronic Repairs", "Vehicle Repairs", "Education", "State Service", "IT Services"};
        ArrayAdapter<String> adapterOfCategory = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                itemsOfServiceCategory);

        AutoCompleteTextView service_category_dropdown = findViewById(R.id.input_service_category);
        service_category_dropdown.setAdapter(adapterOfCategory);
        service_category_dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // do in what to do with dropdown
            }
        });

        // service location province dropdown

        String[] itemsOfProvince = new String[]{"Southern", "Western", "Northern", "Uva", "Central"};
        ArrayAdapter<String> adapterOfProvince = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                itemsOfProvince);

        AutoCompleteTextView service_location_province = findViewById(R.id.input_service_province);
        service_location_province.setAdapter(adapterOfProvince);
        service_location_province.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // do in what to do with dropdown
            }
        });

        // service location district dropdown
        String[] itemsOfDistrict = new String[]{"Southern", "Western", "Northern", "Uva", "Central"};
        ArrayAdapter<String> adapterOfDistrict = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                itemsOfDistrict);

        AutoCompleteTextView service_location_district = findViewById(R.id.input_service_district);
        service_location_district.setAdapter(adapterOfDistrict);
        service_location_district.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // do in what to do with dropdown
            }
        });

        // service location city dropdown
        String[] itemsOfCity = new String[]{"Southern", "Western", "Northern", "Uva", "Central"};
        ArrayAdapter<String> adapterOfCity = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                itemsOfCity);

        AutoCompleteTextView service_location_city = findViewById(R.id.input_service_city);
        service_location_city.setAdapter(adapterOfCity);
        service_location_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // do in what to do with dropdown
            }
        });

    }
}