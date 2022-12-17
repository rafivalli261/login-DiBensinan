package com.iothon.logindibensinan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {

    private FirebaseAuth ojoLali;
    private Button btnLogout;
    private TextView namaInProfile;
    private EditText inputUpdateNama, inputUpdateAlamat, inputUpdateEmail;
    public static final String EXTRA_NAMA = "extra_name";
    public static final String EXTRA_ALAMAT = "extra_alamat";
    public static final String EXTRA_EMAIL = "extra_email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ojoLali = FirebaseAuth.getInstance();
        btnLogout = findViewById(R.id.btn_Logout);
        namaInProfile = findViewById(R.id.nama_in_profile);
        inputUpdateNama = findViewById(R.id.input_update_nama);
        inputUpdateAlamat = findViewById(R.id.input_update_alamat);
        inputUpdateEmail = findViewById(R.id.input_update_email);

        // untuk menampilkan data profil
        tampilkanDataProfil();

        // kode untuk sign out
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ojoLali.signOut();
                startActivity(new Intent(Profile.this, MainActivity.class));
            }
        });

    }

    public void tampilkanDataProfil(){
        String nama = getIntent().getStringExtra(EXTRA_NAMA);
        String email = getIntent().getStringExtra(EXTRA_EMAIL);
        String alamat = getIntent().getStringExtra(EXTRA_ALAMAT);
        namaInProfile.setText(nama);
        inputUpdateNama.setText(nama);
        inputUpdateEmail.setText(email);
        inputUpdateAlamat.setText(alamat);
    }


}
