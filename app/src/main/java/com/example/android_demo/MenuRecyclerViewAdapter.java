package com.example.android_demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_demo.models.Customer;
import com.example.android_demo.models.Dish;
import com.example.android_demo.models.Order;

import java.util.List;

public class MenuRecyclerViewAdapter extends RecyclerView.Adapter<MenuRecyclerViewAdapter.MyViewHolder> {

    Context context;
    List<Dish> dishes;
    List<Order> orders;
    Customer customer;

    public MenuRecyclerViewAdapter(Context context, List<Dish> dishes, Customer customer, List<Order> orders){
        this.context = context;
        this.dishes = dishes;
        this.customer = customer;
        this.orders = orders;
    }

    public List<Order> getOrders(){
        return orders;
    }

    @NonNull
    @Override
    public MenuRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout (giving look to rows)
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_menu, parent, false);
        return new MenuRecyclerViewAdapter.MyViewHolder(view,customer,orders);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuRecyclerViewAdapter.MyViewHolder holder, int position) {
        //assigns values to the views based on the position
        holder.menuItemName.setText(dishes.get(position).getName());
        holder.menuItemPrice.setText(String.valueOf(dishes.get(position).getPrice()));
        holder.menuItemInfo.setText(dishes.get(position).getInfo());
    }

    @Override
    public int getItemCount() {
        //number of items
        return dishes.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView menuItemName;
        TextView menuItemPrice;
        TextView menuItemInfo;
        Button addToCartBtn;
        public MyViewHolder(@NonNull View itemView, Customer customer, List<Order> orders) {
            super(itemView);
            menuItemName = itemView.findViewById(R.id.orderItemName);
            menuItemPrice = itemView.findViewById(R.id.orderItemPrice);
            menuItemInfo = itemView.findViewById(R.id.orderItemInfo);
            addToCartBtn = itemView.findViewById(R.id.addToCartButton);

            addToCartBtn.setOnClickListener(view -> {
                Order o = new Order(menuItemName.getText().toString(),customer.getPhoneNumber(),Integer.parseInt(menuItemPrice.getText().toString()));
                orders.add(o);
            });
        }
    }
}
