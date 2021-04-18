package com.appdevlab.myapplication.Lab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appdevlab.myapplication.Lab.model.Student;

public class MainActivity extends AppCompatActivity {

    DatabaseManager databaseManager;

    TextView roll,name,grade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        roll = findViewById(R.id.roll);
        name = findViewById(R.id.name);
        grade = findViewById(R.id.grade);

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(roll.getText().equals("") || grade.getText().equals("") || name.getText().equals(""))
                    Toast.makeText(getApplicationContext(),"Please enter required fields", Toast.LENGTH_SHORT).show();
                else {
                    databaseManager = new DatabaseManager(getApplicationContext());
                    databaseManager.open();

                    Student student = new Student(roll.getText().toString(),name.getText().toString(),grade.getText().toString());

                    Boolean res = databaseManager.addStudent(student);
                    if(res) {
                        Toast.makeText(getApplicationContext(), "New Student: " + name.getText().toString() + " added successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,AllStudents.class);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(getApplicationContext(),"New Student addition unsuccessful",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.all:
                Toast.makeText(this, "Selected Show All Students", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,AllStudents.class);
                startActivity(intent);
                return true;
            case R.id.edit:
                Toast.makeText(this, "Selected Edit Details", Toast.LENGTH_SHORT).show();
                Intent editIntent = new Intent(MainActivity.this, EditStudent.class);
                startActivity(editIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.student_menu, menu);
        return true;
    }
}
