package com.example.booklendadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddNotification extends AppCompatActivity {
    EditText et_title, et_message;
    Button bt_add_notification;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_notification);
        Mapping();
        BottomNavigation();
        bt_add_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Add();
            }
        });
    }
    void Mapping()
    {
        et_title = findViewById(R.id.et_title);
        et_message = findViewById(R.id.et_message);
        bt_add_notification = findViewById(R.id.bt_add_notification);
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }
    void Add()
    {
        String key = databaseReference.push().getKey();
        databaseReference.child("Notification").child(key).child("Title").setValue(et_title.getText().toString());
        databaseReference.child("Notification").child(key).child("Message").setValue(et_message.getText().toString());
        Toast.makeText(AddNotification.this, "Success", Toast.LENGTH_SHORT).show();
    }
    @SuppressLint("NonConstantResourceId")
    void BottomNavigation()
    {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_notification);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_add_book:
                    startActivity(new Intent(getApplicationContext(), AddBook.class));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    finish();
                    return true;
                case R.id.bottom_edit_book:
                    startActivity(new Intent(getApplicationContext(), EditBook.class));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    finish();
                    return true;
                case R.id.bottom_user_management:
                    startActivity(new Intent(getApplicationContext(), UserManager.class));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    finish();
                    return true;
                case R.id.bottom_notification:
                    return true;
            }
            return false;
        });
    }
}