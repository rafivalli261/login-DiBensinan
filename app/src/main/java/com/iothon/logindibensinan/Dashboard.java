package com.iothon.logindibensinan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Dashboard extends AppCompatActivity {

    private FirebaseAuth ojoLali;
    private Button btnLogout;
    private FirebaseUser userSekarang;
    private TextView haloOm;

    ImageView Profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ojoLali = FirebaseAuth.getInstance();
        btnLogout = findViewById(R.id.btn_Logout);
        haloOm = findViewById(R.id.salamSatuJiwa);
        userSekarang = ojoLali.getCurrentUser();

        Profile = (ImageView) findViewById(R.id.Profile);

        String namakuBento = userSekarang.toString();

        haloOm.setText(String.format("Halo, Kak %s", namakuBento));
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ojoLali.signOut();
                startActivity(new Intent(Dashboard.this, MainActivity.class));
            }
        });

        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Profile = new Intent(getApplicationContext(),Profile.class);
                startActivity(Profile);
            }
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = ojoLali.getCurrentUser();
//        if(currentUser != null){
//            startActivity(new Intent(Dashboard.this, MainActivity.class));
//        }
//
//    }
}