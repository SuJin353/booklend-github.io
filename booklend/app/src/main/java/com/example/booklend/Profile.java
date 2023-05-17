package com.example.booklend;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }
    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_borrowed_book: {
                Intent intent = new Intent(Profile.this, BorrowedBook.class);
                startActivity(intent);
                break;
            }
            case R.id.tv_overdue_book: {
                Intent intent = new Intent(Profile.this, OverdueBook.class);
                startActivity(intent);
                break;
            }
            case R.id.tv_transaction_history: {
                Intent intent = new Intent(Profile.this, TransactionHistory.class);
                startActivity(intent);
                break;
            }
            case R.id.tv_buy_credit: {
                Intent intent = new Intent(Profile.this, BuyCredit.class);
                startActivity(intent);
                break;
            }
        }
        finish();
    }
}