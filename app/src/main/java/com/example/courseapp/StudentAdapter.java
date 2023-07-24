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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_user, parent, false);
            return new StudentViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
            StudentModel student = studentList.get(position);

            holder.textViewId.setText("ID: " + student.getId());
            holder.textViewUsername.setText("Username: " + student.getUsername());
            holder.textViewEmail.setText("Email: " + student.getEmail());
            holder.textViewNiveau.setText("Niveau: " + student.getNiveau());
            holder.textViewDomaine.setText("Domaine: " + student.getDomaine());
        }

        @Override
        public int getItemCount() {
            return studentList.size();
        }

        class StudentViewHolder extends RecyclerView.ViewHolder {
            TextView textViewId, textViewUsername, textViewEmail, textViewNiveau, textViewDomaine;

            public StudentViewHolder(@NonNull View itemView) {
                super(itemView);
                textViewId = itemView.findViewById(R.id.textViewId);
                textViewUsername = itemView.findViewById(R.id.textViewUsername);
                textViewEmail = itemView.findViewById(R.id.textViewEmail);
                textViewNiveau = itemView.findViewById(R.id.textViewNiveau);
                textViewDomaine = itemView.findViewById(R.id.textViewDomaine);
            }
        }

        public void updateData(List<StudentModel> newStudentList) {
            this.studentList = newStudentList;
            notifyDataSetChanged();
        }
    }

