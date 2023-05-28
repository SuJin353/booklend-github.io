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
    private int i;
    private RecyclerView rv_fantasy_book, rv_sci_fi_book;
    private final ArrayList<ArrayList<Book>> bookArrayList = new ArrayList<>();
    private ArrayList<Book> genre;
    private BookItemAdapter fantasy_adapter, sci_fi_adapter;
    private final String[] genres = {"fantasy","sci_fi"};
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Books");
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
        for (i = 0; i < genres.length; i++)
        {
            int j = i;
            databaseReference.child(genres[i]).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    genre.clear();
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        Book book = dataSnapshot.getValue(Book.class);
                        genre.add(book);
                    }
                    bookArrayList.add(genre);
                    switch (genres[j]){
                        case "fantasy": {
                            rv_fantasy_book.setAdapter(fantasy_adapter);
                            break;
                        }
                        case "sci_fi":{
                            rv_sci_fi_book.setAdapter(sci_fi_adapter);
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
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
    void Mapping()
    {
        rv_fantasy_book = findViewById(R.id.rv_fantasy_book);
        rv_sci_fi_book = findViewById(R.id.rv_sci_fi_book);
        rv_fantasy_book.setLayoutManager(new LinearLayoutManager(Home.this, LinearLayoutManager.HORIZONTAL, false));
        rv_sci_fi_book.setLayoutManager(new LinearLayoutManager(Home.this, LinearLayoutManager.HORIZONTAL, false));
        genre = new ArrayList<>();
        fantasy_adapter =  new BookItemAdapter(genre, this, this);
        sci_fi_adapter =  new BookItemAdapter(genre, this, this);
    }
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(Home.this, BookInfo.class);
        intent.putExtra("KEY",bookArrayList.get(position).get(position).getKey());
        intent.putExtra("IMAGE",bookArrayList.get(position).get(position).getImageUri());
        intent.putExtra("NAME", bookArrayList.get(position).get(position).getName());
        intent.putExtra("GENRE", bookArrayList.get(position).get(position).getGenre());
        intent.putExtra("AUTHOR", bookArrayList.get(position).get(position).getAuthor());
        intent.putExtra("PRICE", bookArrayList.get(position).get(position).getPrice());
        intent.putExtra("QUANTITY", bookArrayList.get(position).get(position).getQuantity());
        intent.putExtra("DESCRIPTION", bookArrayList.get(position).get(position).getDescription());
        startActivity(intent);
    }
}