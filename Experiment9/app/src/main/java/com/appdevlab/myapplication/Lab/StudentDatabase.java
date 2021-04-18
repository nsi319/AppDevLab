package com.appdevlab.myapplication.Lab;

import android.content.Context;
import android.database.sqlite.*;


public class StudentDatabase extends SQLiteOpenHelper {
    // Database Info
    public static final String DATABASE_NAME = "studentDB";
    public static final int DATABASE_VERSION = 1;

    // Table Names
    public static final String TABLE_STUDENT = "student";

    // Student Table Columns
    public static final String KEY_STUDENT_CGPA= "cgpa";
    public static final String KEY_STUDENT_ROLL = "roll";
    public static final String KEY_STUDENT_NAME = "name";

    public StudentDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {


        String CREATE_STUDENT_TABLE = "CREATE TABLE " + TABLE_STUDENT +
                "(" +
                KEY_STUDENT_ROLL + " TEXT PRIMARY KEY," + // Define a primary key
                KEY_STUDENT_CGPA + " TEXT," +
                KEY_STUDENT_NAME + " TEXT" +
                ")";

        db.execSQL(CREATE_STUDENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);

            onCreate(db);
        }
    }

}