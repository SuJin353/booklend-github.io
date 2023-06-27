package com.example.booklend;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
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

public class OverdueBook extends AppCompatActivity {
    GridView gv_overdue_book;
    ImageButton ibt_back;
    ArrayList<Book> bookArrayList;
    GridViewAdapter gridViewAdapter;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overdue_book);
        Mapping();
        ReadData();
        Click();
    }
    void Click()
    {
        ibt_back.setOnClickListener(view -> {
            finish();
        });
        gv_overdue_book.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(OverdueBook.this, BookInfo.class);
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
        ibt_back = findViewById(R.id.ibt_back);
        gv_overdue_book = findViewById(R.id.gv_overdue_book);
        bookArrayList = new ArrayList<>();
        gridViewAdapter = new GridViewAdapter(bookArrayList,this);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        gv_overdue_book.setAdapter(gridViewAdapter);
    }
    void ReadData()
    {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference.child("Overdue").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
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