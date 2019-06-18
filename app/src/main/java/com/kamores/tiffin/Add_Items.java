package com.kamores.tiffin;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Add_Items extends AppCompatActivity {
    ImageView itemImage;
    EditText itemName, itemPrice,itemDescription;
    Spinner spinnerDays;
    Button btnChooseImage, btnAddItem;
    List<String> listDays;
    String spDays;
    String name,price,description;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__items);

        initViewItems();

        btnChooseImage.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        btnAddItem.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name= itemName.getText().toString();
                price= itemPrice.getText().toString();
                description= itemDescription.getText().toString();
                if(name.isEmpty()){
                    itemName.setError("Enter Name");
                }
                else if (price.isEmpty()){
                    itemPrice.setError("Enter Service");
                }
                else if(description.isEmpty()){
                    itemDescription.setError("Enter Contact");

                }
//
                else {
                    Intent intent = new Intent(Add_Items.this, BaseActivity.class);
                    startActivity(intent);
                }


                }
        });

        showDays();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,listDays);
        spinnerDays.setAdapter(adapter);
        spinnerDays.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spDays = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void initViewItems(){
        itemImage= findViewById(R.id.img_New_Items);
        itemName= findViewById(R.id.et_ItemName);
        itemPrice= findViewById(R.id.et_ItemPrice);
        itemDescription= findViewById(R.id.et_ItemDescription);
        spinnerDays= findViewById(R.id.spinnerDays);
        btnChooseImage= findViewById(R.id.btn_choose_items);
        btnAddItem=findViewById(R.id.btn_Add_Items);
    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    public void showDays() {
        listDays = new ArrayList<>();
        listDays.add("Monday");
        listDays.add("Tuesday");
        listDays.add("Wednesday");
        listDays.add("Thursday");
        listDays.add("Friday");
        listDays.add("Saturday");
        listDays.add("Sunday");
    }
}