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


public class Dashboard extends AppCompatActivity {

    private FirebaseAuth ojoLali;
    private FirebaseUser userSekarang;
    private TextView haloOm;
    private FirebaseFirestore db;
    private Button kePesan;
//    private String namakuBento;
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
                                // Log.d(TAG, document.getId() + " => " + document.getData());
                                // namakuBento = (String) document.getData().get("nama");
                                 haloOm.setText(String.format("Halo, Kak %s", document.getData().get("nama")));
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        // haloOm.setText(String.format("Halo, Kak %s", namakuBento));
        // Button untuk Sign Out
//=======
//        haloOm.setText(String.format("Halo, Kak %s", namakuBento));
//>>>>>>> 8f364397589f00145ed0a05d69dd8918a5072dae

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