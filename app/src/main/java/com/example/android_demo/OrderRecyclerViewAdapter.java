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

import java.util.ArrayList;
import java.util.List;

public class OrderRecyclerViewAdapter extends RecyclerView.Adapter<OrderRecyclerViewAdapter.MyViewHolder> {

    Context context;
    List<Order> orders;

    public OrderRecyclerViewAdapter(Context context, List<Order> orders){
        this.context = context;
        this.orders = orders;
    }

    public List<Order> getOrders(){
        return orders;
    }

    @NonNull
    @Override
    public OrderRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout (giving look to rows)
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_order, parent, false);
        return new OrderRecyclerViewAdapter.MyViewHolder(view,orders);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderRecyclerViewAdapter.MyViewHolder holder, int position) {
        //assigns values to the views based on the position
        holder.orderItemName.setText(orders.get(position).getItemName());
        holder.orderItemPrice.setText(String.valueOf(orders.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        //number of items
        return orders.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView orderItemName;
        TextView orderItemPrice;
        TextView orderItemInfo;
        Button removeBtn;
        public MyViewHolder(@NonNull View itemView, List<Order> orders) {
            super(itemView);
            orderItemName = itemView.findViewById(R.id.orderItemName);
            orderItemPrice = itemView.findViewById(R.id.orderItemPrice);
            orderItemInfo = itemView.findViewById(R.id.orderItemInfo);
            removeBtn = itemView.findViewById(R.id.removeBtn);

            removeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Get the position of the clicked item
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // Remove the item from the list
                        orders.remove(position);
                        // Notify the adapter that an item has been removed
                        notifyItemRemoved(position);
                    }
                }
            });
        }
    }
}
