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
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ojoLali = FirebaseAuth.getInstance();
        btnLogout = findViewById(R.id.btn_Logout);

        // kode untuk sign out
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ojoLali.signOut();
                startActivity(new Intent(Profile.this, MainActivity.class));
            }
        });

    }

}
