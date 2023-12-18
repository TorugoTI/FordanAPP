package com.App.fordan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.App.fordan.Perfil.Perfil;
import com.google.firebase.auth.FirebaseAuth;

public class Info extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button voltar, aviso, app;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        voltar = findViewById(R.id.voltar);
        aviso = findViewById(R.id.aviso);
        app = findViewById(R.id.app);

        voltar.setOnClickListener(v -> {
            Intent perfil = new Intent(Info.this, MainActivity.class);
            startActivity(perfil);
            finish();
        });

        aviso.setOnClickListener(view ->startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/cgdAU_ahdRQ"))));
        app.setOnClickListener(view ->startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/D26hDxg0qDU"))));

    }
    public void onBackPressed() {
        volto();
    }

    private void volto() {
        Intent in = new Intent( Info.this, MainActivity.class);
        startActivity(in);
        finish();
    }

    private void abrirTelaUsuario() {
        Intent intent = new Intent( Info.this, Perfil.class);
        startActivity(intent);
        finish();
    }

}