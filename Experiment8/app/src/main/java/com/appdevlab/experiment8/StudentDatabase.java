package com.appdevlab.experiment8;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class StudentDatabase extends SQLiteOpenHelper {
    // Database Info
    public static final String DATABASE_NAME = "courseRegistration";
    public static final int DATABASE_VERSION = 1;

    // Table Names
    public static final String TABLE_STUDENT = "student";
    public static final String TABLE_COURSE = "course";


    // Course Table Columns
    public static final String KEY_COURSE_NAME = "name";
    public static final String KEY_COURSE_STUDENT_REGISTERED = "students";

    // Student Table Columns
    public static final String KEY_STUDENT_ROLL = "roll";
    public static final String KEY_STUDENT_NAME = "name";
    public static final String KEY_STUDENT_PASSWORD = "password";
    public static final String KEY_STUDENT_COURSES = "courses";


    public StudentDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_COURSE_TABLE = "CREATE TABLE " + TABLE_COURSE +
                "(" +
                KEY_COURSE_NAME + " TEXT PRIMARY KEY," +
                KEY_COURSE_STUDENT_REGISTERED + " TEXT " +
                ")";

        String CREATE_STUDENT_TABLE = "CREATE TABLE " + TABLE_STUDENT +
                "(" +
                KEY_STUDENT_ROLL + " INTEGER PRIMARY KEY," + // Define a primary key
                KEY_STUDENT_PASSWORD + " TEXT, " +
                KEY_STUDENT_COURSES + " TEXT," +
                KEY_STUDENT_NAME + " TEXT" +
                ")";

        db.execSQL(CREATE_COURSE_TABLE);
        db.execSQL(CREATE_STUDENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE);
            onCreate(db);
        }
    }
}
