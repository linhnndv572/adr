package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DrinkAdapter.OnDrinkClickListener {

    private RecyclerView rvDrinks;
    private DrinkAdapter adapter;
    private DatabaseReference drinksRef;
    private List<Drink> allDrinks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drinksRef = FirebaseDatabase.getInstance().getReference("drinks");

        rvDrinks = findViewById(R.id.rv_drinks);
        rvDrinks.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DrinkAdapter(this);
        rvDrinks.setAdapter(adapter);

        // Add drink button
        Button btnAdd = findViewById(R.id.btn_add_drink);
        btnAdd.setOnClickListener(v -> {
            startActivity(new Intent(this, AddEditDrinkActivity.class));
        });

        // Logout button
        findViewById(R.id.btn_logout).setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        // Search
        EditText etSearch = findViewById(R.id.et_search);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterDrinks(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Load drinks from Firebase
        loadDrinks();
    }

    private void loadDrinks() {
        drinksRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allDrinks.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Drink drink = ds.getValue(Drink.class);
                    if (drink != null) {
                        drink.setId(ds.getKey());
                        allDrinks.add(drink);
                    }
                }
                adapter.setDrinkList(allDrinks);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this,
                        "L\u1ED7i t\u1EA3i d\u1EEF li\u1EC7u: " + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterDrinks(String query) {
        if (query.isEmpty()) {
            adapter.setDrinkList(allDrinks);
            return;
        }
        List<Drink> filtered = new ArrayList<>();
        for (Drink d : allDrinks) {
            if (d.getName().toLowerCase().contains(query.toLowerCase()) ||
                    d.getCategory().toLowerCase().contains(query.toLowerCase())) {
                filtered.add(d);
            }
        }
        adapter.setDrinkList(filtered);
    }

    @Override
    public void onEditClick(Drink drink) {
        Intent intent = new Intent(this, AddEditDrinkActivity.class);
        intent.putExtra("drink_id", drink.getId());
        intent.putExtra("drink_name", drink.getName());
        intent.putExtra("drink_desc", drink.getDescription());
        intent.putExtra("drink_price", drink.getPrice());
        intent.putExtra("drink_emoji", drink.getEmoji());
        intent.putExtra("drink_category", drink.getCategory());
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(Drink drink) {
        new AlertDialog.Builder(this)
                .setTitle("X\u00E1c nh\u1EADn x\u00F3a")
                .setMessage("B\u1EA1n c\u00F3 ch\u1EAFc mu\u1ED1n x\u00F3a \"" + drink.getName() + "\"?")
                .setPositiveButton("X\u00F3a", (dialog, which) -> {
                    drinksRef.child(drink.getId()).removeValue()
                            .addOnSuccessListener(aVoid ->
                                    Toast.makeText(this, "\u0110\u00E3 x\u00F3a!", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e ->
                                    Toast.makeText(this, "L\u1ED7i: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                })
                .setNegativeButton("H\u1EE7y", null)
                .show();
    }
}
