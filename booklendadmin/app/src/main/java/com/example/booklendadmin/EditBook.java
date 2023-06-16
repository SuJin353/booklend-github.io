package com.example.booklendadmin;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditBook extends AppCompatActivity {
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
        setContentView(R.layout.activity_edit_book);
        Mapping();
        BottomNavigation();
        SearchBook();
        Click(bookArrayList);
    }
    void Click(ArrayList<Book> bookArrayList)
    {
        gv_search.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(EditBook.this, BookInfo.class);
            intent.putExtra("KEY", bookArrayList.get(i).getKey());
            intent.putExtra("IMAGE", bookArrayList.get(i).getImageUri());
            intent.putExtra("NAME", bookArrayList.get(i).getName());
            intent.putExtra("GENRE", bookArrayList.get(i).getGenre());
            intent.putExtra("AUTHOR", bookArrayList.get(i).getAuthor());
            intent.putExtra("PRICE", bookArrayList.get(i).getPrice());
            intent.putExtra("QUANTITY", bookArrayList.get(i).getQuantity());
            intent.putExtra("DESCRIPTION", bookArrayList.get(i).getDescription());
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
        bottomNavigationView.setSelectedItemId(R.id.bottom_edit_book);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_add_book:
                    startActivity(new Intent(getApplicationContext(), AddBook.class));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    finish();
                    return true;
                case R.id.bottom_edit_book:
                    return true;
            }
            return false;
        });
    }
}