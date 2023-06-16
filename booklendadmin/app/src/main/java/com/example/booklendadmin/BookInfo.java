package com.example.booklendadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class BookInfo extends AppCompatActivity {
    private ImageView iv_book_cover;
    private EditText et_book_name, et_book_author, et_book_price, et_book_quantity, et_book_description;
    private Spinner sp_book_genre;
    private Uri imageUri;
    String key, uri, name, genre, author, description, username, user_uri;
    int price, quantity, borrowed, UserCredit;
    ArrayAdapter<CharSequence> adapter;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_book_info);
        Mapping();
        getInfo();
        showBookInfo();
    }
    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibt_back: {
                finish();
                break;
            }
            case R.id.bt_edit_book:
                Edit();
                break;
        }
    }
    void Mapping()
    {
        iv_book_cover = findViewById(R.id.iv_book_cover);
        et_book_name = findViewById(R.id.et_book_name);
        sp_book_genre = findViewById(R.id.sp_book_genre);
        et_book_author = findViewById(R.id.et_book_author);
        et_book_price = findViewById(R.id.et_book_price);
        et_book_quantity = findViewById(R.id.et_book_quantity);
        et_book_description = findViewById(R.id.et_book_description);

        adapter = ArrayAdapter.createFromResource(this, R.array.Genres, R.layout.custom_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_book_genre.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Books");
        storageReference = FirebaseStorage.getInstance().getReference();
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
        et_book_name.setText(name);
        sp_book_genre.setSelection(adapter.getPosition(genre));
        et_book_author.setText(author);
        et_book_price.setText(String.valueOf(price));
        et_book_quantity.setText(String.valueOf(quantity));
        et_book_description.setText(description);
    }
    void Edit()
    {

    }
}