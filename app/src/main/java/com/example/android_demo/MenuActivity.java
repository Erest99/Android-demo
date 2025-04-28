package com.example.android_demo;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_demo.models.Customer;
import com.example.android_demo.models.Dish;
import com.example.android_demo.models.Order;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    Button changeAddressBtn;
    Button confirmOrderBtn;
    RecyclerView recyclerView;
    Customer customer;
    DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
    List<Dish> dishes = new ArrayList<>();
    List<Order> orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);

//        dataBaseHelper.addMenuItems(testDishes());
        orders = getIntent().getParcelableArrayListExtra("orders");
        if(orders != null)Log.i("Intent pass",orders.toString());
        else orders = new ArrayList<>();
        customer = getIntent().getParcelableExtra("customer");
        if(customer == null)throw new RuntimeException("Couldnt retrieve customer");
        changeAddressBtn = findViewById(R.id.changeAddressButton);
        confirmOrderBtn = findViewById(R.id.confirmOrderButton);
        recyclerView = findViewById(R.id.recyclerView);
        getDishes();
        MenuRecyclerViewAdapter adapter = new MenuRecyclerViewAdapter(this,dishes,customer,orders);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        confirmOrderBtn.setOnClickListener(view -> {
            orders = adapter.getOrders();
            Intent intent = new Intent(MenuActivity.this, CartActivity.class);
            intent.putParcelableArrayListExtra("orders", new ArrayList<>(orders));
            intent.putExtra("customer", customer);
            startActivity(intent);
        });

        changeAddressBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MenuActivity.this, ChangeAddressActivity.class);
            intent.putExtra("customer", customer);
            intent.putParcelableArrayListExtra("orders", new ArrayList<>(orders));
            startActivity(intent);
        });
    }

    void getDishes() {
        Cursor cursor = dataBaseHelper.getDishes();
        if (cursor.getCount() == 0) {
            Log.w("db operation", "no menu items to display");
        } else {
            while (cursor.moveToNext()) {
                Dish dish = new Dish(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3));
                dishes.add(dish);
            }
        }
    }

//    List<Dish> testDishes(){
//        List<Dish> d = new ArrayList<>();
//        Dish dish = new Dish(0,"dish1",10,"very nutritious");
//        d.add(dish);
//        dish = new Dish(0,"dish2",15,"very nutritious");
//        d.add(dish);
//        dish = new Dish(0,"dish3",20,"very nutritious");
//        d.add(dish);
//        dish = new Dish(0,"dish4",50,"very nutritious");
//        d.add(dish);
//        return d;
//    }
}