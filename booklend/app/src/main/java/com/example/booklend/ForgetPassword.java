package com.example.booklend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {
    EditText et_email;
    TextView tv_change_password;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_forget_password);
        Mapping();
        tv_change_password.setOnClickListener(view -> firebaseAuth.sendPasswordResetEmail(et_email.getText().toString()).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Toast.makeText(ForgetPassword.this, "Reset password has been sent to your email", Toast.LENGTH_SHORT).show();

            }
            else {
                Toast.makeText(ForgetPassword.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }));
    }
    public void onClick(View view){
        if (view.getId() == R.id.ibt_back){
            Intent intent = new Intent(ForgetPassword.this, Login.class);
            startActivity(intent);
        }
        finish();
    }
    void Mapping(){
        et_email = findViewById(R.id.et_email);
        tv_change_password = findViewById(R.id.tv_change_password);
        firebaseAuth = FirebaseAuth.getInstance();
    }
}