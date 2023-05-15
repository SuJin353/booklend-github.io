package com.example.booklend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {
    private EditText et_username, et_password;
    private Button bt_sign_in, bt_forget_password, bt_create_account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Mapping();
    }
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.bt_sign_in:
            {
                Intent intent = new Intent(Login.this, Menu.class);
                startActivity(intent);
                break;
            }
            case R.id.bt_create_account: {
                Intent intent = new Intent(Login.this, CreateAccount.class);
                startActivity(intent);
                break;
            }
            case R.id.bt_forget_password: {
                Intent intent = new Intent(Login.this, ForgetPassword.class);
                startActivity(intent);
                break;
            }
        }
        finish();
    }
    void Mapping()
    {
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText)  findViewById(R.id.et_password);
        bt_sign_in = (Button) findViewById(R.id.bt_sign_in);
        bt_forget_password = (Button) findViewById(R.id.bt_forget_password);
        bt_create_account = (Button) findViewById(R.id.bt_create);
    }
}