package com.example.findzy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    private TextView btnSignUpforAccount;
    private BottomNavigationView bottomNavigationView;

    EditText inputEmail,  inputPassword;
    Button signInButton;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;



    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24));
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        btnSignUpforAccount = findViewById(R.id.btn_signup_for_account);

        btnSignUpforAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            }
        });


        inputEmail = findViewById(R.id.input_username);
        inputPassword = findViewById(R.id.input_password);
        signInButton = findViewById(R.id.btn_sign_in);

        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        // login functionality
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });



    }

    private boolean validateEmail(){
        String email = inputEmail.getText().toString().trim();
        if (email.isEmpty()) {
            inputEmail.setError("Field can not be empty");
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputEmail.setError("Please enter a valid email address");
            return false;
        } else {
            inputEmail.setError(null);
            return true;
        }
    }

    private boolean validatePassword(){
        String password = inputPassword.getText().toString().trim();
        if (password.isEmpty()) {
            inputPassword.setError("Field can not be empty");
            return false;
        }
        else {
            inputPassword.setError(null);
            return true;
        }
    }

    private void performLogin() {
        if (validateEmail() && validatePassword()){

            String email = inputEmail.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();

            progressDialog.setMessage("Please Wait While Login");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(SignInActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(SignInActivity.this, "Email or Password is Incorrect", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }


}