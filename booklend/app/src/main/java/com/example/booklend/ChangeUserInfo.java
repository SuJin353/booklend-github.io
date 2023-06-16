package com.example.booklend;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class ChangeUserInfo extends AppCompatActivity {
    EditText et_change_username, et_change_full_name, et_change_year_of_birth, et_change_school, et_change_class, et_change_phone_number;
    Spinner sp_change_gender;
    de.hdodenhof.circleimageview.CircleImageView iv_change_profile_pic;
    TextView btn_change_info;
    String uid, current_uri;
    Uri new_uri;
    DatabaseReference databaseReference;
    private StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_change_user_info);
        Mapping();
        ShowCurrentInfo();
    }
    @SuppressLint("NonConstantResourceId")
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.ibt_back:{
                finish();
                break;
            }
            case R.id.btn_change_info:
                ChangeInfo();
                break;
            case R.id.iv_change_profile_pic:
                Intent photoPicker = new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
                break;
        }
    }
    void Mapping()
    {
        iv_change_profile_pic = findViewById(R.id.iv_change_profile_pic);
        et_change_username = findViewById(R.id.et_change_username);
        et_change_full_name = findViewById(R.id.et_change_fullname);
        et_change_year_of_birth = findViewById(R.id.et_change_year_of_birth);
        et_change_school = findViewById(R.id.et_change_school);
        et_change_class = findViewById(R.id.et_change_class);
        et_change_phone_number = findViewById(R.id.et_change_phone_number);
        sp_change_gender = findViewById(R.id.sp_change_gender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender, R.layout.custom_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_change_gender.setAdapter(adapter);
        btn_change_info = findViewById(R.id.btn_change_info);

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
        storageReference = FirebaseStorage.getInstance().getReference();
    }
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK){
                    Intent data = result.getData();
                    new_uri = data.getData();
                    iv_change_profile_pic.setImageURI(new_uri);
                }
            }
    );
    void ChangeInfo()
    {
        String username = et_change_username.getText().toString().trim();
        String fullname = et_change_full_name.getText().toString().trim();
        String gender = sp_change_gender.getSelectedItem().toString();
        String year = et_change_year_of_birth.getText().toString();
        String school = et_change_school.getText().toString();
        String user_class = et_change_class.getText().toString();
        String phone_number = et_change_phone_number.getText().toString().trim();
        if (username.isEmpty() || fullname.isEmpty() || gender.isEmpty() || year.isEmpty() || school.isEmpty() || user_class.isEmpty() || phone_number.isEmpty())
        {
            Toast.makeText(ChangeUserInfo.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }
        else if (new_uri != null)
        {
            final StorageReference imageReference = storageReference.child("Users").child(System.currentTimeMillis() + "." + getFileExtension(new_uri));
            imageReference.putFile(new_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Map<String, Object> updateUser =  new HashMap<String, Object>();
                            updateUser.put("uri", uri.toString());
                            updateUser.put("username", username);
                            updateUser.put("fullname", fullname);
                            updateUser.put("gender", gender);
                            updateUser.put("year_of_birth", year);
                            updateUser.put("school", school);
                            updateUser.put("user_class", user_class);
                            updateUser.put("phone_number", phone_number);
                            databaseReference.updateChildren(updateUser);
                            StorageReference imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(current_uri);
                            imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                }
                            });
                            Toast.makeText(ChangeUserInfo.this, "Change info successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ChangeUserInfo.this, UserInfo.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            });
        }
        else {
            Map<String, Object> updateUser =  new HashMap<String, Object>();
            updateUser.put("username", username);
            updateUser.put("fullname", fullname);
            updateUser.put("gender", gender);
            updateUser.put("year_of_birth", year);
            updateUser.put("school", school);
            updateUser.put("user_class", user_class);
            updateUser.put("phone_number", phone_number);
            databaseReference.updateChildren(updateUser);
            Toast.makeText(ChangeUserInfo.this, "Change info successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ChangeUserInfo.this, UserInfo.class);
            startActivity(intent);
            finish();
        }
    }
    void ShowCurrentInfo()
    {
        Intent intent = getIntent();
        current_uri = intent.getStringExtra("PROFILE_AVATAR");
        Glide.with(ChangeUserInfo.this).load(current_uri).circleCrop().into(iv_change_profile_pic);
        et_change_username.setText(intent.getStringExtra("USERNAME"));
        et_change_full_name.setText(intent.getStringExtra("FULLNAME"));
        et_change_year_of_birth.setText(intent.getStringExtra("YEAR_OF_BIRTH"));
        et_change_school.setText(intent.getStringExtra("SCHOOL"));
        et_change_class.setText(intent.getStringExtra("CLASS"));
        et_change_phone_number.setText(intent.getStringExtra("PHONE_NUMBER"));
    }
    private String getFileExtension(Uri fileUri)
    {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(fileUri));
    }
}