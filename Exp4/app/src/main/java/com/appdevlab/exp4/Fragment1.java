package com.appdevlab.exp4;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

public class Fragment1 extends Fragment {
    String name_person;
    public Fragment1() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name_person = getArguments().getString("name");
            Log.d("NAMEPERSON", name_person);
        }
        else {
            Log.d("NAMEPERSON", "null");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView name_text;
        View view = inflater.inflate(R.layout.fragment1, container, false);
        name_text = view.findViewById(R.id.name_course);
        name_text.setText("Welcome " +  name_person + "!");
        Toast.makeText(view.getContext(),"Welcome to department selection (Fragment 1)", Toast.LENGTH_LONG).show();
        return view;
    }
}
