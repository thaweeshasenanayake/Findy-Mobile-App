package com.example.findzy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    EditText inputFirstName, inputLastName, inputEmail, inputPhoneNumber, inputPassword, inputConfirmPassword;
    Button signUpButton;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    FirebaseDatabase rootNode;
    DatabaseReference reference;


    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{4,}" +                // at least 4 characters
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24));
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        // declare elements
        inputFirstName = findViewById(R.id.input_firstname);
        inputLastName = findViewById(R.id.input_lastname);
        inputEmail = findViewById(R.id.input_email);
        inputPhoneNumber = findViewById(R.id.input_phone_no);
        inputPassword = findViewById(R.id.input_password);
        inputConfirmPassword = findViewById(R.id.input_confirm_password);
        signUpButton = findViewById(R.id.btn_sign_up);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");




        // button onClick action
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PerForAuth();
            }
        });
    }

    private boolean validateFirstName(){
        String firstName = inputFirstName.getText().toString().trim();
        if (firstName.isEmpty()) {
            inputFirstName.setError("Field can not be empty");
            return false;
        }
        else if (!firstName.matches("^[a-zA-Z]+$")) {
            inputFirstName.setError("Name should be only letters");
            return false;
        } else {
            inputFirstName.setError(null);
            return true;
        }
    }

    private boolean validaLastName(){
        String lastName = inputLastName.getText().toString().trim();
        if (lastName.isEmpty()) {
            inputLastName.setError("Field can not be empty");
            return false;
        }
        else if (!lastName.matches("^[a-zA-Z]+$")) {
            inputLastName.setError("Name should be only letters");
            return false;
        } else {
            inputLastName.setError(null);
            return true;
        }
    }

    private boolean validatePhoneNumber(){
        String phoneNumber = inputPhoneNumber.getText().toString().trim();
        if (phoneNumber.isEmpty()) {
            inputPhoneNumber.setError("Field can not be empty");
            return false;
        }
        else {
            if (!Patterns.PHONE.matcher(phoneNumber).matches()) {
                inputPhoneNumber.setError("Please enter a valid Mobile Number");
                return false;
            } else {
                inputPhoneNumber.setError(null);
                return true;
            }
        }

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
        else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            inputPassword.setError("Password is too weak");
            return false;
        } else {
            inputPassword.setError(null);
            return true;
        }
    }

    private boolean validateConfirmPassword(){
        String confirmPassword = inputConfirmPassword.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        if(!confirmPassword.equals(password)){
            inputConfirmPassword.setError("Passwords are not matching");
            return false;
        }
        else{
            inputConfirmPassword.setError(null);
            return true;
        }
    }

    private void PerForAuth() {

        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        String firstName = inputFirstName.getText().toString().trim();
        String lastName = inputLastName.getText().toString().trim();
        String phoneNumber = inputPhoneNumber.getText().toString().trim();


        if(validateFirstName() && validateEmail() && validatePassword() && validateConfirmPassword() && validaLastName() && validatePhoneNumber()) {
            progressDialog.setMessage("Please Wait While Registration");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        String userID = user.getUid();
                        reference.child(userID).child("email").setValue(email);
                        reference.child(userID).child("firstName").setValue(firstName);
                        reference.child(userID).child("lastName").setValue(lastName);
                        reference.child(userID).child("phoneNumber").setValue(phoneNumber);
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(SignUpActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(SignUpActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }
    }
    private void sendUserToNextActivity() {
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}