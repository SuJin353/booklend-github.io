package com.example.booklend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Login extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText et_email, et_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Mapping();
    }
    @SuppressLint("NonConstantResourceId")
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.bt_sign_in:
            {
                String email = et_email.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    if (!password.isEmpty())
                    {
                        auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login.this, Menu.class);
                                startActivity(intent);
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login.this, "Login failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else {
                        et_password.setError("Password can't be empty");
                    }
                }
                else if (email.isEmpty())
                {
                    et_password.setError("Email can't be empty");
                }
                else
                {
                    et_password.setError("Invalid email");
                }
                break;
            }
            case R.id.bt_create_account: {
                Intent intent = new Intent(Login.this, CreateAccount.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.bt_forget_password: {
                Intent intent = new Intent(Login.this, ForgetPassword.class);
                startActivity(intent);
                finish();
                break;
            }
        }
    }
    void Mapping()
    {
        auth = FirebaseAuth.getInstance();
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText)  findViewById(R.id.et_password);
    }
}