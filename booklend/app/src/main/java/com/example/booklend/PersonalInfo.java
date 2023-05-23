package com.example.booklend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PersonalInfo extends AppCompatActivity {
    TextView tv_profile_name, tv_profile_email, tv_profile_phone_number, tv_profile_password, tv_change_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        Mapping();
        showUserInfo();
    }
    public void showUserInfo()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("username");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String fullname = snapshot.child("fullname").getValue().toString();
                String email = snapshot.child("email").getValue().toString();
                String phone_number = snapshot.child("phone_number").getValue().toString();
                String password = snapshot.child("password").getValue().toString();

                tv_profile_name.setText(fullname);
                tv_profile_email.setText(email);
                tv_profile_phone_number.setText(phone_number);
                tv_profile_password.setText(password);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void  Mapping()
    {
        tv_profile_name = (TextView) findViewById(R.id.tv_profile_name);
        tv_profile_email = (TextView) findViewById(R.id.tv_profile_email);
        tv_profile_phone_number = (TextView) findViewById(R.id.tv_profile_phone_number);
        tv_profile_password = (TextView) findViewById(R.id.tv_profile_password);
        tv_change_info = (TextView) findViewById(R.id.tv_change_info);
    }
}