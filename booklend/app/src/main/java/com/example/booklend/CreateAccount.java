package com.example.booklend;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class CreateAccount extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText et_username, et_full_name, et_email, et_phone_number, et_password, et_confirm_password, et_school, et_class, et_year_of_birth;
    private Spinner sp_gender;
    private TextView tv_create;
    private de.hdodenhof.circleimageview.CircleImageView iv_profile_pic;
    ImageButton ibt_back;
    private Uri imageUri;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("users");
    private StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_create_account);
        Mapping();
        CreateUser();
        ibt_back.setOnClickListener(view -> finish());

    }
    void Mapping()
    {
        ibt_back = findViewById(R.id.ibt_back);
        auth = FirebaseAuth.getInstance();
        et_username = findViewById(R.id.et_username);
        et_full_name = findViewById(R.id.et_fullname);
        et_year_of_birth = findViewById(R.id.et_year_of_birth);
        et_school = findViewById(R.id.et_school);
        et_class = findViewById(R.id.et_class);
        et_email = findViewById(R.id.et_email);
        et_phone_number = findViewById(R.id.et_phone_number);
        et_password = findViewById(R.id.et_password);
        et_confirm_password = findViewById(R.id.et_confirm_password);

        sp_gender = findViewById(R.id.sp_gender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender, R.layout.custom_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_gender.setAdapter(adapter);

        tv_create = findViewById(R.id.tv_create);
        iv_profile_pic = findViewById(R.id.iv_profile_pic);
        storageReference = FirebaseStorage.getInstance().getReference();
    }
    void CreateUser(){
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        imageUri = data.getData();
                        iv_profile_pic.setImageURI(imageUri);
                    }
                    else {
                        Toast.makeText(CreateAccount.this, "No image selected", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        iv_profile_pic.setOnClickListener(view -> {
            Intent photoPicker = new Intent();
            photoPicker.setAction(Intent.ACTION_GET_CONTENT);
            photoPicker.setType("image/*");
            activityResultLauncher.launch(photoPicker);
        });
        tv_create.setOnClickListener(view -> {
            if (imageUri != null){
                String username = et_username.getText().toString().trim();
                String fullname = et_full_name.getText().toString().trim();
                String gender = sp_gender.getSelectedItem().toString();
                String year = et_year_of_birth.getText().toString();
                String school = et_school.getText().toString();
                String user_class = et_class.getText().toString();
                String email = et_email.getText().toString().trim();
                String phone_number = et_phone_number.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                String confirm_password = et_confirm_password.getText().toString().trim();
                final StorageReference imageReference = storageReference.child("Users").child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
                if (username.isEmpty() || fullname.isEmpty() || gender.isEmpty() || year.isEmpty() || school.isEmpty() || user_class.isEmpty() || email.isEmpty() || password.isEmpty() || phone_number.isEmpty())
                {
                    Toast.makeText(CreateAccount.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
                else if (password.length() < 6){
                    et_password.setError("Password must be at least 6 character");
                }
                else if (!password.equals(confirm_password))
                {
                    et_confirm_password.setError("Password are not matching");
                }
                else
                {
                    imageReference.putFile(imageUri)
                            .addOnSuccessListener(taskSnapshot -> imageReference.getDownloadUrl()
                                    .addOnSuccessListener(uri -> auth.createUserWithEmailAndPassword(email,password)
                                            .addOnCompleteListener(task -> {
                                                if (task.isSuccessful()){
                                                    auth.getCurrentUser().sendEmailVerification()
                                                            .addOnSuccessListener(task1 -> {
                                                                String uid = auth.getCurrentUser().getUid();
                                                                User user = new User(uri.toString(), username, fullname, gender, year, school, user_class, email, phone_number, password, 1000);
                                                                databaseReference.child(uid).setValue(user);
                                                                Toast.makeText(CreateAccount.this,"Sign up successful. Check your email for verification", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(CreateAccount.this, Login.class);
                                                                startActivity(intent);
                                                                finish();
                                                            })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Toast.makeText(CreateAccount.this,"Email not send", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                }
                                                else {
                                                    Toast.makeText(CreateAccount.this,"Sign up failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                    })))
                            .addOnFailureListener(e -> Toast.makeText(CreateAccount.this, "Get image failed", Toast.LENGTH_SHORT).show());
                }
            }
            else
            {
                Toast.makeText(CreateAccount.this, "Please selected image", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private String getFileExtension(Uri fileUri)
    {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(fileUri));
    }
}