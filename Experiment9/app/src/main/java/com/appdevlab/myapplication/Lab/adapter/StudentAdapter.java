package com.appdevlab.myapplication.Lab.adapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


import com.appdevlab.myapplication.Lab.R;
import com.appdevlab.myapplication.Lab.model.Student;

import java.util.ArrayList;
import java.util.List;
public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder>{
    private List<Student> students;

    public StudentAdapter(List<Student> students) {
        this.students = students;
    }
    public void filterList(List<Student> filterList) {
        students = filterList;
        notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.student_details_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Student student = students.get(position);
        holder.name.setText(student.name);
        holder.roll.setText(student.roll);
        holder.grade.setText("CGPA" + "\n" + student.grade);
    }
    @Override
    public int getItemCount() {
        return students.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name,roll,grade;

        public ViewHolder(View itemView) {
            super(itemView);
            this.name =  itemView.findViewById(R.id.name);
            this.roll = (TextView) itemView.findViewById(R.id.roll);
            this.grade = (TextView) itemView.findViewById(R.id.grade);
        }
    }
}