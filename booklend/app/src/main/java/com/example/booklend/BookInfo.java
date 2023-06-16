package com.example.booklend;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class BookInfo extends AppCompatActivity {
    TextView tv_name_info, tv_genre_info, tv_author_info, tv_price_info, tv_quantity_info, tv_description_info, tv_borrowed_info;
    EditText et_comment;
    ListView lv_comment;
    CommentAdapter adapter;
    ArrayList<Comment> commentArrayList;
    String key, uri, name, genre, author, description, username, user_uri;
    int price, quantity, borrowed, UserCredit;
    ImageView iv_book_cover;
    String uid;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_book_info);
        Mapping();
        getInfo();
        showBookInfo();
        ShowComment();
    }
    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibt_back: {
                finish();
                break;
            }
            case R.id.bt_lend: {
                promptLendConfirmation();
                break;
            }
            case R.id.ibt_send_comment: {
                SendComment();
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
        tv_borrowed_info = findViewById(R.id.tv_borrowed_info);
        et_comment = findViewById(R.id.et_comment);
        lv_comment = findViewById(R.id.lv_comment);
        commentArrayList = new ArrayList<>();
        adapter = new CommentAdapter(commentArrayList,this);
        lv_comment.setAdapter(adapter);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();
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
        borrowed = intent.getIntExtra("BORROWED",0);
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
        tv_borrowed_info.setText(String.valueOf(borrowed));
        tv_description_info.setText(description);
    }
    private void promptLendConfirmation() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(BookInfo.this);
        builder.setMessage("Are you sure you want to lend this book?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", (dialogInterface, i) -> databaseReference.child("users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                UserCredit = user.getCredit();
                if (UserCredit < price) {
                    builder.setTitle("You don't have enough credit! Buy more?");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                        Intent intent = new Intent(BookInfo.this, BuyCredit.class);
                        startActivity(intent);
                    });
                    builder.setNegativeButton("Canceled", (dialogInterface, i) -> dialogInterface.dismiss());
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else if (quantity == 0) {
                    Toast.makeText(BookInfo.this, "Out of order!", Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.child("Borrowed").child(uid).child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                builder.setTitle("You currently in borrowed time of this book. Press 'Continue' will reset the borrowed time and deduction your credit");
                                builder.setCancelable(true);
                                builder.setPositiveButton("Continue", (dialogInterface, i) -> Lend());
                                builder.setNegativeButton("Canceled", (dialogInterface, i) -> dialogInterface.dismiss());
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            }
                            else{
                                Lend();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                dialogInterface.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }));
        builder.setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void Lend(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd a 'at' HH:mm:ss");
        Calendar calendar = new GregorianCalendar();
        String borrowedDateAndTime = sdf.format(calendar.getTime());
        calendar.add(Calendar.DATE, 7);
        String returnDateAndTime =  sdf.format(calendar.getTime());

        databaseReference.child("Books").child(genre).child(key).child("quantity").setValue(quantity - 1);
        databaseReference.child("Books").child(genre).child(key).child("borrowed").setValue(borrowed + 1);
        databaseReference.child("users").child(uid).child("credit").setValue(UserCredit - price);
        Book book = new Book(key, uri, name, genre, author, price, quantity, description, borrowed, borrowedDateAndTime, returnDateAndTime);
        databaseReference.child("Borrowed").child(uid).child(key).setValue(book);

        String key = databaseReference.push().getKey();
        TransactionInfo transactionInfo = new TransactionInfo(key, name, price, borrowedDateAndTime);
        databaseReference.child("Transaction History").child(uid).child(key).setValue(transactionInfo);

        final AlertDialog.Builder builder = new AlertDialog.Builder(BookInfo.this);
        builder.setTitle("Lend successfully");
        builder.setMessage("Your book return date: " + returnDateAndTime);
        builder.setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
    void SendComment()
    {
        et_comment.getText().toString();
        databaseReference.child("users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss", Locale.getDefault());
                String currentDateAndTime = sdf.format(new Date());
                User user = snapshot.getValue(User.class);
                username = user.getUsername();
                user_uri = user.getUri();
                Comment comment= new Comment(username, user_uri, et_comment.getText().toString(), currentDateAndTime);
                databaseReference.child("Comment").child(key).child(uid).setValue(comment);
                ShowComment();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    void ShowComment()
    {
        databaseReference.child("Comment").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    commentArrayList.add(ds.getValue(Comment.class));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}