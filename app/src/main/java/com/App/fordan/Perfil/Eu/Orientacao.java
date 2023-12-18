package com.App.fordan.Perfil.Eu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import com.App.fordan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class Orientacao extends AppCompatActivity {

    Button het, les, pan, asex, bi;
    ImageButton speak;
    TextToSpeech t1;
    String ori;
    FirebaseAuth mAuth;
    FirebaseDatabase db;
    String id;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orientacao);
        getSupportActionBar().hide();
        het = findViewById(R.id.het);
        les = findViewById(R.id.les);
        pan = findViewById(R.id.pan);
        asex = findViewById(R.id.asex);
        bi = findViewById(R.id.bi);
        speak = findViewById(R.id.speak);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        id = mAuth.getUid();

        speak.setOnClickListener(view -> {
            String text = "Qual a sua Orientação Sexual? Hétero, Lébisca, Bissexual, Panssexual ou Assexual";
            t1.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        });

        t1 = new TextToSpeech(Orientacao.this, i -> {
            if (i != TextToSpeech.ERROR)
                t1.setLanguage(new Locale("pt-br", "BR"));
        });


        het.setOnClickListener(view -> {
            ori = "Hetêrossexual";
            salvar_dados();
            proximo();
        });

        les.setOnClickListener(view -> {
            ori = "Lésbica";
            salvar_dados();
            proximo();
        });

        pan.setOnClickListener(view -> {
            ori = "Panssexual";
            salvar_dados();
            proximo();
        });

        asex.setOnClickListener(view -> {
            ori = "Assexual";
            salvar_dados();
            proximo();
        });

        bi.setOnClickListener(view -> {
            ori = "Bissexual";
            salvar_dados();
            proximo();
        });
    }
    private void salvar_dados() {
        DatabaseReference reference = db.getReference().child("users").child(id);
        reference.child("Orientação-Sexual").setValue(ori);
    }

    private void proximo() {
        Intent perfil = new Intent(Orientacao.this, Religiao.class);
        startActivity(perfil);
    }
    public void onBackPressed() {
        volto();
    }

    private void volto(){
        Intent in = new Intent( Orientacao.this, Identidade.class);
        startActivity(in);
        finish();
    }
}