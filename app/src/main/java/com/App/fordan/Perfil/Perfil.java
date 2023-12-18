package com.App.fordan.Perfil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import com.App.fordan.Perfil.Eu.Meus_Dados;
import com.App.fordan.R;
import com.App.fordan.Tela_fordan;
import com.google.firebase.auth.FirebaseAuth;
public class Perfil extends AppCompatActivity {
    FirebaseAuth mAuth;
    private ImageButton speak;
    private Button meus;
    private Button familia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        meus = findViewById(R.id.btn_meus);
        familia = findViewById(R.id.btn_familia);
        speak = findViewById(R.id.speak);

        meus.setOnClickListener(v -> {
            Intent perfil=new Intent(Perfil.this, Meus_Dados.class);
            startActivity(perfil);
            finish();
        });

        familia.setOnClickListener(v -> {
            Intent perfil = new Intent(Perfil.this, Familia.class);
            startActivity(perfil);

        });

    }

    public void onBackPressed() {
        volto();
    }

    private void volto() {
        Intent in = new Intent( Perfil.this, Tela_fordan.class);
        startActivity(in);
        finish();
    }
}