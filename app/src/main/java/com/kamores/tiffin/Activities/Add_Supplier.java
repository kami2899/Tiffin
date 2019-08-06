package com.kamores.tiffin.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kamores.tiffin.Constants.Constants;
import com.kamores.tiffin.ModelClasses.Suppliers;
import com.kamores.tiffin.R;
import com.kamores.tiffin.Constants.RequestInterfacePart;
import com.kamores.tiffin.Constants.ServerRequest;
import com.kamores.tiffin.Constants.ServerResponce;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Add_Supplier extends AppCompatActivity {
    EditText etName,etService;
   // ImageView supplier_image1;
    TextView addressTV;
    Button addSupplier;
    String name,address,supplier_image,user_id;
    public static String Supplier_id,Service_id;


    LocationManager locationManager;
    android.location.LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);

        setContentView(R.layout.activity_register_supplier );

        initViewSuppliers();

        addSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = etName.getText().toString();
                address = etService.getText().toString();
               // supplier_image = supplier_image1.getText().toString();
                if (name.equals("")) {
                    etName.setError("Add Name!");
                } else if (address.equals("")){
                    etService.setError("Add Service!");

                } else {
                getValues();
                addSuppliers();
                }
            }
        });

    }

    private void getValues() {
        name = etName.getText().toString();
        address = etService.getText().toString();
       // supplier_image = etContact.getText().toString();
        user_id = "Khanewal";
        //Sup_detail = "Some Detail";


    }

    public void initViewSuppliers(){
        etName =findViewById(R.id.et_supplier_name);
        etService= findViewById(R.id.et_supplier_serviceName);
       // etContact= findViewById(R.id.et_supplier_contact);
        addressTV = findViewById(R.id.tv_supplier_address);
        addSupplier= findViewById(R.id.btn_Add_Supplier);
        //cruntAddress();

    }

    public void cruntAddress() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new android.location.LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i("Location: ", location.toString());
                updateLocationInfo(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            Toast.makeText(this, "Grant the location access!", Toast.LENGTH_SHORT).show();
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (android.location.LocationListener) locationListener);
        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (lastKnownLocation != null) {
            updateLocationInfo(lastKnownLocation);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startListening();
        }

    }
    public void startListening()
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (android.location.LocationListener) locationListener);

    }
    public void updateLocationInfo(Location location)
    {
        Log.i("Location1: ", location.toString());
        String address = "Could not find any address";

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> listAddresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            if (listAddresses != null && listAddresses.size() > 0) {
                address = "";
                if (listAddresses.get(0).getThoroughfare() != null) {
                    address = listAddresses.get(0).getThoroughfare() + "\n";
                }
                if (listAddresses.get(0).getLocality() != null) {
                    address += listAddresses.get(0).getLocality() + " ";
                }
                if (listAddresses.get(0).getPostalCode() != null) {
                    address += listAddresses.get(0).getPostalCode() + " ";
                }
                if (listAddresses.get(0).getAdminArea() != null) {
                    address += listAddresses.get(0).getAdminArea();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        addressTV.setText(address);
    }
    private void addSuppliers() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        RequestInterfacePart requestInterfacePart = retrofit.create(RequestInterfacePart.class);
        final Suppliers suppliers = new Suppliers();

//
//        suppliers.set
//   name(name);
        suppliers.setName(name);
        suppliers.setAddress(address);
       // suppliers.setSupplier_image(Sup_name);
      //  suppliers.setUser_id(user_id);
       // suppliers.setUser_id("1");


        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.REGISTER_SUPPLIER);
        request.setSuppliers(suppliers);

        Call<ServerResponce> resp = requestInterfacePart.operationone(request);

        resp.enqueue(new Callback<ServerResponce>() {
            @Override
            public void onResponse(Call<ServerResponce> call, Response<ServerResponce> response) {
                try {
                    Suppliers suppliers1 = new Suppliers();
                    ServerResponce resp = response.body();
                    suppliers1 = resp.getSuppliers();


                    Supplier_id = suppliers1.getUser_id();
                    Service_id = suppliers1.getAddress();

                    setUpIntent();

//                    Toast.makeText(Add_Supplier.this, ""+suppliers1.getSupplier_id(), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(Add_Supplier.this, ""+suppliers1.getService_id(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(Add_Supplier.this, "" + resp.getMessage(), Toast.LENGTH_LONG).show();
                }
                catch(Exception e){
                    Toast.makeText(Add_Supplier.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ServerResponce> call, Throwable t) {
                Toast.makeText(Add_Supplier.this, "Connection Failure "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void setUpIntent() {
        Intent intent = new Intent(Add_Supplier.this,Add_Items.class);
        startActivity(intent);
        finish();
    }

}