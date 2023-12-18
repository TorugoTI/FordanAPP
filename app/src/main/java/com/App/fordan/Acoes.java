package com.App.fordan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.App.fordan.Pensão.pg1;

public class Acoes extends AppCompatActivity {

    Button voltar;
    Button btn_policia, btn_Boletim, btn_Medida, btn_Pensao, speak;
    @SuppressLint("WrongViewCast")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acoes);
        getSupportActionBar().hide();

        btn_Pensao = findViewById(R.id.btn_Pensao);
        btn_Boletim = findViewById(R.id.btn_Boletim);
        btn_Medida = findViewById(R.id.btn_Medida);
        btn_policia = findViewById(R.id.btn_policia);
        voltar = findViewById(R.id.voltar);

        voltar.setOnClickListener(view -> volto());

        btn_Boletim.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://delegaciaonline.sesp.es.gov.br/deon/xhtml/solicitarregistroocorrencia.jsf"))));

        btn_policia.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/556196100180?text=oi"))));
        btn_Medida.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/forms/d/e/1FAIpQLScFalMbrAHuZLkXssST8SnGkbOqWDTUO8r3yZPeB8U2J9D41g/viewform"))));
        btn_Pensao.setOnClickListener(view -> startActivity(new Intent(Acoes.this, pg1.class)));

    }
    public void onBackPressed() {
        volto();
    }

    private void volto() {
        Intent in = new Intent( Acoes.this, Tela_fordan.class);
        startActivity(in);
        finish();
    }

}