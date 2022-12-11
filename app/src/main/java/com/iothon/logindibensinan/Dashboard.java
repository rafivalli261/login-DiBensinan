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
    private String namaku;
    FirebaseAuth ojoLali;
    FirebaseUser userSekarang;
    FirebaseFirestore db;
    Button kePesan;
    ImageView Profile;

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


        // Query untuk menampilkan Nama pada dashboard
        Query namaBerjaya = db.collection("penggunaHokya").whereIn("email", Arrays.asList(userSekarang.getEmail()));
        namaBerjaya.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                 namaku = Objects.requireNonNull(document.getData().get("nama")).toString();
                                 haloOm.setText(namaku);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        // Tombol untuk menuju ke Profile
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Profile = new Intent(getApplicationContext(),Profile.class);
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

}