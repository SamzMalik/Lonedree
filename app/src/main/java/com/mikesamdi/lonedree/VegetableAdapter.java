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
        holder.vegetableLife.setText(item.getLife());
        holder.vegetablePrice.setText(String.valueOf(item.getPrice()));

        // Load image using Glide into your ImageView
        Glide.with(holder.itemView.getContext())
                .load(item.getImageURL())
                .into(holder.vegetableImage);
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            vegetableName = itemView.findViewById(R.id.vegetableNameMainActivity);
            vegetableLife = itemView.findViewById(R.id.vegetableLifeMainActivity);
            vegetablePrice = itemView.findViewById(R.id.vegetable_priceMainActivity);
            vegetableImage = itemView.findViewById(R.id.vegetableIVMainActivity);
        }
    }
}


