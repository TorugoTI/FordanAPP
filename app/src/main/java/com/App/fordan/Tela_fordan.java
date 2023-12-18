package com.App.fordan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.App.fordan.Perfil.Perfil;
import com.google.firebase.auth.FirebaseAuth;

public class Tela_fordan extends AppCompatActivity {
    FirebaseAuth mAuth;
    Button btn_Sair;
    Button btn_perfil, btn_acoes, btn_info, speak;
    @SuppressLint("WrongViewCast")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_fordan);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        btn_Sair = findViewById(R.id.btn_Sair);
        btn_perfil = findViewById(R.id.btn_perfil);
        btn_acoes = findViewById(R.id.btn_Acoes);
        btn_info = findViewById(R.id.btn_Info);

        btn_Sair.setOnClickListener(v -> {
            mAuth.signOut();
            Intent troca=new Intent(Tela_fordan.this, MainActivity.class);
            startActivity(troca);
            finish();
        });

        btn_perfil.setOnClickListener(v -> {
            Intent perfil = new Intent(Tela_fordan.this, Perfil.class);
            startActivity(perfil);
            finish();
        });

        btn_acoes.setOnClickListener(v -> {
            Intent perfil1 = new Intent(Tela_fordan.this, Acoes.class);
            startActivity(perfil1);
            finish();
        });

        btn_info.setOnClickListener(v -> {
            Intent perfil2 = new Intent(Tela_fordan.this, Info.class);
            startActivity(perfil2);
            finish();
        });


    }
}
