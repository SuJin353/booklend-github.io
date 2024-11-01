package com.example.booklend;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class User_Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_user);
        BottomNavigation();
    }
    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_personal_info: {
                Intent intent = new Intent(User_Home.this, UserInfo.class);
                startActivity(intent);
                break;
            }
            case R.id.tv_borrowed_book: {
                Intent intent = new Intent(User_Home.this, BorrowedBook.class);
                startActivity(intent);
                break;
            }
            case R.id.tv_overdue_book: {
                Intent intent = new Intent(User_Home.this, OverdueBook.class);
                startActivity(intent);
                break;
            }
            case R.id.tv_transaction_history: {
                Intent intent = new Intent(User_Home.this, TransactionHistory.class);
                startActivity(intent);
                break;
            }
            case R.id.tv_buy_credit: {
                Intent intent = new Intent(User_Home.this, BuyCredit.class);
                startActivity(intent);
                break;
            }
        }
    }
    @SuppressLint("NonConstantResourceId")
    void BottomNavigation()
    {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_user);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_home:
                    startActivity(new Intent(getApplicationContext(),Home.class));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    finish();
                    return true;
                case R.id.bottom_serach:
                    startActivity(new Intent(getApplicationContext(), Search.class));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    finish();
                    return true;
                case R.id.bottom_user:
                    return true;
                case R.id.bottom_notification:
                    startActivity(new Intent(getApplicationContext(), Notifications.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_logout:
                    promptLogoutConfirmation();
                    return true;
            }
            return false;
        });
    }
    private void promptLogoutConfirmation() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(User_Home.this);
        builder.setMessage("Are you sure you want to logout ?");
        builder.setCancelable(true);

        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(),Login.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            dialogInterface.dismiss();
            finish();
        });

        builder.setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss());


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}