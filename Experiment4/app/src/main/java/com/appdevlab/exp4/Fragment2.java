package com.appdevlab.exp4;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fragment2 extends Fragment {
    String result;
    public Fragment2(String value) {
        result = value;
    }
    public Fragment2() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView tv1;
        View view = inflater.inflate(R.layout.fragment2, container, false);
        tv1 = view.findViewById(R.id.main_output);
        tv1.setText(result);
        return view;
    }
}