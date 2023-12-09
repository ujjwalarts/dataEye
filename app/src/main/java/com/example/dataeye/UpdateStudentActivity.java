package com.example.dataeye;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateStudentActivity extends AppCompatActivity {

    private EditText editTextName, editTextAge, editTextGrade;
    private Button buttonUpdateStudent;
    private DatabaseHelper databaseHelper;
    private int studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);

        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextGrade = findViewById(R.id.editTextGrade);
        buttonUpdateStudent = findViewById(R.id.buttonUpdateStudent);

        databaseHelper = new DatabaseHelper(this);

        // Get the student ID passed from the previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            studentId = extras.getInt("studentId");
        }

        // Fetch and display the student details
        Student studentToUpdate = databaseHelper.getStudentDetails(studentId);
        displayStudentDetails(studentToUpdate);

        buttonUpdateStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // update student action
                String name = editTextName.getText().toString().trim();
                int age = Integer.parseInt(editTextAge.getText().toString().trim());
                String grade = editTextGrade.getText().toString().trim();

                // Create a new student object with updated details
                Student updatedStudent = new Student(studentId, name, age, grade);

                // Update the student in the database
                int rowsAffected = databaseHelper.updateStudent(updatedStudent);

                if (rowsAffected > 0) {

                    finish(); // Close the activity

                } else {
                    // Failed to update student

                }
            }
        });
    }

    private void displayStudentDetails(Student student) {
        editTextName.setText(student.getName());
        editTextAge.setText(String.valueOf(student.getAge()));
        editTextGrade.setText(student.getGrade());
    }
}
