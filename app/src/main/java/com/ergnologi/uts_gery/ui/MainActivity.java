package com.ergnologi.uts_gery.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ergnologi.uts_gery.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etUsername, etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Hide Status Bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        //View Binding
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
    }

    //Method Login
    private void login(String username, String password) {
        //Pengecekan Data Inputan apabila kosong
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Login gagal, data input kosong!", Toast.LENGTH_SHORT).show();
            return;
        }

        //Pengecekan Username dan Password
        if (username.equals("admin") && password.equals("admin")) {
            Toast.makeText(getApplicationContext(), "Login Berhasil!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
        } else
            Toast.makeText(getApplicationContext(), "Login gagal, periksa lagi akun anda!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        login(etUsername.getText().toString().trim(), etPassword.getText().toString().trim());
    }
}
