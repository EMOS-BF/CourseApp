package com.example.courseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Déclaration des variables des éléments d'interface utilisateur
    EditText username, password;
    Button button_connexion, button_inscription;
    private StudentAdapter adapter;
    DatabaseHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des éléments d'interface utilisateur
        username = findViewById(R.id.username_log);
        password = findViewById(R.id.password_log);
        button_connexion = findViewById(R.id.button_connexion_log);
        button_inscription = findViewById(R.id.button_inscription_log);

        // Initialisation de la base de données et de l'adaptateur
        DB = new DatabaseHelper(this, adapter);

        button_connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (user.equals("") || pass.equals("")) {
                    Toast.makeText(MainActivity.this, "Veuillez entrer tous les champs!", Toast.LENGTH_SHORT).show();
                } else {
                    // Vérification des identifiants dans la base de données
                    Boolean checkuserpass = DB.checkusernamepassword(user, pass);
                    if (checkuserpass) {
                        // Si les identifiants sont valides, afficher un message de connexion réussie et lancer l'activité AcceuilActivity
                        Toast.makeText(MainActivity.this, "Connexion réussie", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), AcceuilActivity.class);
                        intent.putExtra("admin", user);
                        startActivity(intent);
                    } else {
                        // Si les identifiants sont invalides, afficher un message d'erreur
                        Toast.makeText(MainActivity.this, "Identifiants invalides!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        button_inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lancer l'activité d'inscription (RegisterActivity) lorsque le bouton d'inscription est cliqué
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

}
