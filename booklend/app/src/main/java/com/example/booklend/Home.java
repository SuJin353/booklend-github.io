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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home extends AppCompatActivity implements RecycleViewInterface{
    RecyclerView rv_fantasy_book;
    ArrayList<Book> bookArrayList;
    BookItemAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Books");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Mapping();
        BottomNavigation();
        DisplayBook();
    }
    void DisplayBook()
    {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Book book = dataSnapshot.getValue(Book.class);
                    bookArrayList.add(book);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Login.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                dialogInterface.dismiss();
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    void Mapping()
    {
        rv_fantasy_book = findViewById(R.id.rv_fantasy_book);
        linearLayoutManager = new LinearLayoutManager(Home.this, LinearLayoutManager.HORIZONTAL, false);
        rv_fantasy_book.setLayoutManager(linearLayoutManager);
        bookArrayList = new ArrayList<>();
        adapter = new BookItemAdapter(bookArrayList,this, this);
        rv_fantasy_book.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(Home.this, BookInfo.class);
        intent.putExtra("IMAGE",bookArrayList.get(position).getImageUri());
        intent.putExtra("NAME", bookArrayList.get(position).getName());
        intent.putExtra("GENRE", bookArrayList.get(position).getGenre());
        intent.putExtra("AUTHOR", bookArrayList.get(position).getAuthor());
        intent.putExtra("PRICE", bookArrayList.get(position).getPrice());
        startActivity(intent);
    }
}