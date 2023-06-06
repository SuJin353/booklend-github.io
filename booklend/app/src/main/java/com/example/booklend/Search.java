package com.example.booklend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.GridView;
import android.widget.SearchView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Search extends AppCompatActivity {
    SearchView search_view;
    GridView gv_search;
    ArrayList<Book> bookArrayList;
    GridViewAdapter gridViewAdapter;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_search);
        Mapping();
        BottomNavigation();
        SearchBook();
        Click(bookArrayList);
    }
    void Click(ArrayList<Book> bookArrayList)
    {
        gv_search.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(Search.this, BookInfo.class);
            intent.putExtra("KEY", bookArrayList.get(i).getKey());
            intent.putExtra("IMAGE", bookArrayList.get(i).getImageUri());
            intent.putExtra("NAME",  bookArrayList.get(i).getName());
            intent.putExtra("GENRE",   bookArrayList.get(i).getGenre());
            intent.putExtra("AUTHOR",   bookArrayList.get(i).getAuthor());
            intent.putExtra("PRICE",   bookArrayList.get(i).getPrice());
            intent.putExtra("QUANTITY",  bookArrayList.get(i).getQuantity());
            intent.putExtra("DESCRIPTION",   bookArrayList.get(i).getDescription());
            intent.putExtra("BORROWED",  bookArrayList.get(i).getBorrowed());
            startActivity(intent);
        });
    }
    void SearchBook(){
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot genres : snapshot.getChildren()){
                        for (DataSnapshot books: genres.getChildren()){
                            if (snapshot.exists()){
                                    Book book = books.getValue(Book.class);
                                    bookArrayList.add(book);
                            }
                            gridViewAdapter.notifyDataSetChanged();
                        }
                    }
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
        ArrayList<Book> searchList = new ArrayList<>();
        for (Book book : bookArrayList){
            if ((book.getName().toLowerCase().contains(text.toLowerCase())) || (book.getAuthor().toLowerCase().contains(text.toLowerCase()))) {
                searchList.add(book);
            }
        }
        GridViewAdapter gridviewAdapter = new GridViewAdapter(searchList,this);
        gv_search.setAdapter(gridviewAdapter);
        Click(searchList);
    }
    void Mapping()
    {
        search_view = findViewById(R.id.search_view);
        gv_search = findViewById(R.id.gv_search);
        bookArrayList = new ArrayList<>();
        gridViewAdapter = new GridViewAdapter(bookArrayList,this);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Books");
        gv_search.setAdapter(gridViewAdapter);
    }
    @SuppressLint("NonConstantResourceId")
    void BottomNavigation()
    {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_serach);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_home:
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    finish();
                    return true;
                case R.id.bottom_serach:
                    return true;
                case R.id.bottom_user:
                    startActivity(new Intent(getApplicationContext(), User_Home.class));
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
        final AlertDialog.Builder builder = new AlertDialog.Builder(Search.this);
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