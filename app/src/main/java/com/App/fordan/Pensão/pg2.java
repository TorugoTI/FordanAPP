package com.App.fordan.Pensão;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
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

public class pg2 extends AppCompatActivity {
    Button sim, nao, salvar;
    EditText pgt;
    ImageButton speak, mic;
    TextToSpeech t1;
    String profi;
    FirebaseDatabase db;
    FirebaseAuth mAuth;
    String id;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pg2);
        getSupportActionBar().hide();

        sim = findViewById(R.id.sim);
        nao = findViewById(R.id.nao);
        pgt = findViewById(R.id.pgt);
        mic = findViewById(R.id.mic);
        speak = findViewById(R.id.speak);
        salvar = findViewById(R.id.salvar);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        id = mAuth.getUid();

        nao.setOnClickListener(v -> {
            profi = "Não trabalha";

            DatabaseReference reference = db.getReference().child("users").child(id).child("Pensão");
            reference.child("PGT2").setValue(profi);

            Intent perfil = new Intent(pg2.this, pg3.class);
            startActivity(perfil);

        });

        sim.setOnClickListener(v -> {
            pgt.setVisibility(View.VISIBLE);
            salvar.setVisibility(View.VISIBLE);
            mic.setVisibility(View.VISIBLE);
        });

        speak.setOnClickListener(view -> {
            String text = "se o (a) genitor (a) trabalha de carteira assinada? Em caso afirmativo, qual a profissão, nome e endereço da empresa?" ;
            t1.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        });

        t1 = new TextToSpeech(pg2.this, i -> {
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
                pgt.setText("");
            }catch(ActivityNotFoundException e){
                Toast.makeText(getApplicationContext(),"Seu celular não suporta o texto por voz", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
        salvar.setOnClickListener(v -> salvar_dados());
    }
    protected void onActivityForResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK){
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            pgt.setText(result.get(0));
        }
    }
    private void salvar_dados(){
        profi = pgt.getText().toString();
        if (profi.isEmpty()) {
            pgt.setError("Por Favor Preencha com cuidado");
            String text2 = "Por Favor Preencha com cuidado";
            t1.speak(text2, TextToSpeech.QUEUE_FLUSH, null);
            return;
        }

        DatabaseReference reference = db.getReference().child("users").child(id).child("Pensão");
        reference.child("PGT2").setValue(profi);

        Intent perfil = new Intent(pg2.this, pg3.class);
        startActivity(perfil);
    }
    public void onBackPressed() {
        volto();
    }

    private void volto(){
        Intent in = new Intent( pg2.this, pg1.class);
        startActivity(in);
        finish();
    }
}