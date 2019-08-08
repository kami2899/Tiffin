package com.kamores.tiffin.Activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.kamores.tiffin.Constants.Constants;
import com.kamores.tiffin.Constants.RequestInterfacePart;
import com.kamores.tiffin.Constants.ServerRequest;
import com.kamores.tiffin.Constants.ServerResponce;
import com.kamores.tiffin.ModelClasses.User;
import com.kamores.tiffin.ModelClasses.UserShared;
import com.kamores.tiffin.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login_Activity_Supplier extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btRegister;
    private TextView tvLogin;
    private EditText contact,password;
    private Button btn_login;
    String supplier_id;
    User userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_activity_supplier);

        ActionBar actionBar= getSupportActionBar();
        actionBar.hide();

        initViews();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String con = contact.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if (con.isEmpty()) {
                    contact.setError("Enter Username");
                }
                else if (pass.isEmpty()) {

                    password.setError("Enter Password");
                }

                else{
                    loginProcess(con,pass);
                }
            }
        });

    }

    private void initViews() {
        btRegister= findViewById(R.id.btRegister);
        tvLogin = findViewById(R.id.tvLogin);
        contact=findViewById(R.id.Supplier_Contact);
        password=findViewById(R.id.et_Supplier_Password);
        btn_login=findViewById(R.id.btn_Supplier_Login);
        btRegister.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        if (v==btRegister){
            Intent intent   = new Intent(Login_Activity_Supplier.this,Register_Activity_supplier.class);
            Pair[] pairs    = new Pair[1];
            pairs[0] = new Pair<View,String>(tvLogin,"tvLogin");
            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(Login_Activity_Supplier.this,pairs);
            startActivity(intent,activityOptions.toBundle());
        }
    }
    private void loginProcess(String contact,String password){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterfacePart requestInterface = retrofit.create(RequestInterfacePart.class);

        User user = new User();
        user.setContact(contact);
        user.setPassword(password);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.LOG_IN_SUPPLIER);
        request.setUser(user);
        Call<ServerResponce> response = requestInterface.operationone(request);

        response.enqueue(new Callback<ServerResponce>() {
            @Override
            public void onResponse(Call<ServerResponce> call, retrofit2.Response<ServerResponce> response) {

                ServerResponce resp = response.body();
                userData = resp.getUser();
                supplier_id = userData.getSup_id();

                Toast.makeText(Login_Activity_Supplier.this, "" + resp.getMessage() + supplier_id, Toast.LENGTH_SHORT).show();

                if(resp.getResult().equals(Constants.SUCCESS)){
                    UserShared user1 =new UserShared(Login_Activity_Supplier.this);
                    user1.setSupplier_id(supplier_id);
                    Intent intent=new Intent(Login_Activity_Supplier.this,Add_Items.class);
                    intent.putExtra("supplier_id",supplier_id);
                    Toast.makeText(Login_Activity_Supplier.this, supplier_id, Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<ServerResponce> call, Throwable t) {

                Log.d(Constants.TAG,"failed");
                Toast.makeText(Login_Activity_Supplier.this, "Invalid User or Password!" , Toast.LENGTH_SHORT).show();

                // Snackbar.make(MainActivity.this, t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();

            }
        });
    }
}
