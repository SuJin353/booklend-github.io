package com.example.booklend;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BuyCredit extends AppCompatActivity {
    DatabaseReference databaseReference;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_buy_credit);
        Mapping();
    }
    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibt_back: {
                finish();
                break;
            }
            case R.id.bt_buy: {
                final AlertDialog.Builder builder = new AlertDialog.Builder(BuyCredit.this);
                builder.setTitle("Are you sure want to buy more credit");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", ((dialogInterface, i) -> {
                    databaseReference.child("credit").setValue(1000);
                    dialogInterface.dismiss();
                }));
                builder.setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
            }
        }
    }
    void Mapping(){
        uid  = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
    }
}