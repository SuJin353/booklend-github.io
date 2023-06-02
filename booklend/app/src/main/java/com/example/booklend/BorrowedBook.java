package com.example.booklend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class BorrowedBook extends AppCompatActivity {
    GridView gv_borrowed_book;
    ImageButton ibt_back;
    ArrayList<Book> bookArrayList;
    GridViewAdapter gridViewAdapter;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrowed_book);
        Mapping();
        ReadData();
        ibt_back = findViewById(R.id.ibt_back);
        ibt_back.setOnClickListener(view -> {
            Intent intent =  new Intent(BorrowedBook.this, User.class);
            startActivity(intent);
            finish();
        });
        gv_borrowed_book.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(BorrowedBook.this, BookInfo.class);
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
            }
        });
    }
    void Mapping(){
        gv_borrowed_book = findViewById(R.id.gv_borrowed_book);
        ibt_back = findViewById(R.id.ibt_back);
        bookArrayList = new ArrayList<>();
        gridViewAdapter = new GridViewAdapter(bookArrayList,this);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Borrowed");
        gv_borrowed_book.setAdapter(gridViewAdapter);
    }
    void ReadData()
    {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
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