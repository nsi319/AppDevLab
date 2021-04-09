package com.appdevlab.experiment8;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.appdevlab.experiment8.models.Student;

import static com.appdevlab.experiment8.MainActivity.SHARED_PREF;

public class ChangePassword extends AppCompatActivity {
    EditText curr,pass,conPass;
    DatabaseManager databaseManager;
    SharedPreferences sharedPreferences;
    String roll;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        databaseManager = new DatabaseManager(getApplicationContext());
        databaseManager = databaseManager.open();

        sharedPreferences = getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
        roll = sharedPreferences.getString("roll","");

        curr = (EditText) findViewById(R.id.currentPassword);
        pass = (EditText) findViewById(R.id.password);
        conPass = (EditText) findViewById(R.id.confirmPassword);

        ((Button) findViewById(R.id.change)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student = databaseManager.loginStudent(roll,curr.getText().toString());
                if(student.name==null) {
                    Toast.makeText(getApplicationContext(),"Your old password doesn't match with the entered password",Toast.LENGTH_SHORT).show();
                    curr.setText("");
                }
                else {
                    if(!conPass.getText().toString().equals(pass.getText().toString())) {
                        Toast.makeText(getApplicationContext(),"The new passwords do not match",Toast.LENGTH_SHORT).show();
                        conPass.setText("");
                        pass.setText("");
                    }
                    else {
                        student.password = pass.getText().toString();
                        Boolean res = databaseManager.updateStudent(student);
                        if(res) {
                            Toast.makeText(getApplicationContext(),"Password changed successfully",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Password change was unsuccessful",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

    }
}
