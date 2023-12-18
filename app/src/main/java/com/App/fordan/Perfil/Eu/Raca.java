package com.App.fordan.Perfil.Eu;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import com.App.fordan.R;
import com.App.fordan.Salvar.Povo;
import com.App.fordan.Salvar.Quilombo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Locale;

public class Raca extends AppCompatActivity {
    Button sim, nao, preto, pardo, branco, amarelo, indigena, quilombola;
    ImageButton speak;
    TextToSpeech t1;
    String raca, usuarioID;
    FirebaseAuth mAuth;
    FirebaseDatabase db;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raca);
        getSupportActionBar().hide();

        sim = findViewById(R.id.sim);
        nao = findViewById(R.id.nao);
        preto = findViewById(R.id.preto);
        pardo = findViewById(R.id.pardo);
        branco = findViewById(R.id.branco);
        amarelo = findViewById(R.id.amarelo);
        indigena = findViewById(R.id.indigena);
        quilombola = findViewById(R.id.quilombola);
        speak = findViewById(R.id.speak);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        id = mAuth.getUid();

        nao.setOnClickListener(v -> {
            raca = "Não desejo informar";
            salvar_dados();
            Intent perfil = new Intent(Raca.this, Escolaridade.class);
            startActivity(perfil);

        });

        sim.setOnClickListener(v -> {
            preto.setVisibility(View.VISIBLE);
            pardo.setVisibility(View.VISIBLE);
            branco.setVisibility(View.VISIBLE);
            amarelo.setVisibility(View.VISIBLE);
            indigena.setVisibility(View.VISIBLE);
            quilombola.setVisibility(View.VISIBLE);
        });
        speak.setOnClickListener(view -> {
            String text = "Deseja Informar sua raça? Clique no Sim, botão verde e escolha uma das opçãoes: Preto, Pardo, Branco, Amarelo, Indigena e Quilombola, caso não deseje informar, clica no não vermelho";
            t1.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        });

        t1 = new TextToSpeech(Raca.this, i -> {
            if(i != TextToSpeech.ERROR)
                t1.setLanguage(new Locale("pt-br", "BR"));
        });


        preto.setOnClickListener(view -> {
            raca = "Preto";
            salvar_dados();
            proximo();
        });

        pardo.setOnClickListener(view -> {
            raca = "Pardo";
            salvar_dados();
            proximo();
        });

        branco.setOnClickListener(view -> {
            raca = "Branco";
            salvar_dados();
            proximo();
        });

        amarelo.setOnClickListener(view -> {
            raca = "Amarelo";
            salvar_dados();
            proximo();
        });

        indigena.setOnClickListener(view -> {
            raca = "Indigena";
            salvar_dados();
            povo();
        });

        quilombola.setOnClickListener(view -> {
            raca = "Quilombola";
            salvar_dados();
            quilombo();
        });
        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
    private void salvar_dados(){
        DatabaseReference reference = db.getReference().child("users").child(id);
        reference.child("Raça").setValue(raca);
    }
    private void proximo(){
        Intent perfil = new Intent(Raca.this, Escolaridade.class);
        startActivity(perfil);
    }
    private void povo(){
        Intent perfil = new Intent(Raca.this, Povo.class);
        startActivity(perfil);
    }
    private void quilombo(){
        Intent perfil = new Intent(Raca.this, Quilombo.class);
        startActivity(perfil);
    }

    public void onBackPressed() {
        volto();
    }

    private void volto(){
        Intent in = new Intent( Raca.this, CPF.class);
        startActivity(in);
        finish();
    }
}