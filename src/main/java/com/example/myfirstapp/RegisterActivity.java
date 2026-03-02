package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Quay lại trang Đăng nhập
        findViewById(R.id.tv_login_link).setOnClickListener(v -> {
            finish();
        });

        // Nhấn Đăng ký -> vào trang chính Summer Drinks
        findViewById(R.id.btn_register).setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finishAffinity();
        });
    }
}
