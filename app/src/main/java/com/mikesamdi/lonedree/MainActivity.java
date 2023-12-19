package com.mikesamdi.lonedree;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikesamdi.lonedree.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    RecyclerView rv;
    RecyclerView vegetableRV;
    ArrayList<GroceryItem> dataSource;
    LinearLayoutManager linearLayoutManager;
    MyRvAdapter myRvAdapter;
    // After setContentView

    ActivityMainBinding binding;



    private VegetableAdapter adapter;
    private List<VegetableItem> vegetableItemList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                replaceFragment(new HomeFragment());
                Log.d("BottomNav", "Home selected" + item.getItemId() + "==" + R.id.home);
                return true;
            } else if (item.getItemId() == R.id.cart) {
                Log.d("BottomNav", "Cart selected" + item.getItemId() + "==" + R.id.cart);
                replaceFragment(new CartFragment());
                return true;
            } else if (item.getItemId() == R.id.history) {
                Log.d("BottomNav", "History selected" + item.getItemId() + "==" + R.id.history);
                replaceFragment(new HistoryFragment());
                return true;
            }
            return false;
        });
        FirebaseApp.initializeApp(this);
        rv = findViewById(R.id.groceryContainerRV);
        vegetableRV =  findViewById(R.id.allVegetablesRVMainActivity);


        // Setting the data source
        dataSource = new ArrayList<>();
        //dataSource.add(new GroceryItem("Banana", "10 Days", "$4.99", "https://food-ubc.b-cdn.net/wp-content/uploads/2020/02/Save-Money-On-Groceries_UBC-Food-Services.jpg"));

        linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        myRvAdapter = new MyRvAdapter(dataSource);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(myRvAdapter);
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("groceryItems");
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSource.clear(); // Clear existing data

                // Loop through dataSnapshot to retrieve each GroceryItem
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Retrieve data and create GroceryItem objects
                    String title = snapshot.child("title").getValue(String.class);
                    String life = snapshot.child("life").getValue(String.class);
                    int price = snapshot.child("price").getValue(int.class);
                    String imageURL = snapshot.child("imageURL").getValue(String.class);

                    // Create GroceryItem object and add to dataSource
                    dataSource.add(new GroceryItem(title, life, price, imageURL));
                }

                // Notify the adapter about the data change
                myRvAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
            }


        });

        vegetableRV.setLayoutManager(new LinearLayoutManager(this));
        vegetableItemList = new ArrayList<>();
        adapter = new VegetableAdapter(vegetableItemList);
        vegetableRV.setAdapter(adapter);
        DatabaseReference vegetablesDatabase = FirebaseDatabase.getInstance().getReference().child("allVegetables");
        fetchData(vegetablesDatabase);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    //fetchVegetable

    private void fetchData(DatabaseReference vegetablesDatabase) {
        // Fetch data from Firebase Realtime Database for vegetables
        vegetablesDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                vegetableItemList.clear(); // Clear existing data

                // Loop through dataSnapshot to retrieve each VegetableItem
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Retrieve data and create VegetableItem objects
                    String name = snapshot.child("title").getValue(String.class);
                    String life = snapshot.child("life").getValue(String.class);
                    double price = snapshot.child("price").getValue(Double.class);
                    String imageURL = snapshot.child("imageURL").getValue(String.class);

                    // Create VegetableItem object and add to vegetableItemList
                    vegetableItemList.add(new VegetableItem(name, life, price, imageURL));
                }

                // Notify the adapter about the data change
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle potential errors here
            }
        });
    }


}
