package com.example.test;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText Email,Password;
    private TextView registration;
    private Button login;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private TextView ForgetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Email=findViewById(R.id.ptemail);
        Password=findViewById(R.id.ptpassword);
        registration=findViewById(R.id.tvregistration);
        login=findViewById(R.id.btlogin);
        ForgetPassword =findViewById(R.id.tvforgetpassword);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        ForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ForgetActivity.class));
            }
        });

        if(user != null)
        {
            finish();
            startActivity(new Intent(MainActivity.this,Second.class));
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Email.getText().toString(),Password.getText().toString());
            }
        });
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Registration.class));
            }
        });

    }
    private void validate(String userName,String userPassword)
    {
        if(userName.equals("")|| userPassword.equals(""))
        {
            Toast.makeText(this, "Please Enter Email Id And Password", Toast.LENGTH_SHORT).show();
        }
        else
        {
            progressDialog.setMessage("Please Wait!!");
            progressDialog.show();
            firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        // Toast.makeText(MainActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                        // startActivity(new Intent(MainActivity.this,Second.class));
                        checkEmailVerification();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
    private void checkEmailVerification()
    {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Boolean emailFlag = firebaseUser.isEmailVerified();
        if(emailFlag)
        {
            startActivity(new Intent(MainActivity.this,Second.class));
        }
        else
        {
            finish();
            Toast.makeText(this, "Please Verify Your Email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }

    }

}
