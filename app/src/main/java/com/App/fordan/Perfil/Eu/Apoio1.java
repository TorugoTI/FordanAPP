package com.App.fordan.Perfil.Eu;

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

public class Apoio1 extends AppCompatActivity {
    Button salvar;
    ImageButton mic, speak;
    EditText apoio1;
    TextToSpeech t1;
    FirebaseAuth mAuth;
    FirebaseDatabase db;
    String id;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apoio1);
        getSupportActionBar().hide();

        salvar = findViewById(R.id.salvar);
        mic = findViewById(R.id.mic);
        speak = findViewById(R.id.speak);
        apoio1 = findViewById(R.id.apoio1);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        id = mAuth.getUid();

        salvar.setOnClickListener(v -> salvar_dados());
        speak.setOnClickListener(view -> {
            String text = "Você tem alguma rede de Apoio? Qual e onde ela fica? Por exemplo: Cras São Pedro, Território 1";
            t1.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        });
        t1 = new TextToSpeech(Apoio1.this, i -> {
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
                apoio1.setText("");
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
            apoio1.setText(result.get(0).toString());
        }
    }

    private void salvar_dados() {
        String Apoio1 = apoio1.getText().toString();
        if (Apoio1.isEmpty()) {
            apoio1.setError("Por Favor Preencha com o Seu Nome");
            String text2 = "Por Favor Preencha com o Seu Nome";
            t1.speak(text2, TextToSpeech.QUEUE_FLUSH, null);
            return;
        }
        DatabaseReference reference = db.getReference().child("users").child(id);
        reference.child("Rede_de_Apoio_1").setValue(Apoio1);

        Intent perfil = new Intent(Apoio1.this, Apoio2.class);
        startActivity(perfil);
    }

    public void onBackPressed() {
        volto();
    }

    private void volto(){
        Intent in = new Intent( Apoio1.this, Religiao.class);
        startActivity(in);
        finish();
    }
}