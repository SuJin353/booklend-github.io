package com.example.booklend;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Notification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        BottomNavigation();
    }
    @SuppressLint("NonConstantResourceId")
    void BottomNavigation()
    {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_notification);
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
                    startActivity(new Intent(getApplicationContext(), User.class));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    finish();
                    return true;
                case R.id.bottom_notification:
                    return true;
                case R.id.bottom_logout:
                    promptLogoutConfirmation();
                    return true;
            }
            return false;
        });
    }
    private void promptLogoutConfirmation() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Notification.this);
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