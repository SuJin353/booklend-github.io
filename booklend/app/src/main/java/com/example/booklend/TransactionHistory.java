package com.example.booklend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class TransactionHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ImageButton ibt_back;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        ibt_back = findViewById(R.id.ibt_back);

        ibt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(TransactionHistory.this, User.class);
                startActivity(intent);
                finish();
            }
        });
    }
}