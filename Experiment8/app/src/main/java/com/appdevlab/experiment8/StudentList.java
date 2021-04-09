package com.appdevlab.experiment8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.widget.ListView;
import android.widget.TextView;

import com.appdevlab.experiment8.models.Course;
import com.appdevlab.experiment8.models.Student;

import static com.appdevlab.experiment8.MainActivity.SHARED_PREF;

public class StudentList extends AppCompatActivity {

    DatabaseManager databaseManager;
    String name,fac;
    String[] studentNames, rollNumbers;
    SharedPreferences sharedPreferences;
    String roll;
    ListView studentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        studentList = (ListView) findViewById(R.id.studentList);
        sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        roll = sharedPreferences.getString("roll","");

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        fac = intent.getStringExtra("faculty");

        setTitle(name + " Student List");

        databaseManager = new DatabaseManager(getApplicationContext());
        databaseManager = databaseManager.open();

        Course course = databaseManager.getCourse(name);
        rollNumbers = course.students.split(",");
        studentNames = new String[rollNumbers.length];
        for(int i=0;i<rollNumbers.length;i++) {
            Student student = databaseManager.getStudent(rollNumbers[i]);
            studentNames[i]=student.name;
            if(rollNumbers[i].equals(roll)) {
                studentNames[i] = studentNames[i] + " (YOU)";
            }
        }
        String result =  "Course Name: <b> " + name + " </b> <br> Course Faculty: <b> " + fac + " </b> <br> Total count: <b> " + rollNumbers.length + " </b> <br><br>" + "Student List: ";
        ((TextView) findViewById(R.id.result)).setText(Html.fromHtml(result));

        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(),studentNames,rollNumbers,null,2);
        studentList.setAdapter(customAdapter);


    }
}
