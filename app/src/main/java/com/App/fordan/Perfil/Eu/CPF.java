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

public class CPF extends AppCompatActivity {
    Button salvar;
    ImageButton mic, speak;
    EditText cpf;
    TextToSpeech t1;
    FirebaseAuth mAuth;
    FirebaseDatabase db;
    String id;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpf);

        getSupportActionBar().hide();

        salvar = findViewById(R.id.salvar);
        mic = findViewById(R.id.mic);
        speak = findViewById(R.id.speak);
        cpf = findViewById(R.id.cpf);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        id = mAuth.getUid();

        salvar.setOnClickListener(v -> salvar_dados());
        speak.setOnClickListener(view -> {
            String text = "Digite Seu CPF ou Clique no Mic e Fale seu CPF";
            t1.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        });
        t1 = new TextToSpeech(CPF.this, i -> {
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
                cpf.setText("");
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
            cpf.setText(result.get(0).toString());
        }
    }
    private void salvar_dados(){
        String cpfs = cpf.getText().toString();
        if (cpfs.isEmpty()) {
            cpf.setError("Por Favor Preencha com o Seu CPF");
            String text2 = "Por Favor Preencha com o Seu CPF";
            t1.speak(text2, TextToSpeech.QUEUE_FLUSH, null);
            return;
        }
        DatabaseReference reference = db.getReference().child("users").child(id).child("Perfil");
        reference.child("CPF").setValue(cpfs);

        Intent perfil = new Intent(CPF.this, Raca.class);
        startActivity(perfil);
    }

    public void onBackPressed() {
        volto();
    }

    private void volto() {
        Intent in = new Intent( CPF.this, Nome.class);
        startActivity(in);
        finish();
    }
}