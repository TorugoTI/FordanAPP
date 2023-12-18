package com.App.fordan.Perfil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.App.fordan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Familia extends AppCompatActivity {
    private Button editar, voltar;
    private TextView nome;
    FirebaseDatabase db;
    FirebaseAuth mAuth;
    String id;
    String[] itens = {"item 1", "item 2", "item 3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_familia);
        getSupportActionBar().hide();
        iniciar_Components();

        mAuth = FirebaseAuth.getInstance();

        editar.setOnClickListener(v -> perfil1());

        voltar.setOnClickListener(v -> {
            Intent perfil2=new Intent(Familia.this, Perfil.class);
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
                String Name = snapshot.child("Familiares").getValue(String.class);

                nome.setText(Name);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void iniciar_Components(){
        editar = findViewById(R.id.editar);
        nome = findViewById(R.id.nome);
        voltar = findViewById(R.id.voltar);

    }
    private void perfil1(){
        Intent perfil1=new Intent(Familia.this, membro_familia.class);
        startActivity(perfil1);
        finish();
    }

}