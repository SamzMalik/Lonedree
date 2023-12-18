package com.mikesamdi.lonedree;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder> {

    private List<CartItem> cartItemList;

    public CartItemAdapter(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_rv, parent, false);
        return new CartItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {
        CartItem cartItem = cartItemList.get(position);
        holder.itemNameTextView.setText(cartItem.getTitle());
        holder.itemPriceTextView.setText(String.valueOf(cartItem.getPrice()));
        holder.itemLifeTextView.setText(String.valueOf(cartItem.getLife()));
        holder.itemCountTextView.setText(String.valueOf(cartItem.getCount()));
        Glide.with(holder.itemView.getContext())
                .load(cartItem.getImageURL())
                .into(holder.itemImageView); // Assuming you've named the ImageView as itemImageView
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public static class CartItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemNameTextView;
        TextView itemPriceTextView;
        TextView itemLifeTextView; // TextView for item life
        TextView itemCountTextView; // TextView for item count
        ImageView itemImageView;

        // Add other TextViews or views for new fields

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.cartItemName);
            itemPriceTextView = itemView.findViewById(R.id.cartItemPrice);
            itemLifeTextView = itemView.findViewById(R.id.cartItemLife); // Replace with your actual TextView ID
            itemCountTextView = itemView.findViewById(R.id.cartItemCount); // Replace with your actual TextView ID
            itemImageView = itemView.findViewById(R.id.cartImageView);
        }
    }
}
