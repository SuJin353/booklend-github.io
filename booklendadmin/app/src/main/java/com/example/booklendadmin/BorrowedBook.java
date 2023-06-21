package com.example.booklendadmin;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BorrowedBook extends Fragment {
    View view;
    ListView lv_borrowed_book;
    ArrayList<Book> bookArrayList;
    DatabaseReference databaseReference;
    Bundle bundle;

    @Override
    public void onResume() {
        super.onResume();
        String uid = bundle.getString("UID");
        databaseReference.child("Borrowed").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren())
                {
                    bookArrayList.add(ds.getValue(Book.class));
                }
                BookAdapter bookAdapter = new BookAdapter(bookArrayList);
                lv_borrowed_book.setAdapter(bookAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_borrowed_book, container, false);
        lv_borrowed_book = view.findViewById(R.id.lv_borrowed_book);
        bookArrayList = new ArrayList<>();
        bundle = this.getArguments();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        return view;
    }
}