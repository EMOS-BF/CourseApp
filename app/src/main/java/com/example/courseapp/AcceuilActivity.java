package com.example.courseapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

public class AcceuilActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    TextView datalist;
    TextView datalist_count;
    private StudentAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseHelper = new DatabaseHelper(this,adapter);

        List<StudentModel> studentModelList = databaseHelper.getAllStudents();

        // Créez un adaptateur personnalisé pour lier les données à la vue de chaque élément
        StudentAdapter adapter = new StudentAdapter(studentModelList);
        recyclerView.setAdapter(adapter);


        databaseHelper=new DatabaseHelper(AcceuilActivity.this,adapter);
        Button delete=findViewById(R.id.delete_data);
        Button insert=findViewById(R.id.insert_data);
        Button update=findViewById(R.id.update_data);
        Button read=findViewById(R.id.refresh_data);
        datalist=findViewById(R.id.all_data_list);
        datalist_count=findViewById(R.id.data_list_count);

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //refreshData();

            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowInputDialog();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateIdDialog();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
            }
        });

    }

    private void refreshData(StudentModel newStudent) {
        List<StudentModel> studentModelList = databaseHelper.getAllStudents();
        StudentAdapter adapter = new StudentAdapter(studentModelList);
        // Ajoutez le nouvel utilisateur à la base de données
        databaseHelper.AddStudnet(newStudent);

        // Obtenez la liste mise à jour des étudiants depuis la base de données
        List<StudentModel> updatedStudentList = databaseHelper.getAllStudents();

        // Mettez à jour les données de l'adaptateur avec la liste mise à jour
        adapter.updateData(updatedStudentList);
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
               // refreshData();
            }
        });

    }



        private void showDataDialog(final String id) {
        StudentModel studentModel=databaseHelper.getStudent(Integer.parseInt(id));
        AlertDialog.Builder al=new AlertDialog.Builder(AcceuilActivity.this);
        View view=getLayoutInflater().inflate(R.layout.update_dialog,null);
        final EditText username=view.findViewById(R.id.username);
        final EditText email=view.findViewById(R.id.email);
        final EditText domaine=view.findViewById(R.id.domaine);
        final EditText niveau=view.findViewById(R.id.niveau);
        Button update_btn=view.findViewById(R.id.update_btn);
        al.setView(view);

        username.setText(studentModel.getUsername());
        email.setText(studentModel.getEmail());
        domaine.setText(studentModel.getDomaine());
        niveau.setText(studentModel.getNiveau());

        final AlertDialog alertDialog=al.show();
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentModel studentModel=new StudentModel();
                studentModel.setUsername(username.getText().toString());
                studentModel.setId(id);
                studentModel.setEmail(email.getText().toString());
                studentModel.setDomaine(domaine.getText().toString());
                studentModel.setNiveau(niveau.getText().toString());
                databaseHelper.updateStudent(studentModel);
                alertDialog.dismiss();
                //refreshData();
            }
        });
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
                databaseHelper.deleteStudent(id_input.getText().toString());
                alertDialog.dismiss();
                //refreshData();

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
                //refreshData();
            }
        });
    }
}