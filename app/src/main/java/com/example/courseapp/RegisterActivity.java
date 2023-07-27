package com.example.courseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.os.Bundle;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText username, email, password,repassword;
    Button button_inscription, button_connexion;
    DatabaseHelper DB;
    private StudentAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        repassword = (EditText) findViewById(R.id.repassword);
        button_inscription =(Button) findViewById(R.id.button_inscription);
        button_connexion = (Button) findViewById(R.id.button_connexion);
        DB = new DatabaseHelper(getApplicationContext(),adapter);


        button_inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String mail = email.getText().toString();
                String mdp = password.getText().toString();
                String repass = repassword.getText().toString();

                if (user.equals("")||mail.equals("")||mdp.equals("")||repass.equals("")){
                    Toast.makeText(RegisterActivity.this, "Remplisez Tout les champs", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(mdp.equals(repass)){
                        Boolean checkuser = DB.checkusername(user);
                        if(checkuser == false){
                            Boolean insert = DB.insertData(user,mail,mdp);
                            if (insert = true){
                                Toast.makeText(RegisterActivity.this, "Inscription Réussie", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),AcceuilActivity.class);
                                intent.putExtra("admin", user);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(RegisterActivity.this, "Echec de l'inscription", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "l'utilisateur existe déja! Connectez vous svp", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        button_connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });
    }
}