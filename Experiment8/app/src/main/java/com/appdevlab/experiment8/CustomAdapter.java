package com.appdevlab.experiment8;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.zip.Inflater;

import static android.view.View.GONE;

public class CustomAdapter extends BaseAdapter {
    Context context;
    String courseNames[], facultyNames[];
    int studentCount[];
    LayoutInflater infalter;
    int flag=0;


    public CustomAdapter(Context context, String[] courseNames, String[] facultyNames, int[] studentCount, int flag) {
        this.context = context;
        this.courseNames = courseNames;
        this.facultyNames = facultyNames;
        this.studentCount = studentCount;
        infalter = (LayoutInflater.from(context));
        this.flag = flag;

    }

    @Override
    public int getCount() {
        return courseNames.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, final View v, ViewGroup viewGroup) {
        ImageButton imageButton;
        final View view = infalter.inflate(R.layout.course_layout, null);
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView fac = (TextView) view.findViewById(R.id.faculty);
        TextView count = (TextView) view.findViewById(R.id.count);
        final String c_name = courseNames[i];
        final String f_name = facultyNames[i];
        name.setText(courseNames[i]);
        fac.setText(facultyNames[i]);
        imageButton = view.findViewById(R.id.students);
        if(flag==1) {
            count.setText(String.valueOf(studentCount[i]));
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View vi) {
                    Intent intent = new Intent(view.getContext(), StudentList.class);
                    intent.putExtra("name", c_name);
                    intent.putExtra("faculty",f_name);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    v.getContext().startActivity(intent);
                }
            });
        }
        else {
            count.setVisibility(GONE);
            imageButton.setVisibility(GONE);
        }
        return view;
    }
}