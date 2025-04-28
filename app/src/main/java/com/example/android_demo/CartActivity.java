package com.example.android_demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_demo.models.Customer;
import com.example.android_demo.models.Dish;
import com.example.android_demo.models.Order;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    List<Order> orders;

    Button confirmBtn;
    Button backBtn;
    RecyclerView recyclerView;
    Customer customer;
    DataBaseHelper dataBaseHelper = new DataBaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

        orders = getIntent().getParcelableArrayListExtra("orders");
        customer = (Customer) getIntent().getParcelableExtra("customer");
        if(orders==null)throw new RuntimeException("Error passing orders");
        for (Order o:orders) {
            Log.i("show data",o.toString());
        }
        confirmBtn = findViewById(R.id.confirmBtn);
        backBtn = findViewById(R.id.backBtn);
        recyclerView = findViewById(R.id.orderRecyclerView);
        OrderRecyclerViewAdapter adapter = new OrderRecyclerViewAdapter(this,orders);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Order> orders = adapter.getOrders();
                Intent intent = new Intent(CartActivity.this, MenuActivity.class);
                intent.putParcelableArrayListExtra("orders", new ArrayList<>(orders));
                intent.putExtra("customer", customer);
                startActivity(intent);
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Order> orders = adapter.getOrders();
                boolean success = dataBaseHelper.addOrderItems(orders);
                if(!success)Log.e("database transaction","failed to save data");
                else orders = new ArrayList<>();
                Intent intent = new Intent(CartActivity.this, MenuActivity.class);
                intent.putParcelableArrayListExtra("orders", new ArrayList<>(orders));
                intent.putExtra("customer", customer);
                startActivity(intent);
            }
        });

    }
}