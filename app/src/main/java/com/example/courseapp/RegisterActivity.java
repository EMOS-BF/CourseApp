package com.example.courseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.os.Bundle;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    // Déclaration des variables des éléments d'interface utilisateur
    EditText username, email, password, repassword;
    Button button_inscription, button_connexion;
    DatabaseHelper DB;
    private StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialisation des éléments d'interface utilisateur
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);
        button_inscription = findViewById(R.id.button_inscription);
        button_connexion = findViewById(R.id.button_connexion);
        DB = new DatabaseHelper(getApplicationContext(), adapter);

        button_inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String mail = email.getText().toString();
                String mdp = password.getText().toString();
                String repass = repassword.getText().toString();

                if (user.equals("") || mail.equals("") || mdp.equals("") || repass.equals("")) {
                    Toast.makeText(RegisterActivity.this, "Remplissez tous les champs", Toast.LENGTH_SHORT).show();
                } else {
                    if (mdp.equals(repass)) {
                        Boolean checkuser = DB.checkusername(user);
                        if (!checkuser) {
                            Boolean insert = DB.insertData(user, mail, mdp);
                            if (insert) {
                                // Si l'inscription est réussie, afficher un message de succès et lancer l'activité AcceuilActivity
                                Toast.makeText(RegisterActivity.this, "Inscription réussie", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), AcceuilActivity.class);
                                intent.putExtra("admin", user);
                                startActivity(intent);
                            } else {
                                Toast.makeText(RegisterActivity.this, "Échec de l'inscription", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RegisterActivity.this, "L'utilisateur existe déjà! Connectez-vous svp", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        button_connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lancer l'activité MainActivity lorsque le bouton de connexion est cliqué
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
