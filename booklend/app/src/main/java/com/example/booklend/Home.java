package com.example.booklend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home extends AppCompatActivity{
    RecyclerView recyclerView;
    ArrayList<ParentModelClass> parentModelClassArrayList;
    ArrayList<ChildModelClass> childModelClassArrayList;
    ArrayList<ChildModelClass> fantasy_BookList;
    ArrayList<ChildModelClass> sci_fi_BookList;
    ParentAdapter parentAdapter;
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Books");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Mapping();
        BottomNavigation();
        DisplayBook(fantasy_BookList,"Fantasy");
        DisplayBook(sci_fi_BookList,"Science Fiction");
    }
    void DisplayBook( ArrayList<ChildModelClass> childModelClasses, String genre)
    {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                childModelClasses.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Book book = dataSnapshot.getValue(Book.class);
                        childModelClasses.add(new ChildModelClass(book));
                    }
                    parentModelClassArrayList.add(new ParentModelClass(genre, childModelClasses));
                    parentAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReference.child(genre).addListenerForSingleValueEvent(valueEventListener);
    }

    void Mapping()
    {
        recyclerView = findViewById(R.id.rv_parent);
        childModelClassArrayList = new ArrayList<>();
        fantasy_BookList = new ArrayList<>();
        sci_fi_BookList = new ArrayList<>();
        parentModelClassArrayList = new ArrayList<>();
        parentAdapter = new ParentAdapter(parentModelClassArrayList,Home.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(parentAdapter);
    }
    @SuppressLint("NonConstantResourceId")
    void BottomNavigation()
    {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_home:
                    return true;
                case R.id.bottom_serach:
                    startActivity(new Intent(getApplicationContext(), Search.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_user:
                    startActivity(new Intent(getApplicationContext(), User.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_notification:
                    startActivity(new Intent(getApplicationContext(),Notification.class));
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
        final AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
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