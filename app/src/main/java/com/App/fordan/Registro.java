package com.App.fordan;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.App.fordan.Perfil.Eu.CPF;
import com.App.fordan.Perfil.Eu.Nome;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Registro extends AppCompatActivity {
    ImageButton google;
    FirebaseAuth mAuth;
    FirebaseDatabase db;
    int RC_SIGN_IN = 20;
    GoogleSignInClient googleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        getSupportActionBar().hide();

        google = findViewById(R.id.google);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance(Uri.parse("https://fordan-89ac5-default-rtdb.firebaseio.com/").toString());

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();

        googleSignInClient = GoogleSignIn.getClient(this,gso);

        google.setOnClickListener(view -> googleSingIn());
    }

    private void googleSingIn(){

        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try{

                GoogleSignInAccount account = task.getResult(ApiException.class);
                firbaseAuth(account.getIdToken());

            }catch (Exception e){

                Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }
    }
    private void firbaseAuth(String idToken){

        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()){

                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {

                            usuarioAtual();

                        }

                    }else{
                        Toast.makeText(Registro.this,"Algo deu Errado", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void usuarioAtual(){

        FirebaseUser user = mAuth.getCurrentUser();
        DatabaseReference data = db.getReference().child("users").child(user.getUid());
        if (data == null){

            HashMap<String, Object> users = new HashMap<>();
            users.put("id", user.getUid());
            users.put("Nome", null);
            users.put("CPF", null);
            db.getReference().child("users").child(user.getUid()).child("Perfil").setValue(users);

            Intent intent = new Intent(Registro.this, Nome.class);
            startActivity(intent);

        } else{

            DatabaseReference se = db.getReference().child("users").child(user.getUid()).child("Perfil");

            if (se.child("Nome") == null ){

                Intent intent = new Intent(Registro.this, Nome.class);
                startActivity(intent);

            }else if (se.child("CPF") == null){

                Intent intent = new Intent(Registro.this, CPF.class);
                startActivity(intent);

            }else{

                Intent in = new Intent( Registro.this, Tela_fordan.class);
                startActivity(in);
                finish();

            }
        }
    }

    public void onBackPressed() {
        volto();
    }
    private void volto() {
        Intent in = new Intent( Registro.this, MainActivity.class);
        startActivity(in);
        finish();
    }
}