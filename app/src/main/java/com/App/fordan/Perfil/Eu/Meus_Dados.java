package com.App.fordan.Perfil.Eu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.App.fordan.Perfil.Perfil;
import com.App.fordan.Perfil.editar.Nome_Editar;
import com.App.fordan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Meus_Dados extends AppCompatActivity {
    private Button editar;
    private Button voltar;
    private TextView nome, CPF, Cep, Raca, Orientacao, Escola, Religião;
    FirebaseDatabase db;
    FirebaseAuth mAuth;
    String id;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meusdados);
        getSupportActionBar().hide();
        iniciar_Components();

        mAuth = FirebaseAuth.getInstance();
        editar.setOnClickListener(v -> {
            Intent perfil1 = new Intent(Meus_Dados.this, Nome_Editar.class);
            startActivity(perfil1);
            finish();
        });

        voltar.setOnClickListener(v -> {
            Intent perfil2 = new Intent(Meus_Dados.this, Perfil.class);
            startActivity(perfil2);
            finish();
        });

        db = FirebaseDatabase.getInstance();
        id = mAuth.getUid();
        Perfil();
    }
    private void Perfil(){
        DatabaseReference reference = db.getReference().child("users").child(id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Name = snapshot.child("Nome").getValue(String.class);
                String Cpf = snapshot.child("CPF").getValue(String.class);
                String CEP = snapshot.child("CEP").getValue(String.class);
                String Esco = snapshot.child("Escolaridade").getValue(String.class);
                String Ori = snapshot.child("Orientação-Sexual").getValue(String.class);
                String Raça = snapshot.child("Raça").getValue(String.class);
                String Reli = snapshot.child("Religião").getValue(String.class);

                nome.setText(Name);
                CPF.setText(Cpf);
                Cep.setText(CEP);
                Escola.setText(Esco);
                Orientacao.setText(Ori);
                Raca.setText(Raça);
                Religião.setText(Reli);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void onBackPressed() {
        Intent perfil2 = new Intent(Meus_Dados.this, Perfil.class);
        startActivity(perfil2);
        finish();
    }
    private void iniciar_Components(){
        editar = findViewById(R.id.editar);
        voltar = findViewById(R.id.voltar);
        nome = findViewById(R.id.nome);
        CPF = findViewById(R.id.CPF);
        Cep = findViewById(R.id.Cep);
        Escola = findViewById(R.id.Escola);
        Religião = findViewById(R.id.Religião);
        Raca = findViewById(R.id.Raca);
        Orientacao = findViewById(R.id.Orientacao);
    }
}