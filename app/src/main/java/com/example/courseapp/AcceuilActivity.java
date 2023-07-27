package com.example.courseapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AcceuilActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    TextView datalist;
    TextView datalist_count;
    TextView textAdmin;
    private StudentAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);
        TextView textAdmin = findViewById(R.id.textViewAdmin);
        TextView textViewDate = findViewById(R.id.textViewDate);
        String nomAdmin = getIntent().getStringExtra("admin");

        // Obtenir la date actuelle
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        // Formatter la date pour l'afficher dans le TextView
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(currentDate);

        // Mettre à jour le texte du TextView avec la date actuelle
        textViewDate.setText(formattedDate);

        textAdmin.setText(nomAdmin);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseHelper = new DatabaseHelper(this,adapter);

        List<StudentModel> studentModelList = databaseHelper.getAllStudents();

        StudentAdapter adapter = new StudentAdapter(studentModelList);
        recyclerView.setAdapter(adapter);

        int textColor = getResources().getColor(R.color.primary); // Couleur du texte des numéros de ligne
        float textSize = getResources().getDimension(R.dimen.line_number_text_size); // Taille du texte des numéros de ligne
        recyclerView.addItemDecoration(new LineNumbersDecoration(textColor, textSize));

        databaseHelper=new DatabaseHelper(AcceuilActivity.this,adapter);
        ImageButton logout = findViewById(R.id.logoutAdmin);
        Button delete=findViewById(R.id.delete_data);
        Button insert=findViewById(R.id.insert_data);
        Button update=findViewById(R.id.update_data);
        datalist_count=findViewById(R.id.data_list_count);

        datalist_count.setText(""+databaseHelper.getTotalCount());

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });


        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowInputDialog();
                datalist_count.setText(""+databaseHelper.getTotalCount());
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateIdDialog();
                datalist_count.setText(""+databaseHelper.getTotalCount());
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
                datalist_count.setText(""+databaseHelper.getTotalCount());
            }
        });

    }


    private void showUpdateIdDialog() {
        AlertDialog.Builder al=new AlertDialog.Builder(AcceuilActivity.this);
        View view=getLayoutInflater().inflate(R.layout.update_id_dialog,null);
        al.setView(view);
        final EditText id_input=view.findViewById(R.id.id_input);
        Button fetch_btn=view.findViewById(R.id.update_id_btn);
        final AlertDialog alertDialog=al.show();
        fetch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataDialog(id_input.getText().toString());
                alertDialog.dismiss();
                datalist_count.setText(""+databaseHelper.getTotalCount());
            }
        });

    }



        private void showDataDialog(final String id) {
        StudentModel studentModel=databaseHelper.getStudent(Integer.parseInt(id));
        if(studentModel != null) {
            AlertDialog.Builder al = new AlertDialog.Builder(AcceuilActivity.this);
            View view = getLayoutInflater().inflate(R.layout.update_dialog, null);
            final EditText username = view.findViewById(R.id.username);
            final EditText email = view.findViewById(R.id.email);
            final EditText domaine = view.findViewById(R.id.domaine);
            final EditText niveau = view.findViewById(R.id.niveau);
            Button update_btn = view.findViewById(R.id.update_btn);
            al.setView(view);

            username.setText(studentModel.getUsername());
            email.setText(studentModel.getEmail());
            domaine.setText(studentModel.getDomaine());
            niveau.setText(studentModel.getNiveau());

            final AlertDialog alertDialog = al.show();
            update_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StudentModel studentModel = new StudentModel();
                    studentModel.setUsername(username.getText().toString());
                    studentModel.setId(id);
                    studentModel.setEmail(email.getText().toString());
                    studentModel.setDomaine(domaine.getText().toString());
                    studentModel.setNiveau(niveau.getText().toString());
                    databaseHelper.updateStudent(AcceuilActivity.this, studentModel);
                    alertDialog.dismiss();
                }
            });
        }
        else {
                // Show a Toast message indicating that the student with the given ID does not exist
                Toast.makeText(AcceuilActivity.this, "Il n'existe pas d'utilisateur avec l'ID : " + id , Toast.LENGTH_SHORT).show();
            }
        }

    private void showDeleteDialog() {
        AlertDialog.Builder al=new AlertDialog.Builder(AcceuilActivity.this);
        View view=getLayoutInflater().inflate(R.layout.delete_dialog,null);
        al.setView(view);
        final EditText id_input=view.findViewById(R.id.id_input);
        Button delete_btn=view.findViewById(R.id.delete_btn);
        final AlertDialog alertDialog=al.show();

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.deleteStudent(AcceuilActivity.this,id_input.getText().toString());
                alertDialog.dismiss();
                datalist_count.setText(""+databaseHelper.getTotalCount());

            }
        });



    }


    private void ShowInputDialog() {
        AlertDialog.Builder al=new AlertDialog.Builder(AcceuilActivity.this);
        View view=getLayoutInflater().inflate(R.layout.insert_dialog,null);
        final EditText username=view.findViewById(R.id.username);
        final EditText email=view.findViewById(R.id.email);
        final EditText domaine=view.findViewById(R.id.domaine);
        final EditText niveau=view.findViewById(R.id.niveau);
        Button insertBtn=view.findViewById(R.id.insert_btn);
        al.setView(view);

        final AlertDialog alertDialog=al.show();

        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentModel studentModel=new StudentModel();
                studentModel.setUsername(username.getText().toString());
                studentModel.setEmail(email.getText().toString());
                studentModel.setDomaine(domaine.getText().toString());
                studentModel.setNiveau(niveau.getText().toString());
                Date date=new Date();
                studentModel.setCreated_at(""+date.getTime());
                databaseHelper.AddStudnet(studentModel);
                alertDialog.dismiss();
                datalist_count.setText(""+databaseHelper.getTotalCount());
            }
        });
    }
}