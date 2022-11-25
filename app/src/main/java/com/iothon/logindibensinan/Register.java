package com.iothon.logindibensinan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private EditText namaDaftar, emailDaftar, alamatDaftar, passwordDaftar;
    private Button btnRegister;
    private FirebaseAuth ojoLali;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        namaDaftar = findViewById(R.id.nama_Daftar);
        emailDaftar = findViewById(R.id.email_Daftar);
        alamatDaftar = findViewById(R.id.alama_Daftar);
        passwordDaftar = findViewById(R.id.password_Daftar);
        btnRegister = findViewById(R.id.btnRegisterDB);
        ojoLali = FirebaseAuth.getInstance();
        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daftarUser();
            }
        });


    }

    private void daftarUser(){
        String nama = namaDaftar.getText().toString();
        String email = emailDaftar.getText().toString();
        String alamat = alamatDaftar.getText().toString();
        String password = passwordDaftar.getText().toString();

        if(TextUtils.isEmpty(email)){
            namaDaftar.setError("Email nya diisi dulu mas/mbak");
            namaDaftar.requestFocus();
        } else if(TextUtils.isEmpty(password)){
            passwordDaftar.setError("Password nya jangan lupa mas/mbak");
            passwordDaftar.requestFocus();
        } else if(TextUtils.isEmpty(alamat)){
            alamatDaftar.setError("Alamatnya ketinggalan kang/teh");
            alamatDaftar.requestFocus();
        } else if(TextUtils.isEmpty(nama)){
            namaDaftar.setError("Namanya belum kang/teh");
            namaDaftar.requestFocus();
        } else {
            ojoLali.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        // FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        // Create a new user with a first and last name
                        Map<String, Object> pengguna = new HashMap<>();
                        pengguna.put("alamat", alamat);
                        pengguna.put("email", email);
                        pengguna.put("nama", nama);

                        // Add a new document with a generated ID
                        db.collection("penggunaHokya")
                                .add(pengguna)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
//                                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                        Toast.makeText(Register.this, "DocumentSnapshot added with ID: " + documentReference.getId(), Toast.LENGTH_SHORT).show();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
//                                        Log.w(TAG, "Error adding document", e);
                                        Toast.makeText(Register.this, "Pengguna gagal mendaftar karena " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                        Toast.makeText(Register.this, "Pengguna berhasil mendaftar dengan aman", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Register.this, MainActivity.class));
                    } else {
                        Toast.makeText(Register.this, "Pengguna gagal mendaftar karena " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }
}