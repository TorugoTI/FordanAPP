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

public class Religiao extends AppCompatActivity {

    Button cato, cando, evan, umba, agno, judeu, espi, ateu;
    ImageButton speak;
    TextToSpeech t1;
    String religiao, usuarioID;
    FirebaseAuth mAuth;
    FirebaseDatabase db;
    String id;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_religiao);
        getSupportActionBar().hide();

        cato = findViewById(R.id.cato);
        cando = findViewById(R.id.cando);
        evan = findViewById(R.id.evan);
        umba = findViewById(R.id.umba);
        agno = findViewById(R.id.agno);
        judeu = findViewById(R.id.judeu);
        espi = findViewById(R.id.espi);
        ateu = findViewById(R.id.ateu);
        speak = findViewById(R.id.speak);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        id = mAuth.getUid();


        speak.setOnClickListener(view -> {
            String text = "Qual a sua Religião? Católica, Evangêlica, Espirita, Candomblê, Umbanda, Atéu, Agnóstico e Judeu";
            t1.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        });

        t1 = new TextToSpeech(Religiao.this, i -> {
            if (i != TextToSpeech.ERROR)
                t1.setLanguage(new Locale("pt-br", "BR"));
        });


        cato.setOnClickListener(view -> {
            religiao = "Católica";
            salvar_dados();
            proximo();
        });

        cando.setOnClickListener(view -> {
            religiao = "Candomblê";
            salvar_dados();
            proximo();
        });

        evan.setOnClickListener(view -> {
            religiao = "Evangêlica";
            salvar_dados();
            proximo();
        });

        umba.setOnClickListener(view -> {
            religiao = "Umbandista";
            salvar_dados();
            proximo();
        });

        agno.setOnClickListener(view -> {
            religiao = "Agnóstico";
            salvar_dados();
            proximo();
        });

        ateu.setOnClickListener(view -> {
            religiao = "Atéu";
            salvar_dados();
            proximo();
        });
        judeu.setOnClickListener(view -> {
            religiao = "Judeu";
            salvar_dados();
            proximo();
        });
        espi.setOnClickListener(view -> {
            religiao = "Espirita";
            salvar_dados();
            proximo();
        });
        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    private void salvar_dados() {
        DatabaseReference reference = db.getReference().child("users").child(id);
        reference.child("Religião").setValue(religiao);
    }

    private void proximo() {
        Intent perfil = new Intent(Religiao.this, Apoio1.class);
        startActivity(perfil);
    }
    public void onBackPressed() {
        volto();
    }

    private void volto(){
        Intent in = new Intent( Religiao.this, Orientacao.class);
        startActivity(in);
        finish();
    }
}