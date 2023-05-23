package com.example.booklend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccount extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText et_username, et_full_name, et_email, et_phone_number, et_password, et_confirm_password;
    private TextView tv_create;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Mapping();

        tv_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = et_username.getText().toString().trim();
                String fullname = et_full_name.getText().toString().trim();
                String email = et_email.getText().toString().trim();
                String phone_number = et_phone_number.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                String confirm_password = et_confirm_password.getText().toString().trim();

                if (username.isEmpty() || fullname.isEmpty() || email.isEmpty() || password.isEmpty() || phone_number.isEmpty())
                {
                    Toast.makeText(CreateAccount.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
                else if (!password.equals(confirm_password))
                {
                    et_confirm_password.setError("Password are not matching");
                }
                else
                {
                    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                String uid = auth.getCurrentUser().getUid();
                                databaseReference.child(uid).child("username").setValue(username);
                                databaseReference.child(uid).child("fullname").setValue(fullname);
                                databaseReference.child(uid).child("email").setValue(email);
                                databaseReference.child(uid).child("password").setValue(password);
                                databaseReference.child(uid).child("phone_number").setValue(phone_number);
                                Toast.makeText(CreateAccount.this,"Sign up successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CreateAccount.this, Login.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(CreateAccount.this,"Sign up failed " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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
        et_full_name = (EditText) findViewById(R.id.et_fullname);
        et_email = (EditText) findViewById(R.id.et_email);
        et_phone_number = (EditText) findViewById(R.id.et_phone_number);
        et_password = (EditText) findViewById(R.id.et_password);
        et_confirm_password = (EditText) findViewById(R.id.et_confirm_password);
        tv_create = (TextView) findViewById(R.id.tv_create);
    }
}