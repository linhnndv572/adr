package com.example.myfirstapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder> {

    private List<Drink> drinkList = new ArrayList<>();
    private OnDrinkClickListener listener;

    public interface OnDrinkClickListener {
        void onEditClick(Drink drink);
        void onDeleteClick(Drink drink);
    }

    public DrinkAdapter(OnDrinkClickListener listener) {
        this.listener = listener;
    }

    public void setDrinkList(List<Drink> drinks) {
        this.drinkList = drinks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_drink, parent, false);
        return new DrinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkViewHolder holder, int position) {
        Drink drink = drinkList.get(position);
        holder.tvEmoji.setText(drink.getEmoji());
        holder.tvName.setText(drink.getName());
        holder.tvDescription.setText(drink.getDescription());
        holder.tvPrice.setText(drink.getPrice());
        holder.tvCategory.setText(drink.getCategory());

        holder.btnEdit.setOnClickListener(v -> listener.onEditClick(drink));
        holder.btnDelete.setOnClickListener(v -> listener.onDeleteClick(drink));
    }

    @Override
    public int getItemCount() {
        return drinkList.size();
    }

    static class DrinkViewHolder extends RecyclerView.ViewHolder {
        TextView tvEmoji, tvName, tvDescription, tvPrice, tvCategory, btnEdit, btnDelete;

        public DrinkViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmoji = itemView.findViewById(R.id.tv_emoji);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvCategory = itemView.findViewById(R.id.tv_category);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
