package com.example.booklend;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class OverdueBook extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ImageButton ibt_back;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overdue_book);
        ibt_back = findViewById(R.id.ibt_back);
        ibt_back.setOnClickListener(view -> {
            Intent intent =  new Intent(OverdueBook.this, User.class);
            startActivity(intent);
            finish();
        });
    }
}