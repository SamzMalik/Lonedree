package com.mikesamdi.lonedree;// Inside CartFragment.java

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikesamdi.lonedree.CartItem;
import com.mikesamdi.lonedree.CartItemAdapter;
import com.mikesamdi.lonedree.R;

import java.util.ArrayList;
import java.util.List;

// ...

public class CartFragment extends Fragment {

    private RecyclerView recyclerView;
    private CartItemAdapter cartItemAdapter;
    private List<CartItem> cartItemList;
    private DatabaseReference databaseReference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize Firebase Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("cartItems"); // Replace with your Firebase reference
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerView = view.findViewById(R.id.cartRV); // Replace with your RecyclerView ID
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        cartItemList = new ArrayList<>();
        cartItemAdapter = new CartItemAdapter(cartItemList);
        recyclerView.setAdapter(cartItemAdapter);

        // Fetch data from Firebase
        fetchCartItems();

        return view;
    }

    private void fetchCartItems() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cartItemList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    int count = snapshot.child("count").getValue(Integer.class);
                    String imageURL = snapshot.child("imageURL").getValue(String.class);
                    String life = snapshot.child("life").getValue(String.class);
                    double price = snapshot.child("price").getValue(Double.class);
                    String title = snapshot.child("title").getValue(String.class);

                    CartItem cartItem = new CartItem(count, imageURL, life, price, title);
                    cartItemList.add(cartItem);
                }
                cartItemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }
}
