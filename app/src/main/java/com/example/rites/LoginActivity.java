package com.example.rites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private Button buttonLogin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonLogin=findViewById(R.id.buttonLogin);
        editTextEmail=findViewById(R.id.editTextEmail);
        editTextPassword=findViewById(R.id.editTextPassword);

        editTextPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=editTextEmail.getText().toString();
                password=editTextPassword.getText().toString();
            }
        });
    }
}
