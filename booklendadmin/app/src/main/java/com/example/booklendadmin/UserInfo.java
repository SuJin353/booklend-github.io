package com.example.booklendadmin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserInfo extends AppCompatActivity {
    ImageView iv_user_avatar;
    TextView tv_username_info, tv_uid_info, tv_email_info, tv_school_info, tv_class_info, tv_borrowed_info, tv_overdue_info;
    int borrowed_book_num = 0, overdue_book_num = 0;
    String uid;
    Bundle bundle;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        Mapping();
        ShowInfo();
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibt_back: {
                finish();
                break;
            }
            case R.id.bt_borrowed_book: {
                replaceFragment(new BorrowedBook());
            }
            case R.id.bt_overdue_book: {
                replaceFragment(new OverdueBook());
            }
        }
    }
    private void replaceFragment(Fragment fragment)
    {
        bundle.putString("UID", uid);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
    void Mapping()
    {
        iv_user_avatar = findViewById(R.id.iv_user_avatar);
        tv_username_info = findViewById(R.id.tv_username_info);
        tv_uid_info = findViewById(R.id.tv_uid_info);
        tv_email_info = findViewById(R.id.tv_email_info);
        tv_school_info = findViewById(R.id.tv_school_info);
        tv_class_info = findViewById(R.id.tv_class_info);
        tv_borrowed_info = findViewById(R.id.tv_borrowed_info);
        tv_overdue_info = findViewById(R.id.tv_overdue_info);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        bundle = new Bundle();
    }
    void ShowInfo()
    {
        Intent intent = getIntent();
        uid = intent.getStringExtra("UID");
        databaseReference.child("users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Glide.with(UserInfo.this).load(snapshot.child("uri").getValue(String.class)).circleCrop().into(iv_user_avatar);
                    tv_username_info.setText(snapshot.child("username").getValue(String.class));
                    tv_uid_info.setText(uid);
                    tv_email_info.setText(snapshot.child("email").getValue(String.class));
                    tv_school_info.setText(snapshot.child("school").getValue(String.class));
                    tv_class_info.setText(snapshot.child("user_class").getValue(String.class));
                    databaseReference.child("Borrowed").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists())
                            {
                                borrowed_book_num = (int) snapshot.getChildrenCount();
                                tv_borrowed_info.setText(String.valueOf(borrowed_book_num));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}