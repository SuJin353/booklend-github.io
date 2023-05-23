package com.example.booklend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserInfo extends AppCompatActivity {
    TextView tv_profile_username, tv_profile_full_name, tv_profile_credit, tv_profile_email, tv_profile_phone_number, tv_profile_password;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        Mapping();
        showUserInfo();
    }
    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibt_back: {
                Intent intent = new Intent(UserInfo.this, Profile.class);
                startActivity(intent);
                break;
            }
            case R.id.tv_change_info: {
                Intent intent = new Intent(UserInfo.this, BorrowedBook.class);
                startActivity(intent);
                break;
            }
        }
        finish();
    }
    public void showUserInfo()
    {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tv_profile_username.setText(snapshot.child("username").getValue(String.class));
                tv_profile_full_name.setText(snapshot.child("fullname").getValue(String.class));
                tv_profile_email.setText(snapshot.child("email").getValue(String.class));
                tv_profile_phone_number.setText(snapshot.child("phone_number").getValue(String.class));
                tv_profile_password.setText(snapshot.child("password").getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    void Mapping()
    {
        tv_profile_username = (TextView) findViewById(R.id.tv_profile_username);
        tv_profile_full_name = (TextView) findViewById(R.id.tv_profile_full_name);
        tv_profile_credit = (TextView) findViewById(R.id.tv_profile_credit);
        tv_profile_email = (TextView) findViewById(R.id.tv_profile_email);
        tv_profile_phone_number = (TextView) findViewById(R.id.tv_profile_phone_number);
        tv_profile_password = (TextView) findViewById(R.id.tv_profile_password);
    }
}