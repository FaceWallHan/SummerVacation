package com.start.head.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.start.head.R;

public class LoginActivity extends BroadcastBaseActivity {
    private EditText textPassword,account;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        account=findViewById(R.id.account);
        textPassword=findViewById(R.id.textPassword);
        login=findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String accountText=account.getText().toString().trim();
                String password=textPassword.getText().toString().trim();
                if (accountText.equals("admin")&&password.equals("123456")){
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this, "accountText or password is invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}