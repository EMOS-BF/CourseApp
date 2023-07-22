package com.example.courseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username, password;
    Button button_connexion, button_inscription;
    DatabaseHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username =(EditText) findViewById(R.id.username_log);
        password = (EditText) findViewById(R.id.password_log);
        button_connexion = (Button) findViewById(R.id.button_connexion_log);
        button_inscription = (Button) findViewById(R.id.button_inscription_log);
        DB = new DatabaseHelper(this);


        button_connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(user.equals("")||pass.equals(""))
                    Toast.makeText(MainActivity.this, "Entrez tout les champs svp", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkuserpass = DB.checkusernamepassword(user, pass);
                    if(checkuserpass==true){
                        Toast.makeText(MainActivity.this, "Connexion réussie", Toast.LENGTH_SHORT).show();
                        Intent intent  = new Intent(getApplicationContext(),AcceuilActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(MainActivity.this, "Identifiants invalides!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        button_inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent  = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);

            }
        });

    }


}