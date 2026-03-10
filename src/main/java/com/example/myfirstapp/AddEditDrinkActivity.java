package com.example.myfirstapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEditDrinkActivity extends AppCompatActivity {

    private EditText etName, etDescription, etPrice, etEmoji, etCategory;
    private Button btnSave;
    private DatabaseReference drinksRef;
    private String drinkId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_drink);

        drinksRef = FirebaseDatabase.getInstance().getReference("drinks");

        etName = findViewById(R.id.et_name);
        etDescription = findViewById(R.id.et_description);
        etPrice = findViewById(R.id.et_price);
        etEmoji = findViewById(R.id.et_emoji);
        etCategory = findViewById(R.id.et_category);
        btnSave = findViewById(R.id.btn_save);
        TextView tvTitle = findViewById(R.id.tv_title);
        TextView btnCancel = findViewById(R.id.btn_cancel);

        // Check if editing existing drink
        if (getIntent().hasExtra("drink_id")) {
            drinkId = getIntent().getStringExtra("drink_id");
            tvTitle.setText("S\u1EEDa \u0111\u1ED3 u\u1ED1ng");
            btnSave.setText("C\u1EADp nh\u1EADt");

            etName.setText(getIntent().getStringExtra("drink_name"));
            etDescription.setText(getIntent().getStringExtra("drink_desc"));
            etPrice.setText(getIntent().getStringExtra("drink_price"));
            etEmoji.setText(getIntent().getStringExtra("drink_emoji"));
            etCategory.setText(getIntent().getStringExtra("drink_category"));
        }

        btnSave.setOnClickListener(v -> saveDrink());
        btnCancel.setOnClickListener(v -> finish());
    }

    private void saveDrink() {
        String name = etName.getText().toString().trim();
        String desc = etDescription.getText().toString().trim();
        String price = etPrice.getText().toString().trim();
        String emoji = etEmoji.getText().toString().trim();
        String category = etCategory.getText().toString().trim();

        if (name.isEmpty() || price.isEmpty()) {
            Toast.makeText(this, "Vui l\u00F2ng nh\u1EADp t\u00EAn v\u00E0 gi\u00E1", Toast.LENGTH_SHORT).show();
            return;
        }

        Drink drink = new Drink(name, desc, price, emoji, category);

        if (drinkId != null) {
            // Update existing
            drink.setId(drinkId);
            drinksRef.child(drinkId).setValue(drink)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "C\u1EADp nh\u1EADt th\u00E0nh c\u00F4ng!", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "L\u1ED7i: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            // Create new
            String newId = drinksRef.push().getKey();
            drink.setId(newId);
            drinksRef.child(newId).setValue(drink)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Th\u00EAm th\u00E0nh c\u00F4ng!", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "L\u1ED7i: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }
}
