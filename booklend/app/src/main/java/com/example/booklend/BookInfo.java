package com.example.booklend;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.net.URI;

public class BookInfo extends AppCompatActivity {
    TextView tv_name_info, tv_genre_info, tv_author_info, tv_price_info;
    ImageView iv_book_cover;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        Mapping();
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
        }
    }
    void Mapping(){
        iv_book_cover = findViewById(R.id.iv_book_cover);
        tv_name_info = findViewById(R.id.tv_name_info);
        tv_genre_info = findViewById(R.id.tv_genre_info);
        tv_author_info = findViewById(R.id.tv_author_info);
        tv_price_info = findViewById(R.id.tv_price_info);
    }
    void showBookInfo()
    {
        Intent intent = getIntent();
        Glide.with(this).load(Uri.parse(intent.getStringExtra("IMAGE"))).into(iv_book_cover);
        tv_name_info.setText(intent.getStringExtra("NAME"));
        tv_author_info.setText(intent.getStringExtra("AUTHOR"));
        tv_genre_info.setText(intent.getStringExtra("GENRE"));
        tv_price_info.setText(String.valueOf(intent.getIntExtra("PRICE",0)));
    }
}