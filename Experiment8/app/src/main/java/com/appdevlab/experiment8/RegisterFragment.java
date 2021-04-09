package com.appdevlab.experiment8;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.appdevlab.experiment8.models.Student;

public class RegisterFragment extends Fragment {
    DatabaseManager databaseManager;
    EditText name,roll,password,confirmPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.activity_register,null,false);
        databaseManager = new DatabaseManager(root.getContext());
        databaseManager.open();
        name = (EditText) root.findViewById(R.id.name);
        roll = (EditText) root.findViewById(R.id.roll);
        password = (EditText) root.findViewById(R.id.password);
        confirmPassword = (EditText) root.findViewById(R.id.confirmPassword);

        ((Button) root.findViewById(R.id.register)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().isEmpty() || roll.getText().toString().isEmpty() || password.getText().toString().isEmpty() || confirmPassword.getText().toString().isEmpty()) {
                    Toast.makeText(root.getContext(),"Please enter required fields",Toast.LENGTH_SHORT).show();
                }
                else if(!password.getText().toString().equals(confirmPassword.getText().toString())) {
                    Toast.makeText(root.getContext(),"Passwords do not match",Toast.LENGTH_SHORT).show();

                }
                else {
                    Student student = new Student(roll.getText().toString(),name.getText().toString(),password.getText().toString());
                    Boolean result = databaseManager.registerStudent(student);
                    if(result) {
                        Toast.makeText(root.getContext(),"Student Registration Successful",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(root.getContext(),"Student already registered",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        return root;
    }
}
