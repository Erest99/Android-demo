package com.example.android_demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android_demo.models.Customer;
import com.example.android_demo.models.Order;

import java.util.ArrayList;
import java.util.List;

public class ChangeAddressActivity extends AppCompatActivity {

    Customer customer;
    Customer old;
    List<Order> orders;

    EditText nameEditText,addressEditText, cityEditText;
    Button backBtn, confirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_changeaddress);

        orders = getIntent().getParcelableArrayListExtra("orders");
        customer = (Customer) getIntent().getParcelableExtra("customer");
        old = customer;
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);

        nameEditText = findViewById(R.id.nameEditText2);
        addressEditText = findViewById(R.id.addressEditText2);
        cityEditText = findViewById(R.id.cityEditText2);
        backBtn = findViewById(R.id.backBtn2);
        confirmBtn = findViewById(R.id.confirmBtn2);

        nameEditText.setText(customer.getName());
        addressEditText.setText(customer.getAddress().split("/")[0]);
        cityEditText.setText(customer.getAddress().split("/")[1]);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangeAddressActivity.this, MenuActivity.class);
                intent.putExtra("customer", old);
                intent.putParcelableArrayListExtra("orders", new ArrayList<>(orders));
                startActivity(intent);
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customer.setName(nameEditText.getText().toString());
                customer.setAddress(addressEditText.getText().toString()+"/"+cityEditText.getText().toString());
                boolean success = dataBaseHelper.updateCustomer(customer);
                if(success){
                    Log.i("db operation", "Customer successfully updated");
                    Intent intent = new Intent(ChangeAddressActivity.this, MenuActivity.class);
                    intent.putExtra("customer", customer);
                    intent.putParcelableArrayListExtra("orders", new ArrayList<>(orders));
                    startActivity(intent);
                }else{
                    Log.e("db operation", "Couldnt update the customer");
                }
            }
        });
    }
}