package com.example.android_demo;

import static android.view.View.VISIBLE;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android_demo.models.Customer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button loginBtn;
    ImageView logoView;
    TextView nameTextView;
    EditText phoneEditText,nameEditText, cityEditText, addressEditText;
    boolean register = false;

    DataBaseHelper dataBaseHelper = new DataBaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loginBtn = findViewById(R.id.loginButton);
        logoView = findViewById(R.id.logoImageView);
        nameTextView = findViewById(R.id.nameTextView);
        phoneEditText = findViewById(R.id.phoneEditText);
        nameEditText = findViewById(R.id.nameEditText);
        cityEditText = findViewById(R.id.cityEditText);
        addressEditText = findViewById(R.id.addressEditText);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = phoneEditText.getText().toString();
                if(phone != null && phone.matches("\\d{9}")){
                    if(!register) {
                        Customer customer = getCustomerByPhone(phone);
                        if (customer.getPhoneNumber() != null) {
                            Log.i("customer details", customer.toString());
                            switchPage(customer);
                        } else {
                            loginBtn.setText("Register");
                            nameEditText.setVisibility(VISIBLE);
                            addressEditText.setVisibility(VISIBLE);
                            cityEditText.setVisibility(VISIBLE);
                            register = true;
                        }
                    }else {
                        String name = nameEditText.getText().toString();
                        String address = addressEditText.getText().toString();
                        String city = cityEditText.getText().toString();
                        if(name.length() > 2 && address.length() > 4 && city.length() > 2){
                            Customer customer = new Customer(phone,name,address + "/" + city);
                            boolean success = addCutomer(customer);
                            if(success)Log.i("db operation","Customer " + customer + " saved successfully");
                            else Log.e("db error","failed to insert customer " + customer + " into database");
                            switchPage(customer);

                        }else{
                            Toast.makeText(MainActivity.this, "Invalid input data.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Please insert your phone number in correct format.", Toast.LENGTH_SHORT).show();
                    phoneEditText.setText("");
                }
            }
        });

    }

    void switchPage(Customer customer){
        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
        intent.putExtra("customer", customer);
        startActivity(intent);
    }
    List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<>();
        Cursor cursor = dataBaseHelper.getCustomers();
        if (cursor.getCount() == 0) {
            Log.w("db operation", "no data to display");
        } else {
            while (cursor.moveToNext()) {
                Customer customer = new Customer(cursor.getString(0), cursor.getString(1), cursor.getString(2));
                customers.add(customer);
            }
        }
        return customers;
    }

    boolean addCutomer(Customer customer){
        return dataBaseHelper.addCustomer(customer);
    }

    Customer getCustomerByPhone(String phone) {
        Customer customer = new Customer();
        Cursor cursor = dataBaseHelper.getCustomerByPhone(phone);
        if (cursor.getCount() != 0 && cursor.moveToNext()) {
            customer = new Customer(cursor.getString(0), cursor.getString(1), cursor.getString(2));
        } else {
            Log.w("db operation","Customer with phone: " + phone + " not found.");
        }
        return customer;
    }

}