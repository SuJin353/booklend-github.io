package com.example.booklend;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class OverdueBook extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ImageButton ibt_back;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overdue_book);
        ibt_back = findViewById(R.id.ibt_back);
        ibt_back.setOnClickListener(view -> {
            finish();
        });
    }
}