package com.appdevlab.experiment8;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.appdevlab.experiment8.models.Course;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    TextView welcome,date;

    String []names = {"Mobile App Dev","Cryptography","DWDM","Industrial lecture","Randomized Algos"};
    String []faculties = {"Dr. P. KUMARAN","Dr. C. MALA","Dr. E.SIVA SANKAR","Dr. M. SRIDEVI","Dr. KAMALIKA"};
    int []checkboxes = {R.id.sub1,R.id.sub2,R.id.sub3,R.id.sub4,R.id.sub5};
    ArrayList<String> registered = new ArrayList<>();

    public static String SHARED_PREF = "com.appdevlab.Experiment8";

    Boolean isLogged = false;
    String roll,name;

    SharedPreferences sharedpreferences;
    DatabaseManager databaseManager;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseManager = new DatabaseManager(getApplicationContext());
        databaseManager = databaseManager.open();
//        for(String c_name : names) {
//            Course course = new Course(c_name,"");
//            databaseManager.addCourse(course);
//        }
//        Toast.makeText(getApplicationContext(),"Added new courses",Toast.LENGTH_SHORT).show();
        sharedpreferences = getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        if(sharedpreferences.getString("roll","").isEmpty()) {
            isLogged=false;
        }
        else {
            isLogged=true;
            roll = sharedpreferences.getString("roll","");
            name = sharedpreferences.getString("name","");
        }
        if(isLogged) {
            setContentView(R.layout.activity_home);
            welcome = (TextView) findViewById(R.id.welcome);
            date = (TextView) findViewById(R.id.date);

            String welcomeMsg = "Welcome <b>" + name.toUpperCase() + "</b> ";
            welcome.setText(Html.fromHtml(welcomeMsg));

            date.setText(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));


            ((Button)findViewById(R.id.submitCourses)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    registered = new ArrayList<>();
                    String courseNames="", facultyNames="";
                    for(int i=0;i<checkboxes.length;i++) {
                            if(((CheckBox)findViewById(checkboxes[i])).isChecked()) {
                                if(!registered.contains(names[i])) {
                                    registered.add(names[i]);
                                    if(courseNames.equals("")) {
                                        courseNames = names[i];
                                        facultyNames = faculties[i];
                                    }
                                    else {
                                        courseNames = courseNames  + "," + names[i];
                                        facultyNames = facultyNames + "," + faculties[i];
                                    }
                                }
                            }
                        }
                        if(registered.size()==0)
                            Toast.makeText(getApplicationContext(),"Please select at least one course", Toast.LENGTH_SHORT).show();
                        else {
                            int flag=0;
                            for(String name : registered) {
                                Course course = databaseManager.getCourse(name);
                                if(course.students.equals("")) {
                                    course.students = roll;
                                }
                                else {
                                    course.students = course.students + "," + roll;
                                }
                                Boolean res = databaseManager.updateCourse(course);
                                if(!res) {
                                    flag++;
                                    Toast.makeText(getApplicationContext(),"Failed updating course: " + name,Toast.LENGTH_SHORT).show();
                                }
                            }
                            if(flag==0) {
                                Toast.makeText(getApplicationContext(), "Course Registration Successful. Total courses registered: " + String.valueOf(registered.size()), Toast.LENGTH_SHORT).show();
//
                                Intent intent = new Intent(getApplicationContext(),CompleteRegistration.class);
                                intent.putExtra("name",courseNames);
                                intent.putExtra("faculty",facultyNames);
                                startActivity(intent);
                            }

                        }

                }
            });

        }
        else {
            setContentView(R.layout.activity_main);
            viewPager = (ViewPager) findViewById(R.id.viewpager);
            setupViewPager(viewPager);

            tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.getTabAt(0).setIcon(R.drawable.ic_account_circle_black_24dp);
            tabLayout.getTabAt(1).setIcon(R.drawable.ic_person_add_black_24dp);

            tabLayout.getTabAt(0).getIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
            tabLayout.getTabAt(1).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    tab.getIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
                }

                @SuppressLint("ResourceAsColor")
                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    tab.getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }
    }
//    public void checkbox_clicked(View v) {
//        String c_name = "";
//        for(int i=0;i<checkboxes.length;i++) {
//            if(v.getId()==checkboxes[i]) {
//                c_name = names[i];
//                break;
//            }
//        }
//        if(((CheckBox)v).isChecked()) {
//            Toast.makeText(getApplicationContext(),"Selected " + c_name,Toast.LENGTH_SHORT).show();
//        }
//        else {
//            Toast.makeText(getApplicationContext(),"Unselected " + c_name,Toast.LENGTH_SHORT).show();
//        }
//    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LoginFragment(), "LOGIN");
        adapter.addFragment(new RegisterFragment(), "REGISTER");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.password:
                Toast.makeText(this, "Selected Change Password", Toast.LENGTH_SHORT).show();
                Intent changeIntent = new Intent(getApplicationContext(),ChangePassword.class);
                startActivity(changeIntent);
                return true;
            case R.id.logout:
                Toast.makeText(getApplicationContext(),"Logging out...",Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.remove("roll");
                editor.commit();
                Intent intent = getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                overridePendingTransition(0, 0);
                finish();
                overridePendingTransition(0, 0);
                startActivity(intent);
                return true;
            case R.id.exit:
                Toast.makeText(this, "Selected Exit App", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(isLogged)
        getMenuInflater().inflate(R.menu.list_menu, menu);
        return true;
    }
}