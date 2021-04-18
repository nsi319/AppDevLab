package com.appdevlab.myapplication.Lab;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.Toast;

import com.appdevlab.myapplication.Lab.adapter.StudentAdapter;
import com.appdevlab.myapplication.Lab.model.Student;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AllStudents extends AppCompatActivity {
    private DatabaseManager databaseManager;
    List<Student> students;
    StudentAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_students);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        databaseManager = new DatabaseManager(getApplicationContext());
        databaseManager.open();
        setTitle("Complete Student List");
        students = databaseManager.getAllStudents();
        students.sort(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return Integer.parseInt(o1.roll) - Integer.parseInt(o2.roll);
            }
        });
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new StudentAdapter(students);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
        return true;
    }
    private void filter(String text) {
        List<Student> filterList = new ArrayList<>();
        for (Student item : students)
            if (item.roll.contains(text.toLowerCase()) || item.name.contains(text.toLowerCase()))
                filterList.add(item);
        if (filterList.isEmpty())
            Toast.makeText(this, "No matches found", Toast.LENGTH_SHORT).show();
        else
            adapter.filterList(filterList);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(getApplicationContext(),"Back to Home Screen via Back Button", Toast.LENGTH_SHORT).show();
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}