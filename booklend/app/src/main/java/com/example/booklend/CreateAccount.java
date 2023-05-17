package com.example.booklend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateAccount extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText et_username, et_email, et_password, et_confirm_password;
    private Button bt_create;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Mapping();

        bt_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = et_username.getText().toString().trim();
                String email = et_email.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                String confirm_password = et_confirm_password.getText().toString().trim();

                if (username.isEmpty())
                {
                    et_username.setError("User name can't be empty");
                }
                if (email.isEmpty())
                {
                    et_email.setError("Email can't be empty");
                }
                if (password.isEmpty())
                {
                    et_password.setError("Password can't be empty");
                }
                if (confirm_password.isEmpty() || confirm_password != password)
                {
                    et_username.setError("Please input same password");
                }
                else {
                    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(CreateAccount.this,"Sign up successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CreateAccount.this, Login.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(CreateAccount.this,"Sign up failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
    void Mapping()
    {
        auth = FirebaseAuth.getInstance();
        et_username = (EditText) findViewById(R.id.et_username);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        et_confirm_password = (EditText) findViewById(R.id.et_confirm_password);
        bt_create = (Button) findViewById(R.id.bt_create);
    }
}