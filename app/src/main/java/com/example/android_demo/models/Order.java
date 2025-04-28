package com.example.android_demo.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

public class Order implements Parcelable {

    private int id;
    private String itemName;
    private String customerPhone;
    private int price;
    private String timeOfOrder;
    private String dueTime;

    public Order() {
    }

    public Order(String itemName, String customerPhone, int price) {
        this.itemName = itemName;
        this.customerPhone = customerPhone;
        this.price = price;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        this.timeOfOrder = sdf.format(new Date());
        this.dueTime = sdf.format(new Date());

    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getDueTime() {
        return dueTime;
    }

    public String getTimeOfOrder() {
        return timeOfOrder;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", customerPhone='" + customerPhone + '\'' +
                ", timeOfOrder='" + timeOfOrder + '\'' +
                ", dueTime='" + dueTime + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(itemName);
        parcel.writeString(customerPhone);
        parcel.writeInt(price);
        parcel.writeString(timeOfOrder);
        parcel.writeString(dueTime);
    }

    // Creator field to regenerate the object from a Parcel
    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            Order order = new Order();
            order.id = in.readInt();
            order.itemName = in.readString();
            order.customerPhone = in.readString();
            order.price = in.readInt();
            order.timeOfOrder = in.readString();
            order.dueTime = in.readString();
            return order;
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
}
