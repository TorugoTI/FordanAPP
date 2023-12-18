package com.App.fordan.Salvar;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import com.App.fordan.Perfil.Eu.Escolaridade;
import com.App.fordan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Locale;
import javax.annotation.Nullable;

public class Local_Quilombola extends AppCompatActivity {

    Button salvar;
    ImageButton mic, speak;
    EditText local;
    TextToSpeech t1;
    FirebaseDatabase db;
    FirebaseAuth mAuth;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_quilombola);
        getSupportActionBar().hide();

        salvar = findViewById(R.id.salvar);
        mic = findViewById(R.id.mic);
        speak = findViewById(R.id.speak);
        local = findViewById(R.id.Local);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        id = mAuth.getUid();

        salvar.setOnClickListener(v -> {
            salvar_dados();
            Intent perfil = new Intent(Local_Quilombola.this, Escolaridade.class);
            startActivity(perfil);

        });
        speak.setOnClickListener(view -> {
            String text = "Digite a localização do seu Quilombo ou Clique no Mic e Fale a localização do seu Quilombo";
            t1.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        });
        t1 = new TextToSpeech(Local_Quilombola.this, i -> {
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
            local.setText(data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0).toString());
        }

    }
    private void salvar_dados(){
        String locos = local.getText().toString();
        if (locos.isEmpty()) {
            local.setError("Por Favor Preencha com a localização do seu Quilombo");
            String text2 = "Por Favor Preencha com a localização do seu Quilombo";
            t1.speak(text2, TextToSpeech.QUEUE_FLUSH, null);
            return;
        }

        DatabaseReference reference = db.getReference().child("users").child(id);
        reference.child("CEP").setValue(locos);

        Intent perfil = new Intent(Local_Quilombola.this, Escolaridade.class);
        startActivity(perfil);
    }

    public void onBackPressed() {
        volto();
    }

    private void volto() {
        Intent in = new Intent( Local_Quilombola.this, Quilombo.class);
        startActivity(in);
        finish();
    }
}