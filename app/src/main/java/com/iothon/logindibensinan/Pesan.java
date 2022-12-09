package com.iothon.logindibensinan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Pesan extends AppCompatActivity {

    private Button kirimPesan;
    private EditText namaUser, jmlBensinPesan, jnsBensinPesan, noWA, lokasi, namaMitra;
    private FirebaseFirestore db;
    private FirebaseAuth ojoLali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan);

        kirimPesan = findViewById(R.id.btn_kirim_pesan);
        namaUser = findViewById(R.id.nama_user);
        jmlBensinPesan = findViewById(R.id.jml_bensin_pesan);
        jnsBensinPesan = findViewById(R.id.jns_bensin_pesan);
        noWA = findViewById(R.id.no_wa);
        lokasi = findViewById(R.id.fl_loc);
        namaMitra = findViewById(R.id.nama_mitra_pesan);
        db = FirebaseFirestore.getInstance();
        ojoLali = FirebaseAuth.getInstance();

        kirimPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tambahData();
                Toast.makeText(Pesan.this, "Pesanan segera diproses. Silakan Tunggu!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Pesan.this, Dashboard.class));
            }
        });

    }

    // menambahkan data ke database transaksiGelap
    private void tambahData(){
        String namaPengguna = namaUser.getText().toString();
        int jumlahBensin = Integer.parseInt(jmlBensinPesan.getText().toString());
        String jenisBensin = jnsBensinPesan.getText().toString();
        String nomorWA = noWA.getText().toString();
        String namaRekan = namaMitra.getText().toString();
        String location = lokasi.getText().toString();

        // Add a new document with a generated id.
        Map<String, Object> data = new HashMap<>();
        data.put("namaUser", namaPengguna);
        data.put("namaMitra", namaRekan);
        data.put("lokasi", location);
        data.put("jenisBensin", jenisBensin);
        data.put("noWA", nomorWA);
        data.put("tglPesan", getCurrentDate());
        data.put("totalBayar", jumlahBensin * 10000);
        data.put("totalLiter", jumlahBensin);

        db.collection("transaksiGelap")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(Pesan.this, "Data berhasil direkam dengan ID: " + documentReference.getId(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error adding document" + e);
                        Toast.makeText(Pesan.this, "Data gagal direkam dengan error : " + e, Toast.LENGTH_SHORT).show();

                    }
                });

    }

    // fungsi untuk menetapkan waktu dan tanggal transaksi
    private String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();

        return dateFormat.format(date);
    }

}