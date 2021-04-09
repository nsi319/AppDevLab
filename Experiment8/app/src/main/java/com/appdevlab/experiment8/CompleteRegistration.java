package com.appdevlab.experiment8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.ListView;
import android.widget.TextView;

import com.appdevlab.experiment8.models.Course;

import java.util.ArrayList;

public class CompleteRegistration extends AppCompatActivity {
    DatabaseManager databaseManager;
    ListView courseList;
    String[] courseNames, facultyNames;
    int[] studentCount;
    ArrayList<ArrayList<String>> studentList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_registration);
        courseList = (ListView) findViewById(R.id.courseList);
        String result = "Course Registration Completed. <br><br> Chosen courses for this semester are: <br> ";
        ((TextView) findViewById(R.id.result)).setText(Html.fromHtml(result));

        databaseManager = new DatabaseManager(getApplicationContext());
        databaseManager = databaseManager.open();

        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        String faculty = intent.getStringExtra("faculty");
        courseNames = name.split(",");
        facultyNames = faculty.split(",");

        studentCount = new int[courseNames.length];
        for(int i=0;i<courseNames.length;i++) {
            Course course = databaseManager.getCourse(courseNames[i]);
            studentCount[i] = course.students.split(",").length;
        }
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(),courseNames,facultyNames,studentCount,1);
        courseList.setAdapter(customAdapter);
    }
}
