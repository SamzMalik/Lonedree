package com.mikesamdi.lonedree;

import android.util.Log;
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

import java.util.List;

public class VegetableAdapter extends RecyclerView.Adapter<VegetableAdapter.ViewHolder> {
    private List<VegetableItem> vegetableItemList;

    public VegetableAdapter(List<VegetableItem> vegetableItemList) {
        this.vegetableItemList = vegetableItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vegetable_item_rv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VegetableItem item = vegetableItemList.get(position);
        holder.vegetableName.setText(item.getName());
        holder.vegetableLife.setText("Storage life: " + item.getLife());
        holder.vegetablePrice.setText("$" + String.valueOf(item.getPrice()));

        Glide.with(holder.itemView.getContext())
                .load(item.getImageURL())
                .into(holder.vegetableImage);

        holder.addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = Integer.parseInt(holder.quantityTextView.getText().toString());

                DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference().child("cartItems");

                cartRef.orderByChild("title").equalTo(item.getName()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                int currentCount = snapshot.child("count").getValue(Integer.class);
                                int newCount = currentCount + currentQuantity;
                                double updatedPrice = item.getPrice() * newCount; // Update price based on new count
                                Log.d("UpdatedPrice", "Updated price: " + updatedPrice); // Log the updated price

                                snapshot.getRef().child("count").setValue(newCount);
                                snapshot.getRef().child("price").setValue(updatedPrice);

                                Toast.makeText(holder.itemView.getContext(), "Item count updated", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            double totalPrice = item.getPrice() * currentQuantity; // Calculate the total price for the new item

                            CartItem cartItem = new CartItem(currentQuantity, item.getImageURL(), item.getLife(), (item.getPrice() * currentQuantity), item.getName());
                            cartRef.push().setValue(cartItem);

                            Toast.makeText(holder.itemView.getContext(), "Item added to cart", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle error
                    }
                });
            }
        });

        holder.incrementQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = Integer.parseInt(holder.quantityTextView.getText().toString());
                currentQuantity++;
                holder.quantityTextView.setText(String.valueOf(currentQuantity));
            }
        });

        holder.decrementQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = Integer.parseInt(holder.quantityTextView.getText().toString());
                if (currentQuantity > 0) {
                    currentQuantity--;
                    holder.quantityTextView.setText(String.valueOf(currentQuantity));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return vegetableItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView vegetableName;
        TextView vegetableLife;
        TextView vegetablePrice;
        ImageView vegetableImage;
        Button addToCartBtn;
        Button incrementQuantity;
        Button decrementQuantity;
        TextView quantityTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            vegetableName = itemView.findViewById(R.id.vegetableNameMainActivity);
            vegetableLife = itemView.findViewById(R.id.vegetableLifeMainActivity);
            vegetablePrice = itemView.findViewById(R.id.vegetable_priceMainActivity);
            vegetableImage = itemView.findViewById(R.id.vegetableIVMainActivity);
            addToCartBtn = itemView.findViewById(R.id.addToCartVegetable);
            incrementQuantity = itemView.findViewById(R.id.incrementQuantity);
            decrementQuantity = itemView.findViewById(R.id.decrementQuantity);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
        }
    }
}
