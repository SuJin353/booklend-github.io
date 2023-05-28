package com.example.booklend;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class BookInfo extends AppCompatActivity {
    TextView tv_name_info, tv_genre_info, tv_author_info, tv_price_info, tv_quantity_info, tv_description_info;
    String key, uri, name, genre, author, description;
    int price, quantity;
    ImageView iv_book_cover;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        Mapping();
        getInfo();
        showBookInfo();
    }
    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibt_back: {
                Intent intent = new Intent(BookInfo.this, Home.class);
                startActivity(intent);
                break;
            }
            case R.id.bt_lend:{
                promptLendConfirmation();
                break;
            }
        }
    }
    void Mapping(){
        iv_book_cover = findViewById(R.id.iv_book_cover);
        tv_name_info = findViewById(R.id.tv_name_info);
        tv_genre_info = findViewById(R.id.tv_genre_info);
        tv_author_info = findViewById(R.id.tv_author_info);
        tv_price_info = findViewById(R.id.tv_price_info);
        tv_quantity_info = findViewById(R.id.tv_quantity_info);
        tv_description_info = findViewById(R.id.tv_description_info);
    }
    void getInfo()
    {
        Intent intent = getIntent();
        key = intent.getStringExtra("KEY");
        uri = intent.getStringExtra("IMAGE");
        name = intent.getStringExtra("NAME");
        author = intent.getStringExtra("AUTHOR");
        genre = intent.getStringExtra("GENRE");
        price = intent.getIntExtra("PRICE",0);
        quantity = intent.getIntExtra("QUANTITY",0);
        description = intent.getStringExtra("DESCRIPTION");
    }
    void showBookInfo()
    {
        Glide.with(this).load(Uri.parse(uri)).into(iv_book_cover);
        tv_name_info.setText(name);
        tv_genre_info.setText(genre);
        tv_author_info.setText(author);
        tv_price_info.setText(String.valueOf(price));
        tv_quantity_info.setText(String.valueOf(quantity));
        tv_description_info.setText(description);
    }
    private void promptLendConfirmation() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(BookInfo.this);
        builder.setMessage("Are you sure you want to lend this book?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            if (quantity == 0) {
                Toast.makeText(BookInfo.this, "Out of order!", Toast.LENGTH_SHORT).show();
            }
            else{
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                Book book = new Book(key, uri, name, genre, author, price, quantity, description);
                databaseReference.child("Borrowed").child(uid).child(key).setValue(book);
                databaseReference.child("Books").child(genre).child(key).child("quantity").setValue(quantity - 1);

            }
            dialogInterface.dismiss();
        });
        builder.setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}