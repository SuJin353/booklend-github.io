package com.example.booklendadmin;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddBook extends AppCompatActivity {
    private Button bt_add_book;
    private ImageView iv_book_cover;
    private EditText et_book_name, et_book_genre, et_book_author, et_book_price;
    private Uri imageUri;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        Mapping();
        Add();
    }
    @SuppressLint("NotConstructor")
    void Add(){
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            imageUri = data.getData();
                            iv_book_cover.setImageURI(imageUri);
                        }
                        else {
                            Toast.makeText(AddBook.this, "No image selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        iv_book_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
        bt_add_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageUri != null)
                {
                    String name = et_book_name.getText().toString();
                    String genre = et_book_genre.getText().toString();
                    String author = et_book_author.getText().toString();
                    int price = Integer.parseInt(et_book_price.getText().toString());
                    final StorageReference imageReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));

                    imageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Book book = new Book(uri.toString(), name, genre, author, price);
                                    String key = databaseReference.push().getKey();
                                    databaseReference.child(key).setValue(book);
                                    Toast.makeText(AddBook.this, "Upload", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddBook.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    Toast.makeText(AddBook.this, "Please selected image", Toast.LENGTH_SHORT).show();
                }
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
        et_book_genre = findViewById(R.id.et_book_genre);
        et_book_author = findViewById(R.id.et_book_author);
        et_book_price = findViewById(R.id.et_book_price);
        databaseReference = FirebaseDatabase.getInstance().getReference("Books");
        storageReference = FirebaseStorage.getInstance().getReference();
    }
}