package com.example.booklendadmin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AddBook extends AppCompatActivity {
    private Button bt_add_book;
    private ImageView iv_book_cover;
    private EditText et_book_name, et_book_author, et_book_price, et_book_quantity, et_book_description;
    private Spinner sp_book_genre;
    private Uri imageUri;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_book);
        Mapping();
        BottomNavigation();
        Add();
    }
    @SuppressLint("NotConstructor")
    void Add(){
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        imageUri = data.getData();
                        iv_book_cover.setImageURI(imageUri);
                    }
                    else {
                        Toast.makeText(AddBook.this, "No image selected", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        iv_book_cover.setOnClickListener(view -> {
            Intent photoPicker = new Intent();
            photoPicker.setAction(Intent.ACTION_GET_CONTENT);
            photoPicker.setType("image/*");
            activityResultLauncher.launch(photoPicker);
        });
        bt_add_book.setOnClickListener(view -> {
            if (imageUri != null)
            {
                String name = et_book_name.getText().toString();
                String genre = sp_book_genre.getSelectedItem().toString();
                String author = et_book_author.getText().toString();
                int price = Integer.parseInt(et_book_price.getText().toString());
                int quantity = Integer.parseInt(et_book_quantity.getText().toString());
                String description = et_book_description.getText().toString();
                final StorageReference imageReference = storageReference.child("Books").child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
                imageReference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> imageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    String key = databaseReference.push().getKey();
                    Book book = new Book(key, uri.toString(), name, genre, author, price, quantity, description, 0);
                    databaseReference.child(genre).child(key).setValue(book);
                    et_book_description.setText("");
                    Toast.makeText(AddBook.this, "Upload", Toast.LENGTH_SHORT).show();
                })).addOnFailureListener(e -> Toast.makeText(AddBook.this, "Failed", Toast.LENGTH_SHORT).show());
            }
            else
            {
                Toast.makeText(AddBook.this, "Please selected image", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private String getFileExtension(Uri fileUri)
    {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(fileUri));
    }
    void Mapping()
    {
        iv_book_cover = findViewById(R.id.iv_book_cover);
        bt_add_book = findViewById(R.id.bt_add_book);
        et_book_name = findViewById(R.id.et_book_name);
        sp_book_genre = findViewById(R.id.sp_book_genre);
        et_book_author = findViewById(R.id.et_book_author);
        et_book_price = findViewById(R.id.et_book_price);
        et_book_quantity = findViewById(R.id.et_book_quantity);
        et_book_description = findViewById(R.id.et_book_description);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Genres, R.layout.custom_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_book_genre.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Books");
        storageReference = FirebaseStorage.getInstance().getReference();
    }
    @SuppressLint("NonConstantResourceId")
    void BottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_add_book);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.bottom_add_book:
                    return true;
                case R.id.bottom_edit_book:
                    startActivity(new Intent(AddBook.this, EditBook.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
            }
            return false;
        });
    }
}