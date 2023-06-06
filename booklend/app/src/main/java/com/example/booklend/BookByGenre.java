package com.example.booklend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookByGenre extends AppCompatActivity {
    TextView tv_genre;
    GridView gv_genre;
    ImageButton ibt_back;
    ArrayList<Book> bookArrayList;
    GridViewAdapter gridViewAdapter;
    DatabaseReference databaseReference;
    String genre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_by_genre);
        Mapping();
        ReadData();
        ibt_back.setOnClickListener(view -> {
            finish();
        });
        gv_genre.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(BookByGenre.this, BookInfo.class);
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
    void Mapping(){
        tv_genre = findViewById(R.id.tv_genre);
        ibt_back = findViewById(R.id.ibt_back);
        gv_genre = findViewById(R.id.gv_genre);
        bookArrayList = new ArrayList<>();
        gridViewAdapter = new GridViewAdapter(bookArrayList,this);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Books");
        gv_genre.setAdapter(gridViewAdapter);
        Intent intent = getIntent();
        genre = intent.getStringExtra("GENRE");
    }
    void ReadData()
    {
        tv_genre.setText(genre);
        databaseReference.child(genre).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot ds: snapshot.getChildren()){
                        Book book = ds.getValue(Book.class);
                        bookArrayList.add(book);
                    }
                    gridViewAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}