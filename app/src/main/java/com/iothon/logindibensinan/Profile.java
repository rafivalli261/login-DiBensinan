package com.iothon.logindibensinan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {

    private FirebaseAuth ojoLali;
    private TextView namaProfil;
    private Button btnLogout;
    public static final String EXTRA_NAMA = "extra_age";
    public static final String EXTRA_ALAMAT = "extra_alamat";
    public static final String EXTRA_EMAIL = "extra_email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ojoLali = FirebaseAuth.getInstance();
        btnLogout = findViewById(R.id.btn_Logout);
        namaProfil = findViewById(R.id.nama_profile);

        // fungsi untuk menampilkan Nama pada dashboard
        tampilkanNama();

        // kode untuk sign out
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ojoLali.signOut();
                startActivity(new Intent(Profile.this, MainActivity.class));
            }
        });

    }

    public void tampilkanNama(){
        String nama = getIntent().getStringExtra(EXTRA_NAMA);
        namaProfil.setText(nama);
    }

}