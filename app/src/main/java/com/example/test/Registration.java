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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registration extends AppCompatActivity {

    private EditText username,useremail,userpassword;
    private Button submit;
    private TextView login;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        username=findViewById(R.id.pt1reg);
        useremail=findViewById(R.id.pt2reg);
        userpassword=findViewById(R.id.editText3);
        submit=findViewById(R.id.bt1reg);
        login=findViewById(R.id.tv1reg);
        firebaseAuth = FirebaseAuth.getInstance();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate())
                {
                    String user_email = useremail.getText().toString().trim();
                    String user_password = userpassword.getText().toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())
                            {
                                //Toast.makeText(Registration.this, "Your Registration Is Successfully", Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(Registration.this,Second.class));
                                sendEmailVerification();
                            }
                            else
                            {
                                Toast.makeText(Registration.this, "Your registration Is Not Successfully", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    //Upload The Data The Data Base
                }

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Registration.this,MainActivity.class));
            }
        });
    }

    private Boolean validate()
    {
        Boolean ans = false;

        String namereg =username.getText().toString();
        String emailreg=useremail.getText().toString();
        String passwordreg=userpassword.getText().toString();
        if(namereg.isEmpty() || emailreg.isEmpty() || passwordreg.isEmpty())
        {
            Toast.makeText(this, "Please Fill All The Details", Toast.LENGTH_SHORT).show();
        }
        else
        {
            ans = true;
        }
        return ans;

    }
    private void sendEmailVerification()
    {
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser != null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(Registration.this, "Registration successfully , check your email to verify", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(Registration.this,MainActivity.class));
                    }
                    else
                    {
                        Toast.makeText(Registration.this, "Verification Email not send", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
