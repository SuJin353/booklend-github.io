package com.example.booklendadmin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class BookInfo extends AppCompatActivity {
    private ImageView iv_book_cover;
    private EditText et_book_name, et_book_author, et_book_price, et_book_quantity, et_book_description;
    private Spinner sp_book_genre;
    private Uri new_uri;
    private String key, current_uri, name, genre, author, description;
    int price, quantity;
    private ArrayAdapter<CharSequence> adapter;
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
            case R.id.iv_book_cover:
                Intent photoPicker = new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
                break;
        }
    }
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK){
                    Intent data = result.getData();
                    new_uri = data.getData();
                    iv_book_cover.setImageURI(new_uri);
                }
            }
    );
    private String getFileExtension(Uri fileUri)
    {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(fileUri));
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

        storageReference = FirebaseStorage.getInstance().getReference();
    }
    void getInfo()
    {
        Intent intent = getIntent();
        key = intent.getStringExtra("KEY");
        current_uri = intent.getStringExtra("IMAGE");
        name = intent.getStringExtra("NAME");
        author = intent.getStringExtra("AUTHOR");
        genre = intent.getStringExtra("GENRE");
        price = intent.getIntExtra("PRICE",0);
        quantity = intent.getIntExtra("QUANTITY",0);
        description = intent.getStringExtra("DESCRIPTION");

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Books").child(genre).child(key);
    }
    void showBookInfo()
    {
        Glide.with(this).load(Uri.parse(current_uri)).into(iv_book_cover);
        et_book_name.setText(name);
        sp_book_genre.setSelection(adapter.getPosition(genre));
        et_book_author.setText(author);
        et_book_price.setText(String.valueOf(price));
        et_book_quantity.setText(String.valueOf(quantity));
        et_book_description.setText(description);
    }
    void Edit()
    {
        name = et_book_name.getText().toString();
        author = et_book_author.getText().toString();
        genre = sp_book_genre.getSelectedItem().toString();
        price = Integer.parseInt(et_book_price.getText().toString());
        quantity = Integer.parseInt(et_book_quantity.getText().toString());
        description = et_book_description.getText().toString();
        if (name.isEmpty() || author.isEmpty())
        {
            Toast.makeText(BookInfo.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }
        else if (price <= 0 || quantity <= 0){
            Toast.makeText(BookInfo.this, "Invalid input. Number can be less than 0", Toast.LENGTH_SHORT).show();
        }
        else if (new_uri != null)
        {
            final StorageReference imageReference = storageReference.child("Books").child(System.currentTimeMillis() + "." + getFileExtension(new_uri));
            imageReference.putFile(new_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Map<String, Object> updateBook =  new HashMap<String, Object>();
                            updateBook.put("imageUri", uri.toString());
                            updateBook.put("name", name);
                            updateBook.put("author", author);
                            updateBook.put("genre", genre);
                            updateBook.put("price", price);
                            updateBook.put("quantity", quantity);
                            updateBook.put("description", description);
                            databaseReference.updateChildren(updateBook);
                            StorageReference imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(current_uri);
                            imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                }
                            });
                            Toast.makeText(BookInfo.this, "Change info successful", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
        else {
            Map<String, Object> updateBook =  new HashMap<String, Object>();
            updateBook.put("name", name);
            updateBook.put("author", author);
            updateBook.put("genre", genre);
            updateBook.put("price", price);
            updateBook.put("quantity", quantity);
            updateBook.put("description", description);
            databaseReference.child(genre).child(key).updateChildren(updateBook);
            Toast.makeText(BookInfo.this, "Change info successful", Toast.LENGTH_SHORT).show();
        }
    }
}