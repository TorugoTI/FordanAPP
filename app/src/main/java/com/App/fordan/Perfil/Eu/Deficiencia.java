package com.App.fordan.Perfil.Eu;

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

public class Deficiencia extends AppCompatActivity {

    Button sim, nao, salvar;
    EditText def;
    ImageButton speak, mic;
    TextToSpeech t1;
    String defi, usuarioID;
    FirebaseAuth mAuth;
    FirebaseDatabase db;
    String id;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deficiencia);
        getSupportActionBar().hide();

        sim = findViewById(R.id.sim);
        nao = findViewById(R.id.nao);
        def = findViewById(R.id.def);
        mic = findViewById(R.id.mic);
        speak = findViewById(R.id.speak);
        salvar = findViewById(R.id.salvar);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        id = mAuth.getUid();

        nao.setOnClickListener(v -> {
            defi = "Não tenho nenhuma deficiência";
            salvar_dados();
            Intent perfil = new Intent(Deficiencia.this, Meus_Dados.class);
            startActivity(perfil);


        });

        sim.setOnClickListener(v -> {
            def.setVisibility(View.VISIBLE);
            mic.setVisibility(View.VISIBLE);
            salvar.setVisibility(View.VISIBLE);
            defi = def.getText().toString();
        });

        speak.setOnClickListener(view -> {
            String text = "Você possui alguma decifiencia em caso afirmativo, infome qual" ;
            t1.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        });

        t1 = new TextToSpeech(Deficiencia.this, i -> {
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
                def.setText("");
            }catch(ActivityNotFoundException e){
                Toast.makeText(getApplicationContext(),"Seu celular não suporta o texto por voz", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
        salvar.setOnClickListener(view -> {
            Intent perfil = new Intent(Deficiencia.this, Meus_Dados.class);
            startActivity(perfil);
        });
    }
    protected void onActivityForResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK){
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            def.setText(result.get(0).toString());
        }
    }
    private void salvar_dados(){
        DatabaseReference reference = db.getReference().child("users").child(id);
        reference.child("Deficiencia").setValue(defi);
    }
    public void onBackPressed() {
        volto();
    }

    private void volto(){
        Intent in = new Intent( Deficiencia.this, Apoio2.class);
        startActivity(in);
        finish();
    }
}