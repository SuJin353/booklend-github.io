package com.example.booklendadmin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserInfo extends AppCompatActivity {
    ImageView iv_user_avatar;
    TextView tv_username_info, tv_uid_info, tv_email_info, tv_school_info, tv_class_info, tv_borrowed_info, tv_overdue_info;
    ListView lv_borrowed_book, lv_overdue_book;
    ArrayList<Book> borrowedArrayList, overdueArrayList;
    BookAdapter borrowedBookAdapter, overdueBookAdapter;
    int borrowed_book_num = 0, overdue_book_num = 0;
    String uid;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        Mapping();
        ShowInfo();
        ClickItem();
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        if (view.getId() == R.id.ibt_back) {
            finish();
        }
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

        lv_borrowed_book = findViewById(R.id.lv_borrowed_book);
        lv_overdue_book = findViewById(R.id.lv_overdue_book);

        borrowedArrayList = new ArrayList<>();
        overdueArrayList = new ArrayList<>();

        borrowedBookAdapter = new BookAdapter(borrowedArrayList, UserInfo.this);
        overdueBookAdapter = new BookAdapter(overdueArrayList, UserInfo.this);

        lv_borrowed_book.setAdapter(borrowedBookAdapter);
        lv_overdue_book.setAdapter(overdueBookAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference();

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
                                for (DataSnapshot ds : snapshot.getChildren()){
                                    borrowedArrayList.add(ds.getValue(Book.class));
                                }
                                borrowedBookAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    databaseReference.child("Overdue").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists())
                            {
                                overdue_book_num = (int) snapshot.getChildrenCount();
                                tv_overdue_info.setText(String.valueOf(overdue_book_num));
                                for (DataSnapshot ds : snapshot.getChildren()){
                                    overdueArrayList.add(ds.getValue(Book.class));
                                }
                                overdueBookAdapter.notifyDataSetChanged();
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
    void ClickItem()
    {
        lv_overdue_book.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UserInfo.this);
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure this book is returned?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", (dialogInterface, i1) -> {
                    String id = overdueArrayList.get(i).getKey();
                    databaseReference.child("Overdue").child(uid).child(id).removeValue();
                    overdueArrayList.remove(i);
                    overdueBookAdapter.notifyDataSetChanged();
                });
                builder.setNegativeButton("No", (dialogInterface, i1) -> {
                    dialogInterface.dismiss();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        lv_borrowed_book.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UserInfo.this);
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure this book is returned?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", (dialogInterface, i1) -> {
                    String id = borrowedArrayList.get(i).getKey();
                    databaseReference.child("Borrowed").child(uid).child(id).removeValue();
                    borrowedArrayList.remove(i);
                    borrowedBookAdapter.notifyDataSetChanged();
                });
                builder.setNegativeButton("No", (dialogInterface, i1) -> {
                    dialogInterface.dismiss();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
}