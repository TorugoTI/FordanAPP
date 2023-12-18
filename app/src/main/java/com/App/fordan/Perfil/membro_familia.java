package com.App.fordan.Perfil;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.App.fordan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.Locale;

public class membro_familia extends AppCompatActivity {
    Button Filho, Companheiro, Companheira;
    ImageButton mic, speak;
    EditText familia;
    TextToSpeech t1;
    String filiacao;
    FirebaseAuth mAuth;
    FirebaseDatabase db;
    String id;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membro_familia);

        getSupportActionBar().hide();

        Filho = findViewById(R.id.Filho);
        Companheiro = findViewById(R.id.Companheiro);
        Companheira = findViewById(R.id.Companheira);
        mic = findViewById(R.id.mic);
        speak = findViewById(R.id.speak);
        familia = findViewById(R.id.familia);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        id = mAuth.getUid();


        Filho.setOnClickListener(v -> {
            filiacao = "Filho";
            salvar_dados();
            proximo();

        });

        Companheira.setOnClickListener(v -> {
            filiacao = "Companheira";
            salvar_dados();
            proximo();

        });

        Companheiro.setOnClickListener(v -> {
            filiacao = "Companheiro";
            salvar_dados();
            proximo();

        });

        speak.setOnClickListener(view -> {
            String text = "Digite o nome do integrante da sua familia ou Clique no Mic e Fale, ao terminar clique no botão em que aquela pessoa significa a você";
            t1.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        });
        t1 = new TextToSpeech(membro_familia.this, i -> {
            if(i != TextToSpeech.ERROR)
                t1.setLanguage(new Locale("pt-br", "BR"));
        });
        mic.setOnClickListener(view -> {
            Intent voz = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            voz.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            voz.putExtra(RecognizerIntent.EXTRA_PROMPT, "Comece a falar");
            voz.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"pt-BR");
            try{
                startActivityForResult(voz, 100);
                familia.setText("");
            }catch(ActivityNotFoundException e){
                Toast.makeText(getApplicationContext(),"Seu celular não suporta o texto por voz", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
    }
    protected void onActivityForResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK){
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            familia.setText(result.get(0).toString());
        }
    }
    private void salvar_dados(){
        String familiares = familia.getText().toString();
        if (familiares.isEmpty()) {
            familia.setError("Por Favor Preencha com o nome e a filiação da sua familia");
            String text2 = "Por Favor Preencha com o nome e a filiação da sua familia";
            t1.speak(text2, TextToSpeech.QUEUE_FLUSH, null);
            return;
        }

        DatabaseReference reference = db.getReference().child("users").child(id);
        reference.child("Familiares").setValue(familia);
        reference.child("Filiação").setValue(filiacao);

        Intent perfil = new Intent(membro_familia.this, Familia.class);
        startActivity(perfil);
    }
    private void proximo(){
        Intent perfil = new Intent(membro_familia.this, Familia.class);
        startActivity(perfil);
    }

    public void onBackPressed() {
        volto();
    }

    private void volto() {
        Intent in = new Intent( membro_familia.this, Familia.class);
        startActivity(in);
        finish();
    }
}