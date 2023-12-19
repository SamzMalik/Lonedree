package com.mikesamdi.lonedree;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private HistoryAdapter historyAdapter;
    private List<HistoryItem> historyItemList;
    private DatabaseReference databaseReference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize Firebase Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("historyItems");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = view.findViewById(R.id.historyRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        historyItemList = new ArrayList<>();
        historyAdapter = new HistoryAdapter(historyItemList);
        recyclerView.setAdapter(historyAdapter);

        // Fetch data from Firebase
        fetchHistoryItems();

        return view;
    }

    private void fetchHistoryItems() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                historyItemList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    int count = snapshot.child("count").getValue(Integer.class);
                    String imageURL = snapshot.child("imageURL").getValue(String.class);
                    String life = snapshot.child("life").getValue(String.class);
                    String title = snapshot.child("title").getValue(String.class);
                    double price = snapshot.child("price").getValue(Double.class);

                    HistoryItem historyItem = new HistoryItem(title, imageURL, life, price, count);
                    historyItemList.add(historyItem);
                }
                historyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

}
