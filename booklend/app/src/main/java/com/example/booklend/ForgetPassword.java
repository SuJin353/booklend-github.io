package com.example.booklend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class ForgetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
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