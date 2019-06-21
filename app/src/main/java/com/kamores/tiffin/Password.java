package com.kamores.tiffin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class Password extends AppCompatActivity {
    EditText etPass, etConfirmPass;
    Button finish;

    GoogleSignInClient mGoogleSignInClient;
    Button signOut;
    TextView nameTV;
    TextView emailTV;
    TextView idTV;
    ImageView imageIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        initViews();

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent is = new Intent(Password.this,BaseActivity.class);
                startActivity(is);
            }
        });

        // for google retrive image, name & email
        //signOut = findViewById(R.id.sign_Out);
        nameTV = findViewById(R.id.user_name);
        emailTV = findViewById(R.id.user_email);
        imageIV = findViewById(R.id.imageIVGoogle);




        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(Password.this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivinName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();

            Uri personImage = acct.getPhotoUrl();

            nameTV.setText("Name: " + personName);
            emailTV.setText("Email: " + personEmail);
            Glide.with(this).load(personImage).into(imageIV);
        }
//        signOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                forSignOut();
//            }
//        });
    }
    public void initViews(){
        etPass= findViewById(R.id.et_Pass);
        etConfirmPass= findViewById(R.id.et_ConfirmPass);
        finish= findViewById(R.id.btn_Finished);
    }

    public void forSignOut() {

        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Password.this, "Sign Out", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Password.this, MainActivity.class));
                        finish();

                    }
                });
    }
}