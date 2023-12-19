package com.mikesamdi.lonedree;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private RecyclerView recyclerView;
    private CartItemAdapter cartItemAdapter;
    private List<CartItem> cartItemList;
    private DatabaseReference databaseReference;
    private TextView totalPriceTextView;
    private double totalPrice = 0.0;
    private Button buyButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize Firebase Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("cartItems");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        buyButton = view.findViewById(R.id.cartBuyButton);
        Button clearCartButton = view.findViewById(R.id.clearCartButton);
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveCartItemsToHistory();
                Toast.makeText(requireContext(), "Successfully bought items", Toast.LENGTH_SHORT).show();
            }


        });

        clearCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCartInFirebase();
            }
        });

        recyclerView = view.findViewById(R.id.cartRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        cartItemList = new ArrayList<>();
        cartItemAdapter = new CartItemAdapter(cartItemList);
        recyclerView.setAdapter(cartItemAdapter);
        totalPriceTextView = view.findViewById(R.id.totalPriceCart);

        // Fetch data from Firebase
        fetchCartItems();

        return view;
    }

    private void fetchCartItems() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cartItemList.clear();
                totalPrice = 0.0;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    int count = snapshot.child("count").getValue(Integer.class);
                    String imageURL = snapshot.child("imageURL").getValue(String.class);
                    String life = snapshot.child("life").getValue(String.class);
                    double price = snapshot.child("price").getValue(Double.class);
                    String title = snapshot.child("title").getValue(String.class);

                    CartItem cartItem = new CartItem(count, imageURL, life, price, title);
                    cartItemList.add(cartItem);
                    totalPrice += price;
                }
                cartItemAdapter.notifyDataSetChanged();

                totalPriceTextView.setText(String.format("Total: $%.2f", totalPrice));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private void moveCartItemsToHistory() {
        DatabaseReference historyItemsRef = FirebaseDatabase.getInstance().getReference("historyItems");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    int count = snapshot.child("count").getValue(Integer.class);
                    String imageURL = snapshot.child("imageURL").getValue(String.class);
                    String life = snapshot.child("life").getValue(String.class);
                    double price = snapshot.child("price").getValue(Double.class);
                    String title = snapshot.child("title").getValue(String.class);

                    HistoryItem historyItem = new HistoryItem(title, imageURL, life, price, count);
                    historyItemsRef.push().setValue(historyItem);
                    snapshot.getRef().removeValue(); // Remove the item from cartItems
                }

                cartItemList.clear();
                cartItemAdapter.notifyDataSetChanged();
                totalPrice = 0.0;
                totalPriceTextView.setText("Total: $0.00");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private void clearCartInFirebase() {
        databaseReference.removeValue();
        cartItemList.clear();
        cartItemAdapter.notifyDataSetChanged();
        totalPrice = 0.0;
        totalPriceTextView.setText("Total: $0.00");
    }

    public static class IntroActivity extends AppCompatActivity {


        LottieAnimationView lottie;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_intro);

            lottie = findViewById(R.id.lottie);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                }
            }, 5000);

        }
    }
}
