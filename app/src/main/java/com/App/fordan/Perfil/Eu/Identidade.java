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

public class Identidade extends AppCompatActivity {

    Button cis, trans, bina;
    ImageButton speak;
    TextToSpeech t1;
    String iden;
    FirebaseAuth mAuth;
    FirebaseDatabase db;
    String id;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identidade);
        getSupportActionBar().hide();
        cis = findViewById(R.id.cis);
        trans = findViewById(R.id.trans);
        bina = findViewById(R.id.bina);
        speak = findViewById(R.id.speak);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        id = mAuth.getUid();

        speak.setOnClickListener(view -> {
            String text = "Qual a sua Identidade de Gênero? Cisgênero, nasceu com o sexo que se identifica, Transgênero, pessoa que não se identifica com o sexo biologico exemplo transsexual, Não binario, pessoa que não se identifica como homem ou mulher";
            t1.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        });

        t1 = new TextToSpeech(Identidade.this, i -> {
            if (i != TextToSpeech.ERROR)
                t1.setLanguage(new Locale("pt-br", "BR"));
        });


        cis.setOnClickListener(view -> {
            iden = "Cis-Gênero";
            salvar_dados();
            proximo();
        });

        trans.setOnClickListener(view -> {
            iden = "Trans-Gênero";
            salvar_dados();
            proximo();
        });

        bina.setOnClickListener(view -> {
            iden = "Não-Binario";
            salvar_dados();
            proximo();
        });
    }
    private void salvar_dados() {
        DatabaseReference reference = db.getReference().child("users").child(id);
        reference.child("Identidade-Genero").setValue(iden);
    }

    private void proximo() {
        Intent perfil = new Intent(Identidade.this, Orientacao.class);
        startActivity(perfil);
    }
    public void onBackPressed() {
        volto();
    }

    private void volto(){
        Intent in = new Intent( Identidade.this, Escolaridade.class);
        startActivity(in);
        finish();
    }
}