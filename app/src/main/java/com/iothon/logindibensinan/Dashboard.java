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
import android.widget.Toast;

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
    private int backButtonCount = 0;
    FirebaseAuth ojoLali;
    FirebaseUser userSekarang;
    FirebaseFirestore db;
    Button kePesan;
    ImageView Profile;
    public static final String EXTRA_NAMA = "extra_name";
    public static final String EXTRA_ALAMAT = "extra_alamat";
    public static final String EXTRA_EMAIL = "extra_email";
    public static final String EXTRA_ID_DOKUMEN = "extra_id_dokumen";

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
                Profile.putExtra(Dashboard.EXTRA_ID_DOKUMEN, getIntent().getStringExtra(EXTRA_ID_DOKUMEN));
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

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = ojoLali.getCurrentUser();
        if(currentUser == null){
            Toast.makeText(this, "Login Heula!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Dashboard.this, MainActivity.class));
        }
    }

    // blok kode untuk keluar dari aplikasi
    @Override
    public void onBackPressed() {
        if(backButtonCount >= 1)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Tekan tombol kembali sekali lagi untuk keluar dari aplikasi", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }

}