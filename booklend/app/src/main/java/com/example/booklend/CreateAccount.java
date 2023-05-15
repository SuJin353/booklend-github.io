package com.example.booklend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateAccount extends AppCompatActivity {
    private EditText et_username, et_email, et_phone_number, et_password, et_confirm_password;
    private Button bt_back, bt_create;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Mapping();
    }
    public void onClick(View view){
        switch(view.getId()){
            case R.id.bt_back:
            {
                Intent intent = new Intent(CreateAccount.this, Login.class);
                startActivity(intent);
                break;
            }
            case R.id.bt_create:
            {
                Intent intent = new Intent(CreateAccount.this, Menu.class);
                startActivity(intent);
                break;
            }
        }
        finish();
    }
    void Mapping()
    {
        et_username = (EditText) findViewById(R.id.et_username);
        et_email = (EditText) findViewById(R.id.et_email);
        et_phone_number = (EditText) findViewById(R.id.et_phone_number);
        et_password = (EditText) findViewById(R.id.et_password);
        et_confirm_password = (EditText) findViewById(R.id.et_confirm_password);
        bt_back = (Button) findViewById(R.id.bt_back);
        bt_create = (Button) findViewById(R.id.bt_create);
    }
}