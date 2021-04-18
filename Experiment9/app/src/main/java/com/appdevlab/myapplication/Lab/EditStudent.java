package com.appdevlab.myapplication.Lab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.appdevlab.myapplication.Lab.model.Student;

import static android.view.View.GONE;

public class EditStudent extends AppCompatActivity {

    EditText name,roll,grade;
    Button update, find;
    int found = -1;
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Edit Student");
        roll = findViewById(R.id.roll);
        name = findViewById(R.id.name);
        grade = findViewById(R.id.grade);

        if(found==-1) {
            findViewById(R.id.nameLayout).setVisibility(GONE);
            findViewById(R.id.nameLayout).setVisibility(GONE);
        }
        else {
            findViewById(R.id.nameLayout).setVisibility(View.VISIBLE);
            findViewById(R.id.nameLayout).setVisibility(View.VISIBLE);
        }

        findViewById(R.id.find).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
                databaseManager.open();

                student = databaseManager.getStudent(roll.getText().toString());
                if(student==null) {
                    Toast.makeText(getApplicationContext(),"Student: " + roll.getText().toString() + " not found",Toast.LENGTH_SHORT).show();
                    found=-1;
                }
                else {
                    Toast.makeText(getApplicationContext(),"Student: " + roll.getText().toString() + " found!",Toast.LENGTH_SHORT).show();
                    roll.setEnabled(false);
                    found=1;
                    findViewById(R.id.nameLayout).setVisibility(View.VISIBLE);
                    findViewById(R.id.gradeLayout).setVisibility(View.VISIBLE);
                    findViewById(R.id.updateCard).setVisibility(View.VISIBLE);
                    findViewById(R.id.findCard).setVisibility(GONE);
                    name.setText(student.name);
                    grade.setText(student.grade);
                    roll.setText(student.roll);
                }
            }
        });

        findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(roll.getText().equals("") || grade.getText().equals("") || name.getText().equals(""))
                    Toast.makeText(getApplicationContext(),"Please enter required fields", Toast.LENGTH_SHORT).show();
                else {
                    DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
                    databaseManager.open();

                    Student student = new Student(roll.getText().toString(),name.getText().toString(),grade.getText().toString());
                    Boolean res = databaseManager.updateStudent(student);

                    if(res) {
                        Toast.makeText(getApplicationContext(), "Student: " + roll.getText().toString() + " updated successfully", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(getApplicationContext(),"Student updation failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(getApplicationContext(),"Back to Home Screen via Back Button", Toast.LENGTH_SHORT).show();
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
