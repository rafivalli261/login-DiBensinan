package com.iothon.logindibensinan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    TextView tvRegister;
    Button btnLogin;

    private FirebaseAuth ojoLali;
    private EditText rxEmail, rxPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        ojoLali = FirebaseAuth.getInstance();
        rxEmail = findViewById(R.id.rx_email);
        rxPassword = findViewById(R.id.rx_password);
        tvRegister = (TextView) findViewById(R.id.tvRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent Login = new Intent(getApplicationContext(),Dashboard.class);
//                startActivity(Login);
                loginPengguna();
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BukaRegister();
            }
        });
    }
    public void BukaRegister(){
        Intent BukaActRegister = new Intent(getApplicationContext(),Register.class);
        startActivity(BukaActRegister);
    }

    private void loginPengguna(){
        String email = rxEmail.getText().toString();
        String password = rxPassword.getText().toString();
        if(TextUtils.isEmpty(email)){
            rxEmail.setError("Email nya diisi dulu mas/mbak");
            rxEmail.requestFocus();
        } else if(TextUtils.isEmpty(password)){
            rxPassword.setError("Password nya jangan lupa mas/mbak");
            rxPassword.requestFocus();
        } else {
            ojoLali.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Pengguna berhasil login dengan aman", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, Dashboard.class));
                    } else {
                        Toast.makeText(MainActivity.this, "Pengguna gagal login karena " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}