package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DrinkDetailActivity extends AppCompatActivity {

    private String drinkId, drinkName, drinkDesc, drinkPrice, drinkEmoji, drinkCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_detail);

        // Get data from intent
        Intent intent = getIntent();
        drinkId = intent.getStringExtra("drink_id");
        drinkName = intent.getStringExtra("drink_name");
        drinkDesc = intent.getStringExtra("drink_desc");
        drinkPrice = intent.getStringExtra("drink_price");
        drinkEmoji = intent.getStringExtra("drink_emoji");
        drinkCategory = intent.getStringExtra("drink_category");

        // Bind views
        TextView tvEmoji = findViewById(R.id.tv_detail_emoji);
        TextView tvName = findViewById(R.id.tv_detail_name);
        TextView tvCategory = findViewById(R.id.tv_detail_category);
        TextView tvPrice = findViewById(R.id.tv_detail_price);
        TextView tvDescription = findViewById(R.id.tv_detail_description);
        TextView tvCategoryInfo = findViewById(R.id.tv_detail_category_info);
        TextView tvPriceInfo = findViewById(R.id.tv_detail_price_info);

        // Set data
        tvEmoji.setText(drinkEmoji);
        tvName.setText(drinkName);
        tvCategory.setText(drinkCategory);
        tvPrice.setText(drinkPrice);
        tvDescription.setText(drinkDesc != null && !drinkDesc.isEmpty() ? drinkDesc : "Chưa có mô tả");
        tvCategoryInfo.setText(drinkCategory);
        tvPriceInfo.setText(drinkPrice);

        // Back button
        findViewById(R.id.btn_back).setOnClickListener(v -> finish());

        // Edit button
        findViewById(R.id.btn_detail_edit).setOnClickListener(v -> {
            Intent editIntent = new Intent(this, AddEditDrinkActivity.class);
            editIntent.putExtra("drink_id", drinkId);
            editIntent.putExtra("drink_name", drinkName);
            editIntent.putExtra("drink_desc", drinkDesc);
            editIntent.putExtra("drink_price", drinkPrice);
            editIntent.putExtra("drink_emoji", drinkEmoji);
            editIntent.putExtra("drink_category", drinkCategory);
            startActivity(editIntent);
            finish();
        });

        // Delete button
        findViewById(R.id.btn_detail_delete).setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc muốn xóa \"" + drinkName + "\"?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        DatabaseReference drinksRef = FirebaseDatabase.getInstance().getReference("drinks");
                        drinksRef.child(drinkId).removeValue()
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(this, "Đã xóa!", Toast.LENGTH_SHORT).show();
                                    finish();
                                })
                                .addOnFailureListener(e ->
                                        Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });
    }
}
