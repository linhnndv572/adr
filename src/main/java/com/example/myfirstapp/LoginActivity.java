package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Chuyển sang trang Đăng ký
        findViewById(R.id.tv_register_link).setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });

        // Nhấn Đăng nhập -> vào trang chính Summer Drinks
        findViewById(R.id.btn_login).setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }
}
