package com.example.android_demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.android_demo.models.Customer;
import com.example.android_demo.models.Dish;
import com.example.android_demo.models.Order;

import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "MY_DATABASE.db";
    private static final int DB_VERSION = 1;

    private static final String CUSTOMER_TABLE_NAME = "CUSTOMERS";
    private static final String MENU_TABLE_NAME = "MENU";
    private static final String ORDER_TABLE_NAME = "ORDERS";
    private static final String CUSTOMER_NAME = "name";
    private static final String CUSTOMER_PHONE = "phone";
    private static final String CUSTOMER_ADDRESS = "address";
    private static final String MENU_NAME = "name";
    private static final String MENU_PRICE = "price";
    private static final String MENU_INFO = "info";
    private static final String ORDER_ITEM = "item";
    private static final String ORDER_CUSTOMER_ID = "customer_id";
    private static final String ORDER_PRICE = "price";
    private static final String ORDER_START = "confirmed_at";
    private static final String ORDER_DUE = "due_time";
    private static final String ID = "id";




    public DataBaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE "+ CUSTOMER_TABLE_NAME + " (" +
                        CUSTOMER_PHONE + " TEXT PRIMARY KEY, " +
                        CUSTOMER_NAME + " TEXT, " +
                        CUSTOMER_ADDRESS + " TEXT);";
        db.execSQL(query);

        query =
                "CREATE TABLE "+ MENU_TABLE_NAME + " (" +
                        ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        MENU_NAME + " TEXT, " +
                        MENU_PRICE + " INTEGER, " +
                        MENU_INFO + " TEXT);";

        db.execSQL(query);

        query =
                "CREATE TABLE "+ ORDER_TABLE_NAME + " (" +
                        ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        ORDER_ITEM + " TEXT, " +
                        ORDER_CUSTOMER_ID + " TEXT, " +
                        ORDER_PRICE + " INTEGER, " +
                        ORDER_START + " TEXT, " +
                        ORDER_DUE + " TEXT, " +
                        "FOREIGN KEY(" + ORDER_CUSTOMER_ID + ") REFERENCES " + CUSTOMER_TABLE_NAME + "(" + CUSTOMER_PHONE + ")" +
                        ");";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //gets called when database version changes

    }

    public boolean addCustomer(Customer customer)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(CUSTOMER_PHONE,customer.getPhoneNumber());
        cv.put(CUSTOMER_NAME,customer.getName());
        cv.put(CUSTOMER_ADDRESS,customer.getAddress());

        long result = db.insert(CUSTOMER_TABLE_NAME, null, cv);
        return result > -1;

    }

    public Cursor getDishes()
    {
        String query = "SELECT * FROM " + MENU_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null)
        {
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public Cursor getCustomerByPhone(String phone)
    {

        String query = "SELECT * FROM " + CUSTOMER_TABLE_NAME + " WHERE " + CUSTOMER_PHONE+ " = "+phone;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null)
        {
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public boolean addMenuItems(List<Dish> dishes)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        for(Dish dish: dishes){
            cv.put(MENU_NAME,dish.getName());
            cv.put(MENU_PRICE,dish.getPrice());
            cv.put(MENU_INFO,dish.getInfo());
            long result = db.insert(MENU_TABLE_NAME, null, cv);
            if(result < 0) return false;
        }
        return true;
    }

    public boolean addOrderItems(List<Order> orders)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        for(Order order: orders){
            cv.put(ORDER_ITEM,order.getItemName());
            cv.put(ORDER_CUSTOMER_ID,order.getCustomerPhone());
            cv.put(ORDER_PRICE,order.getPrice());
            cv.put(ORDER_START,order.getTimeOfOrder());
            cv.put(ORDER_DUE,order.getDueTime());
            long result = db.insert(ORDER_TABLE_NAME, null, cv);
            if(result < 0) return false;
        }
        return true;
    }

    public boolean updateCustomer(Customer customer)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CUSTOMER_NAME, customer.getName());
        cv.put(CUSTOMER_ADDRESS, customer.getAddress());
        long result = db.update(CUSTOMER_TABLE_NAME,cv,CUSTOMER_PHONE+"=?",new String[]{customer.getPhoneNumber()});
        return result > -1;
    }

}
