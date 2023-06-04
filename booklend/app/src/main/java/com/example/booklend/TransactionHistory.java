package com.example.booklend;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TransactionHistory extends AppCompatActivity {
    ImageButton ibt_back;
    ListView lv_transaction_history;
    ListViewAdapter listViewAdapter;
    ArrayList<TransactionInfo> transactionInfoArrayList;
    String uid;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_transaction_history);
        Mapping();
        ReadData();
        ibt_back.setOnClickListener(view -> {
            finish();
        });
    }
    void Mapping(){
        ibt_back = findViewById(R.id.ibt_back);
        lv_transaction_history = findViewById(R.id.lv_transaction_history);
        transactionInfoArrayList = new ArrayList<>();
        listViewAdapter = new ListViewAdapter(this, transactionInfoArrayList);
        lv_transaction_history.setAdapter(listViewAdapter);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Transaction History").child(uid);
    }
    void ReadData(){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot ds : snapshot.getChildren()){
                        TransactionInfo transactionInfo = ds.getValue(TransactionInfo.class);
                        transactionInfoArrayList.add(transactionInfo);
                    }
                    listViewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}