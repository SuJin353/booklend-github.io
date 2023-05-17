package com.example.booklend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ForgetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
    }
    public void onClick(View view){
        if (view.getId() == R.id.ibt_back){
            Intent intent = new Intent(ForgetPassword.this, Login.class);
            startActivity(intent);
        }
        finish();
    }
}