package com.mikesamdi.lonedree;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyRvAdapter extends RecyclerView.Adapter<MyRvAdapter.MyHolder> {
    ArrayList<GroceryItem> data;

    public MyRvAdapter(ArrayList<GroceryItem> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery_rv, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        GroceryItem currentItem = data.get(position);
        holder.groceryTitle.setText(currentItem.getTitle());
        holder.groceryLife.setText(currentItem.getLife());
        holder.groceryPrice.setText(String.valueOf(currentItem.getPrice())); // Convert int to String
        Glide.with(holder.itemView.getContext())
                .load(currentItem.getImageURL())
                .into(holder.groceryImageView);

        holder.addToCartBtn.setOnClickListener(v -> {
            DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference().child("cartItems");

            // Check if the item already exists in the cart
            cartRef.orderByChild("title").equalTo(currentItem.getTitle()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Item exists in the cart, update the count
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            int currentCount = snapshot.child("count").getValue(Integer.class);
                            snapshot.getRef().child("count").setValue(currentCount + 1);

                            // Increment the total price
                            int currentPrice = snapshot.child("price").getValue(Integer.class);
                            snapshot.getRef().child("price").setValue(currentPrice + currentItem.getPrice());

                            // Inform the user that the count has been incremented (Optional)
                            Toast.makeText(holder.itemView.getContext(), "Item count incremented", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Item does not exist in the cart, add it with count 1 and initial price
                        currentItem.setCount(1);
                        cartRef.push().setValue(currentItem);

                        // Inform the user that the item has been added to the cart (Optional)
                        Toast.makeText(holder.itemView.getContext(), "Item added to cart", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle error
                }
            });
        });
    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView groceryTitle;
        TextView groceryLife;
        TextView groceryPrice;
        ImageView groceryImageView;
        Button addToCartBtn; // Reference to the button


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            groceryTitle = itemView.findViewById(R.id.groceryTitleMainActivity);
            groceryLife = itemView.findViewById(R.id.groceryAmountMainActivity);
            groceryPrice = itemView.findViewById(R.id.groceryPriceMainActivity);
            groceryImageView = itemView.findViewById(R.id.groceryImageMainActivity);
            addToCartBtn = itemView.findViewById(R.id.addToCartBtn); // Initialize the button
        }
    }
}
