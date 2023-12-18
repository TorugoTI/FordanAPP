package com.App.fordan.Salvar;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import com.App.fordan.Perfil.Eu.Raca;
import com.App.fordan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Locale;
import javax.annotation.Nullable;

public class Quilombo extends AppCompatActivity {

    Button salvar;
    ImageButton mic, speak;
    EditText quilombola;
    TextToSpeech t1;
    FirebaseDatabase db;
    FirebaseAuth mAuth;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quilombo);
        getSupportActionBar().hide();

        salvar = findViewById(R.id.salvar);
        mic = findViewById(R.id.mic);
        speak = findViewById(R.id.speak);
        quilombola = findViewById(R.id.quilombola);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        id = mAuth.getUid();

        salvar.setOnClickListener(v -> {
            salvar_dados();
            Intent perfil = new Intent(Quilombo.this, Local_Quilombola.class);
            startActivity(perfil);

        });
        speak.setOnClickListener(view -> {
            String text = "Digite o nome do seu Quilombo ou Clique no Mic e Fale o nome do seu Quilombo";
            t1.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        });
        t1 = new TextToSpeech(Quilombo.this, i -> {
            if(i != TextToSpeech.ERROR)
                t1.setLanguage(new Locale("pt-br", "BR"));
        });
        mic.setOnClickListener(view -> {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Comece a falar");
            startActivityForResult(intent, 100);

        });
    }
    protected void onActivityForResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100 && resultCode == RESULT_OK){
            quilombola.setText(data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0).toString());
        }

    }
    private void salvar_dados(){
        String quilombos = quilombola.getText().toString();
        if (quilombos.isEmpty()) {
            quilombola.setError("Por Favor Preencha com o nome do seu Quilombo");
            String text2 = "Por Favor Preencha com o nome do seu Quilombo";
            t1.speak(text2, TextToSpeech.QUEUE_FLUSH, null);
            return;
        }

        DatabaseReference reference = db.getReference().child("users").child(id);
        reference.child("Quilombo").setValue(quilombos);

        Intent perfil = new Intent(Quilombo.this, Local_Quilombola.class);
        startActivity(perfil);
    }

    public void onBackPressed() {
        volto();
    }

    private void volto() {
        Intent in = new Intent( Quilombo.this, Raca.class);
        startActivity(in);
        finish();
    }
}