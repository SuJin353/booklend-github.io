package com.example.booklendadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserManager extends AppCompatActivity {
    SearchView search_view;
    ListView lv_search;
    ArrayList<User> userArrayList;
    UserAdapter userAdapter;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_user_manager);
        Mapping();
        BottomNavigation();
        SearchUser();
        Click(userArrayList);
    }
    void Click(ArrayList<User> userArrayList)
    {
        lv_search.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(UserManager.this, UserInfo.class);
            intent.putExtra("UID", userArrayList.get(i).getUid());
            startActivity(intent);
        });
    }
    void SearchUser(){
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    for (DataSnapshot ds : snapshot.getChildren())
                    {
                        User user = new User(ds.child("username").getValue(String.class), ds.getKey(), ds.child("uri").getValue(String.class));
                        userArrayList.add(user);
                    }
                    userAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        search_view.clearFocus();
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                search(s);
                return true;
            }
        });
    }
    private void search(String text)
    {
        ArrayList<User> searchList = new ArrayList<>();
        for (User user : userArrayList){
            if ((user.getUsername().toLowerCase().contains(text.toLowerCase()))) {
                searchList.add(user);
            }
        }
        UserAdapter adapter = new UserAdapter(searchList, this);
        lv_search.setAdapter(adapter);
        Click(searchList);
    }
    void Mapping()
    {
        search_view = findViewById(R.id.search_view);
        lv_search = findViewById(R.id.lv_search);
        userArrayList = new ArrayList<>();
        userAdapter = new UserAdapter(userArrayList, UserManager.this);
        lv_search.setAdapter(userAdapter);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
    }
    @SuppressLint("NonConstantResourceId")
    void BottomNavigation()
    {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_user_management);
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
                    return true;
                case R.id.bottom_notification:
                    startActivity(new Intent(getApplicationContext(), AddNotification.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
            }
            return false;
        });
    }
}