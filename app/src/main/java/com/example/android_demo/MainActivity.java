package com.example.android_demo;

import static android.view.View.VISIBLE;

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

public class MainActivity extends AppCompatActivity {

    Button loginBtn;
    ImageView logoView;
    TextView nameTextView;
    EditText phoneEditText,nameEditText, cityEditText, addressEditText;

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
                    //TODO check if exists in customer database
                    boolean inDb = false;
                    if(inDb){
                        String name = "";
                        String address = "";
                        String city = "";
                        Customer customer = new Customer(phone, name,address + "/" + city);
                        //TODO change activity to menu and pass customer details

                    }else{
                        //TODO if not then switch to register mode (check if name and address are valid format)
                        nameEditText.setVisibility(VISIBLE);
                        addressEditText.setVisibility(VISIBLE);
                        cityEditText.setVisibility(VISIBLE);
                        String name = nameEditText.getText().toString();
                        String address = addressEditText.getText().toString();
                        String city = cityEditText.getText().toString();
                        if(name.length() > 2 && address.length() > 4 && city.length() > 2){
                            boolean success = dataBaseHelper.addCustomer(new Customer(phone,name,address + "/" + city));
                            if(success)Log.i("db operation","Customer saved successfully");
                            else Log.e("db error","failed to insert customer into database");
                            //TODO change activity to menu and pass customer details
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
}