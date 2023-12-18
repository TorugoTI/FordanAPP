package com.App.fordan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.App.fordan.Salvar.Login_google;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    Button login, registrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        login = findViewById(R.id.Login);
        registrar = findViewById(R.id.Registrar);

        login.setOnClickListener(view -> logar());

        registrar.setOnClickListener(view -> registrar());
    }

    protected void onStart() {
        super.onStart();

        FirebaseUser usuarioAtual = FirebaseAuth.getInstance().getCurrentUser();

        if (usuarioAtual != null){
            telaPrincipal();
        }
    }
    private void telaPrincipal(){
        Intent novo = new Intent(MainActivity.this,Tela_fordan.class);
        startActivity(novo);
        finish();
    }

    private void logar(){
        Intent logando = new Intent(MainActivity.this,Login_google.class);
        startActivity(logando);
        finish();
        Log.d("Erro:", logando.toString());
    }

    private void registrar(){
        Intent registrando = new Intent(MainActivity.this,Registro.class);
        startActivity(registrando);
        finish();
        Log.d("Erro:", registrando.toString());
    }


}
