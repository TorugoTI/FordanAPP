package com.App.fordan.Pensão;

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
import com.App.fordan.Acoes;
import com.App.fordan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.Locale;

public class pg12 extends AppCompatActivity {
    Button salvar;
    ImageButton mic, speak;
    EditText pgt;
    TextToSpeech t1;
    FirebaseAuth mAuth;
    FirebaseDatabase db;
    String id;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pg12);
        getSupportActionBar().hide();

        salvar = findViewById(R.id.salvar);
        mic = findViewById(R.id.mic);
        speak = findViewById(R.id.speak);
        pgt = findViewById(R.id.pgt);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        id = mAuth.getUid();

        salvar.setOnClickListener(v -> salvar_dados());
        speak.setOnClickListener(view -> {
            String text = "Deseja regularizar a guarda e visitação do(a) genitor(a) ao menor? Em caso positivo, indicar o dia da semana para buscar e o dia para entregar, a periodicidade disso (de quanto em quanto tempo: toda semana, de 15 em 15 dias, mensal etc.), os horários de busca e entrega, quem vai buscar e quem vai entregar.\n" +
                    "Como pretende que seja regulada a convivência em datas comemorativas como carnaval, natal, ano novo, aniversário etc.";
            t1.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        });
        t1 = new TextToSpeech(pg12.this, i -> {
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

    }
    protected void onActivityForResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK){
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            pgt.setText(result.get(0));
        }
    }
    private void salvar_dados(){
        String Pgt = pgt.getText().toString();
        if (Pgt.isEmpty()) {
            pgt.setError("Por Favor Preencha com cuidado");
            String text2 = "Por Favor Preencha com cuidado";
            t1.speak(text2, TextToSpeech.QUEUE_FLUSH, null);
            return;
        }

        DatabaseReference reference = db.getReference().child("users").child(id).child("Pensão");
        reference.child("PGT12").setValue(Pgt);

        Intent perfil = new Intent(pg12.this, Acoes.class);
        startActivity(perfil);
    }

    public void onBackPressed() {
        volto();
    }

    private void volto() {
        Intent in = new Intent( pg12.this, pg11.class);
        startActivity(in);
        finish();
    }
}