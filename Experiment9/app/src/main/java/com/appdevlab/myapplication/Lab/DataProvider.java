package com.appdevlab.myapplication.Lab;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.appdevlab.myapplication.Lab.model.Student;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static android.R.id.text1;
import static android.R.layout.simple_list_item_1;

public class DataProvider implements RemoteViewsService.RemoteViewsFactory {

    List<Student> studentList = new ArrayList<>();
    Context mContext = null;
    DatabaseManager databaseManager;

    public DataProvider(Context context, Intent intent) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        initData();
    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return studentList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews view = new RemoteViews(mContext.getPackageName(),
                R.layout.student_list_item);
        String sourceString = "<b>" + studentList.get(position).name + "</b><br>" + studentList.get(position).roll + "<br> <b>" + "CGPA: " + studentList.get(position).grade + "</b>";
        view.setTextViewText(text1, Html.fromHtml(sourceString));


        return view;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private void initData() {
        studentList.clear();
        databaseManager = new DatabaseManager(mContext);
        databaseManager.open();
        List<Student> students = databaseManager.getAllStudents();
        students.sort(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return Integer.parseInt(o1.roll) - Integer.parseInt(o2.roll);
            }
        });
        studentList.addAll(students);

    }
}