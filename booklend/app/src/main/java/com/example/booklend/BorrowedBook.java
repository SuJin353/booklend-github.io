package com.example.booklend;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class BorrowedBook extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ImageButton ibt_back;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrowed_book);
        ibt_back = findViewById(R.id.ibt_back);
        ibt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(BorrowedBook.this, User.class);
                startActivity(intent);
                finish();
            }
        });
    }
}