package com.example.booklend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
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
                Intent intent = new Intent(UserInfo.this, User_Home.class);
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
                if (snapshot.exists()){
                    User user = snapshot.getValue(User.class);
                    tv_profile_username.setText(user.username);
                    tv_profile_full_name.setText(user.fullname);
                    tv_profile_credit.setText(String.valueOf(user.getCredit()));
                    tv_profile_email.setText(user.getEmail());
                    tv_profile_phone_number.setText(user.getPhone_number());
                    tv_profile_password.setText(user.getPassword());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    void Mapping()
    {
        tv_profile_username = findViewById(R.id.tv_profile_username);
        tv_profile_full_name = findViewById(R.id.tv_profile_full_name);
        tv_profile_credit = findViewById(R.id.tv_profile_credit);
        tv_profile_email = findViewById(R.id.tv_profile_email);
        tv_profile_phone_number = findViewById(R.id.tv_profile_phone_number);
        tv_profile_password = findViewById(R.id.tv_profile_password);
    }
}