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

public class pg7 extends AppCompatActivity {
    Button sim, nao, salvar;
    EditText pgt;
    ImageButton speak, mic;
    TextToSpeech t1;
    String tel, id;
    FirebaseAuth mAuth;
    FirebaseDatabase db;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pg7);
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
            tel = "Não possui filho";

            DatabaseReference reference = db.getReference().child("users").child(id).child("Pensão");
            reference.child("PGT7").setValue(tel);

            Intent perfil = new Intent(pg7.this,pg8.class);
            startActivity(perfil);

        });

        sim.setOnClickListener(v -> {
            pgt.setVisibility(View.VISIBLE);
            salvar.setVisibility(View.VISIBLE);
            mic.setVisibility(View.VISIBLE);
        });

        speak.setOnClickListener(view -> {
            String text = "O (A) genitor (a) que pagará a pensão tem outros filhos? Se sim, quantos?" ;
            t1.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        });

        t1 = new TextToSpeech(pg7.this, i -> {
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
        tel = pgt.getText().toString();
        if (tel.isEmpty()) {
            pgt.setError("Por Favor Preencha com cuidado");
            String text2 = "Por Favor Preencha com cuidado";
            t1.speak(text2, TextToSpeech.QUEUE_FLUSH, null);
            return;
        }

        DatabaseReference reference = db.getReference().child("users").child(id).child("Pensão");
        reference.child("PGT7").setValue(tel);

        Intent perfil = new Intent(pg7.this,pg8.class);
        startActivity(perfil);
    }
    public void onBackPressed() {
        volto();
    }

    private void volto(){
        Intent in = new Intent( pg7.this, pg6.class);
        startActivity(in);
        finish();
    }
}