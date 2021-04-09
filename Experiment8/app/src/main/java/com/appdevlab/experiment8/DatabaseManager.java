package com.appdevlab.experiment8;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;


import com.appdevlab.experiment8.models.Course;
import com.appdevlab.experiment8.models.Student;

import java.util.ArrayList;
import java.util.List;

import static com.appdevlab.experiment8.StudentDatabase.*;
public class DatabaseManager {
    private StudentDatabase dbHelper;
    private Context context;
    private SQLiteDatabase database;
    public DatabaseManager(Context c) {
        context = c;
    }
    public DatabaseManager open() throws SQLException {
        dbHelper = new StudentDatabase(context);
        database = dbHelper.getWritableDatabase();
//        dbHelper.onUpgrade(database,1,2);
//        Toast.makeText(context,"Deleted old db",Toast.LENGTH_SHORT).show();
        return this;
    }
    public void close() {
        dbHelper.close();
    }
    public boolean addCourse(Course course) {
        database.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_COURSE_NAME, course.name);
            values.put(KEY_COURSE_STUDENT_REGISTERED, course.students);

            database.insertOrThrow(TABLE_COURSE, null, values);
            database.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("DBINSERTERROR", "Error while trying to add team to database");
            return false;
        } finally {
            database.endTransaction();
        }
        return true;
    }

    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();

        String GET_ALL_COURSES =
                String.format("SELECT * FROM %s",
                        TABLE_COURSE);

        Cursor cursor = database.rawQuery(GET_ALL_COURSES, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Course course = new Course();
                    course.name = cursor.getString(cursor.getColumnIndex(KEY_COURSE_NAME));
                    course.students = cursor.getString(cursor.getColumnIndex(KEY_COURSE_STUDENT_REGISTERED));
                    courses.add(course);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("DBFETCHERROR", "Error while trying to get teams from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return courses;
    }

    public Course getCourse(String name) {
        Course course = new Course();
        String GET_COURSE =
                String.format("SELECT * FROM %s WHERE %s='" + name + "'",
                        TABLE_COURSE,KEY_COURSE_NAME);

        Cursor cursor = database.rawQuery(GET_COURSE, null);
        try {
            if (cursor.moveToFirst()) {
                course.name = cursor.getString(cursor.getColumnIndex(KEY_COURSE_NAME));
                course.students = cursor.getString(cursor.getColumnIndex(KEY_COURSE_STUDENT_REGISTERED));
            }
        } catch (Exception e) {
            Log.d("DBFETCHERROR", "Error while trying to get team from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return course;
    }
    public boolean registerStudent(Student student) {
        database.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_STUDENT_ROLL, student.roll);
            values.put(KEY_STUDENT_NAME, student.name);
            values.put(KEY_STUDENT_PASSWORD, student.password);

            database.insertOrThrow(TABLE_STUDENT, null, values);
            database.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("DBINSERTERROR", "Error while trying to add student to database");
            return false;
        } finally {
            database.endTransaction();
        }
        return true;
    }
    public Student loginStudent(String roll, String password) {
        Student student = new Student();
        String GET_STUDENT =
                String.format("SELECT * FROM %s WHERE (%s=%s AND %s=%s)",
                        TABLE_STUDENT,KEY_STUDENT_ROLL,roll,KEY_STUDENT_PASSWORD,password);
        Log.d("SQLCHECK",GET_STUDENT);

        String[] columns ={KEY_STUDENT_NAME,KEY_STUDENT_ROLL};

        Cursor cursor = database.query(TABLE_STUDENT,columns , String.format("%s=? and %s=?",KEY_STUDENT_ROLL,KEY_STUDENT_PASSWORD), new String[] { roll,password }, null, null, null);

        try {
            if (cursor.moveToFirst()) {
                student.roll = cursor.getString(cursor.getColumnIndex(KEY_STUDENT_ROLL));
                student.name = cursor.getString(cursor.getColumnIndex(KEY_STUDENT_NAME));
            }
        } catch (Exception e) {
            Log.d("DBFETCHERROR", "Error while trying to get student from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return student;
    }
    public Student getStudent(String roll) {
        Student student = new Student();
        String GET_STUDENT =
                String.format("SELECT * FROM %s WHERE %s=%s",
                        TABLE_STUDENT,KEY_STUDENT_ROLL,roll);

        Cursor cursor = database.rawQuery(GET_STUDENT, null);
        try {
            if (cursor.moveToFirst()) {
                student.roll = cursor.getString(cursor.getColumnIndex(KEY_STUDENT_ROLL));
                student.name = cursor.getString(cursor.getColumnIndex(KEY_STUDENT_NAME));
            }
        } catch (Exception e) {
            Log.d("DBFETCHERROR", "Error while trying to get student from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return student;
    }
    public boolean updateCourse(Course course) {
        database.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_COURSE_NAME, course.name);
            values.put(KEY_COURSE_STUDENT_REGISTERED,course.students);

            database.update(TABLE_COURSE, values,String.format("%s = ?",KEY_COURSE_NAME), new String[]{String.valueOf(course.name)});
            database.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("DBINSERTERROR", "Error while trying to update course to database");
            return false;
        } finally {
            database.endTransaction();
        }
        return true;
    }
    public boolean updateStudent(Student student) {
        database.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_STUDENT_NAME, student.name);
            values.put(KEY_STUDENT_PASSWORD,student.password);
            values.put(KEY_STUDENT_ROLL,student.roll);

            database.update(TABLE_STUDENT, values,String.format("%s = ?",KEY_STUDENT_ROLL), new String[]{String.valueOf(student.roll)});
            database.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("DBINSERTERROR", "Error while trying to update student to database");
            return false;
        } finally {
            database.endTransaction();
        }
        return true;
    }
}