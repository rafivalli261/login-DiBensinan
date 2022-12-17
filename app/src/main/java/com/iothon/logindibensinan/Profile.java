package com.iothon.logindibensinan;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {

    private FirebaseAuth ojoLali;
    private Button btnLogout, btnUpload;
    private TextView namaInProfile;
    private EditText inputUpdateNama, inputUpdateAlamat, inputUpdateEmail;
    private FirebaseUser user;
    private FirebaseFirestore db;
    public static final String EXTRA_NAMA = "extra_name";
    public static final String EXTRA_ALAMAT = "extra_alamat";
    public static final String EXTRA_EMAIL = "extra_email";
    public static final String EXTRA_ID_DOKUMEN = "extra_id_dokumen";

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
        btnUpload = findViewById(R.id.btn_update);
        user = ojoLali.getCurrentUser();
        db = FirebaseFirestore.getInstance();

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

        // kode untuk upload
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = inputUpdateNama.getText().toString();
                String email = inputUpdateEmail.getText().toString();
                String alamat = inputUpdateAlamat.getText().toString();
                String idDokumen = getIntent().getStringExtra(EXTRA_ID_DOKUMEN);

                if(TextUtils.isEmpty(email)){
                    inputUpdateEmail.setError("Email nya diisi dulu mas/mbak");
                    inputUpdateEmail.requestFocus();
                } else if(TextUtils.isEmpty(nama)){
                    inputUpdateNama.setError("Namanya nya jangan lupa mas/mbak");
                    inputUpdateNama.requestFocus();
                } else if(TextUtils.isEmpty(alamat)){
                    inputUpdateAlamat.setError("Alamatnya ketinggalan kang/teh");
                    inputUpdateAlamat.requestFocus();
                } else {

                    user.updateEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "User email address updated.");
                                    }
                                }
                            });

                    DocumentReference penggunaUpdate = db.collection("penggunaHokya").document(idDokumen);

                    // Set the "email", "nama", "alamat", and "peran" field of the user 'idDokumen'
                    penggunaUpdate
                            .update(
                                    "email", email,
                                    "nama", nama,
                                    "alamat", alamat,
                                    "peran", "user")
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Profile.this, "Pengguna berhasil update dengan nyaman", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "DocumentSnapshot successfully updated!");
                                    Intent sendAct = new Intent(Profile.this, Dashboard.class);
                                    sendAct.putExtra(Dashboard.EXTRA_NAMA, nama);
                                    sendAct.putExtra(Dashboard.EXTRA_EMAIL, email);
                                    sendAct.putExtra(Dashboard.EXTRA_ALAMAT, alamat);
                                    startActivity(sendAct);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Profile.this, "Pengguna berhasil update dengan nyaman", Toast.LENGTH_SHORT).show();
                                    Log.w(TAG, "Error updating document", e);
                                }
                            });
                }

            }
        });

    }

    private void tampilkanDataProfil(){
        String nama = getIntent().getStringExtra(EXTRA_NAMA);
        String email = getIntent().getStringExtra(EXTRA_EMAIL);
        String alamat = getIntent().getStringExtra(EXTRA_ALAMAT);
        namaInProfile.setText(nama);
        inputUpdateNama.setText(nama);
        inputUpdateEmail.setText(email);
        inputUpdateAlamat.setText(alamat);
    }

}
