package com.example.dataeye;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "students_db";

    private static final String TABLE_STUDENTS = "students";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_AGE = "age";
    private static final String KEY_GRADE = "grade";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STUDENTS_TABLE = "CREATE TABLE " + TABLE_STUDENTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_AGE + " INTEGER,"
                + KEY_GRADE + " TEXT"
                + ")";
        db.execSQL(CREATE_STUDENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if it exists and create a new one
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(db);
    }

    // Add a new student to the database
    public long addStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, student.getName());
        values.put(KEY_AGE, student.getAge());
        values.put(KEY_GRADE, student.getGrade());

        // Inserting Row
        long id = db.insert(TABLE_STUDENTS, null, values);
        db.close(); // Closing database connection
        return id;
    }

    // Get a single student by ID
    public Student getStudentDetails(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_STUDENTS, new String[]{KEY_ID, KEY_NAME, KEY_AGE, KEY_GRADE},
                KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(KEY_ID);
            int nameIndex = cursor.getColumnIndex(KEY_NAME);
            int ageIndex = cursor.getColumnIndex(KEY_AGE);
            int gradeIndex = cursor.getColumnIndex(KEY_GRADE);

            // Ensure the columns exist in the cursor
            if (idIndex >= 0 && nameIndex >= 0 && ageIndex >= 0 && gradeIndex >= 0) {
                Student student = new Student(
                        cursor.getInt(idIndex),
                        cursor.getString(nameIndex),
                        cursor.getInt(ageIndex),
                        cursor.getString(gradeIndex)
                );
                cursor.close();
                return student;
            }
        }

        if (cursor != null) {
            cursor.close();
        }
        return null;
    }

    // Get all students in the database
    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_STUDENTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(KEY_ID);
                int nameIndex = cursor.getColumnIndex(KEY_NAME);
                int ageIndex = cursor.getColumnIndex(KEY_AGE);
                int gradeIndex = cursor.getColumnIndex(KEY_GRADE);

                // Ensure the columns exist in the cursor
                if (idIndex >= 0 && nameIndex >= 0 && ageIndex >= 0 && gradeIndex >= 0) {
                    Student student = new Student(
                            cursor.getInt(idIndex),
                            cursor.getString(nameIndex),
                            cursor.getInt(ageIndex),
                            cursor.getString(gradeIndex)
                    );
                    studentList.add(student);
                }
            } while (cursor.moveToNext());
        }


        if (cursor != null) {
            cursor.close();
        }
        return studentList;
    }

    // Update a student's details in the database
    public int updateStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, student.getName());
        values.put(KEY_AGE, student.getAge());
        values.put(KEY_GRADE, student.getGrade());

        // Updating row
        return db.update(TABLE_STUDENTS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(student.getId())});
    }

    // Delete a student from the database
    public int deleteStudent(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Deleting row
        return db.delete(TABLE_STUDENTS, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }
}
