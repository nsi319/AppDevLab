package com.appdevlab.experiment8;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import static com.appdevlab.experiment8.MainActivity.SHARED_PREF;

public class LoginFragment extends Fragment {

    DatabaseManager databaseManager;
    EditText roll,password;
    SharedPreferences sharedpreferences;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.activity_login,null,false);
        sharedpreferences = this.getActivity().getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedpreferences.edit();

        databaseManager = new DatabaseManager(root.getContext());
        databaseManager.open();
        roll = (EditText) root.findViewById(R.id.roll);
        password = (EditText) root.findViewById(R.id.password);

        ((Button) root.findViewById(R.id.login)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(roll.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                    Toast.makeText(root.getContext(),"Please enter required fields",Toast.LENGTH_SHORT).show();
                }
                else {
                    Student student = databaseManager.loginStudent(roll.getText().toString(),password.getText().toString());
                    if(student.name!=null) {
                        Toast.makeText(root.getContext(),"Login Successful",Toast.LENGTH_SHORT).show();

                        editor.putString("roll", student.roll);
                        editor.putString("name",student.name);
                        editor.commit();

                        Intent intent = getActivity().getIntent();
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        getActivity().overridePendingTransition(0, 0);
                        getActivity().finish();

                        getActivity().overridePendingTransition(0, 0);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(root.getContext(),"Login Unsuccessful",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        return root;
    }
}
