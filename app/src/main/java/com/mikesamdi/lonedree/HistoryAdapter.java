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

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<HistoryItem> historyItemList;

    public HistoryAdapter(List<HistoryItem> historyItemList) {
        this.historyItemList = historyItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_rv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryItem item = historyItemList.get(position);

        // Bind data to the ViewHolder's views here
        holder.titleTextView.setText(item.getTitle());
        holder.countTextView.setText(String.valueOf(item.getCount()));
        holder.lifeTextView.setText(item.getLife());
        holder.priceTextView.setText(String.valueOf(item.getPrice()));

        // Load image using Glide into ImageView
        Glide.with(holder.itemView.getContext())
                .load(item.getImageURL())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return historyItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        TextView countTextView;
        TextView lifeTextView;
        TextView priceTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.historyImageView);
            titleTextView = itemView.findViewById(R.id.historyItemName);
            countTextView = itemView.findViewById(R.id.historyItemCount);
            lifeTextView = itemView.findViewById(R.id.historyItemLife);
            priceTextView = itemView.findViewById(R.id.historyItemPrice);
        }
    }
}

