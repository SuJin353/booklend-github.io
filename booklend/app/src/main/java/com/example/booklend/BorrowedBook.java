package com.example.booklend;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class BorrowedBook extends AppCompatActivity {
    GridView gv_borrowed_book;
    ImageButton ibt_back;
    ArrayList<Book> bookArrayList;
    GridViewAdapter gridViewAdapter;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_borrowed_book);
        Mapping();
        ReadData();
        Click();
    }
    void Click()
    {
        ibt_back.setOnClickListener(view -> {
            finish();
        });
        gv_borrowed_book.setOnItemClickListener((adapterView, view, i, l) -> {
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
        });
    }
    void Mapping(){
        ibt_back = findViewById(R.id.ibt_back);
        gv_borrowed_book = findViewById(R.id.gv_borrowed_book);
        bookArrayList = new ArrayList<>();
        gridViewAdapter = new GridViewAdapter(bookArrayList,this);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        gv_borrowed_book.setAdapter(gridViewAdapter);
    }
    void ReadData()
    {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference.child("Borrowed").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot ds: snapshot.getChildren()){
                        Book book = ds.getValue(Book.class);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd a 'at' HH:mm:ss");
                        Calendar calendar = new GregorianCalendar();
                        String currentDateAndTime = sdf.format(calendar.getTime());
                        try {
                            Date date1 = sdf.parse(currentDateAndTime);
                            Date date2 = sdf.parse(book.getReturn_date());
                            if (date1.after(date2)){
                                databaseReference.child("Overdue").child(uid).child(book.getKey()).setValue(book);
                                databaseReference.child("Borrowed").child(uid).child(book.getKey()).removeValue();
                            }
                            else {
                                bookArrayList.add(book);
                            }
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }

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