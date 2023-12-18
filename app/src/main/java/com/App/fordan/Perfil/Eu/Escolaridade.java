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

public class Escolaridade extends AppCompatActivity {
    Button mestre, doutor, fundaIn, fundaCom, supIn, supCom, medioIn, medioCom;
    ImageButton speak;
    TextToSpeech t1;
    String escola, usuarioID;
    FirebaseAuth mAuth;
    FirebaseDatabase db;
    String id;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolaridade);
        getSupportActionBar().hide();

        mestre = findViewById(R.id.mestre);
        doutor = findViewById(R.id.doutor);
        fundaCom = findViewById(R.id.fundaCom);
        fundaIn = findViewById(R.id.fundaIn);
        supCom = findViewById(R.id.supCom);
        supIn = findViewById(R.id.supIn);
        medioCom = findViewById(R.id.medioCom);
        medioIn = findViewById(R.id.medioIn);
        speak = findViewById(R.id.speak);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        id = mAuth.getUid();

        speak.setOnClickListener(view -> {
            String text = "Qual a sua escolaridade? Ensino Fundamental Incompleto, Ensino Fundamental Completo, Ensino Médio Incompleto, Ensino Médio Completo, Ensino Superior Incompleto, Ensino Superior Completo, Mestrado e Doutorado";
            t1.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        });

        t1 = new TextToSpeech(Escolaridade.this, i -> {
            if (i != TextToSpeech.ERROR)
                t1.setLanguage(new Locale("pt-br", "BR"));
        });


        mestre.setOnClickListener(view -> {
            escola = "mestrado";
            salvar_dados();
            proximo();
        });

        doutor.setOnClickListener(view -> {
            escola = "Doutorado";
            salvar_dados();
            proximo();
        });

        supCom.setOnClickListener(view -> {
            escola = "Ensino Superior Completo";
            salvar_dados();
            proximo();
        });

        supIn.setOnClickListener(view -> {
            escola = "Ensino Superior Incompleto";
            salvar_dados();
            proximo();
        });

        medioCom.setOnClickListener(view -> {
            escola = "Ensino Médio Completo";
            salvar_dados();
            proximo();
        });

        medioIn.setOnClickListener(view -> {
            escola = "Ensino Médio Incompleto";
            salvar_dados();
            proximo();
        });
        fundaCom.setOnClickListener(view -> {
            escola = "Ensino Fundamental Completo";
            salvar_dados();
            proximo();
        });
        fundaIn.setOnClickListener(view -> {
            escola = "Ensino Fundamental Incompleto";
            salvar_dados();
            proximo();
        });
        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    private void salvar_dados() {
        DatabaseReference reference = db.getReference().child("users").child(id);
        reference.child("Escolaridade").setValue(escola);
    }

    private void proximo() {
        Intent perfil = new Intent(Escolaridade.this, Identidade.class);
        startActivity(perfil);
    }
    public void onBackPressed() {
        volto();
    }

    private void volto(){
        Intent in = new Intent( Escolaridade.this, Raca.class);
        startActivity(in);
        finish();
    }
}