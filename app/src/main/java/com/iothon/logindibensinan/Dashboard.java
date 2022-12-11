package com.iothon.logindibensinan;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.Objects;


public class Dashboard extends AppCompatActivity {

    private TextView haloOm;
    FirebaseAuth ojoLali;
    FirebaseUser userSekarang;
    FirebaseFirestore db;
    Button kePesan;
    ImageView Profile;
    public static final String EXTRA_NAMA = "extra_age";
    public static final String EXTRA_ALAMAT = "extra_alamat";
    public static final String EXTRA_EMAIL = "extra_email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ojoLali = FirebaseAuth.getInstance();
        haloOm = findViewById(R.id.salamSatuJiwa);
        userSekarang = ojoLali.getCurrentUser();
        Profile = (ImageView) findViewById(R.id.Profile);
        kePesan = findViewById(R.id.pesan_kasana);
        db = FirebaseFirestore.getInstance();


        // fungsi untuk menampilkan Nama pada dashboard
        tampilkanNama();

        // Tombol untuk menuju ke Profile
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Profile = new Intent(getApplicationContext(),Profile.class);
                Profile.putExtra(Dashboard.EXTRA_NAMA, getIntent().getStringExtra(EXTRA_NAMA));
                Profile.putExtra(Dashboard.EXTRA_EMAIL, getIntent().getStringExtra(EXTRA_EMAIL));
                Profile.putExtra(Dashboard.EXTRA_ALAMAT, getIntent().getStringExtra(EXTRA_ALAMAT));
                startActivity(Profile);
            }
        });

        // tombol untuk menuju halaman pemesanan
        kePesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, Pesan.class));
            }
        });
    }

    public void tampilkanNama(){
        String nama = getIntent().getStringExtra(EXTRA_NAMA);
        haloOm.setText(nama);
    }

}