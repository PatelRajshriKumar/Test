package com.example.test;

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
import com.google.firebase.auth.FirebaseAuth;

public class ForgetActivity extends AppCompatActivity {

    private EditText ForgetEmail;
    private Button ForgetResetPassword;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        ForgetEmail = findViewById(R.id.forgetemail);
        ForgetResetPassword = findViewById(R.id.forgetresetpassword);
        firebaseAuth = firebaseAuth.getInstance();
        ForgetResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userforgetemail = ForgetEmail.getText().toString().trim();
                if(userforgetemail.equals(""))
                {
                    Toast.makeText(ForgetActivity.this, "Please Enter Email Address", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    firebaseAuth.sendPasswordResetEmail(userforgetemail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful())
                            {
                                Toast.makeText(ForgetActivity.this, "Check your email to reset your Password", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(ForgetActivity.this,MainActivity.class));
                            }
                            else
                            {
                                Toast.makeText(ForgetActivity.this, "Error in reset link Sending", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
