package com.example.courseapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private List<StudentModel> studentList;

    public StudentAdapter(List<StudentModel> studentList) {
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Créer la vue pour chaque élément de la liste à partir du layout list_item_user.xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_user, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        // Remplir les éléments de l'interface utilisateur avec les données du modèle d'étudiant (StudentModel)
        StudentModel student = studentList.get(position);

        holder.textViewId.setText("ID: " + student.getId());
        holder.textViewUsername.setText("Username: " + student.getUsername());
        holder.textViewEmail.setText("Email: " + student.getEmail());
        holder.textViewNiveau.setText("Niveau: " + student.getNiveau());
        holder.textViewDomaine.setText("Domaine: " + student.getDomaine());
    }

    @Override
    public int getItemCount() {
        // Retourner le nombre d'éléments dans la liste
        return studentList.size();
    }

    // Classe interne pour contenir les vues de chaque élément de la liste
    class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView textViewId, textViewUsername, textViewEmail, textViewNiveau, textViewDomaine;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialiser les éléments d'interface utilisateur pour chaque élément de la liste
            textViewId = itemView.findViewById(R.id.textViewId);
            textViewUsername = itemView.findViewById(R.id.textViewUsername);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewNiveau = itemView.findViewById(R.id.textViewNiveau);
            textViewDomaine = itemView.findViewById(R.id.textViewDomaine);
        }
    }

    // Méthode pour mettre à jour les données de la liste avec une nouvelle liste d'étudiants
    public void updateData(List<StudentModel> newStudentList) {
        this.studentList = newStudentList;
        notifyDataSetChanged();
    }
}
