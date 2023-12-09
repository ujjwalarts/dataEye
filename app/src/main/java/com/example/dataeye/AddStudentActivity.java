package com.example.dataeye;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddStudentActivity extends AppCompatActivity {

    private EditText editTextName, editTextAge, editTextGrade;
    private Button buttonAddStudent;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextGrade = findViewById(R.id.editTextGrade);
        buttonAddStudent = findViewById(R.id.buttonAddStudent);

        databaseHelper = new DatabaseHelper(this);

        buttonAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the add student action
                String name = editTextName.getText().toString().trim();
                int age = Integer.parseInt(editTextAge.getText().toString().trim());
                String grade = editTextGrade.getText().toString().trim();

                // Create a new student object
                Student student = new Student(0, name, age, grade);

                // Add the student to the database
                long id = databaseHelper.addStudent(student);

                if (id != -1) {
                    Toast.makeText(AddStudentActivity.this, "Student added successfully.",
                            Toast.LENGTH_SHORT).show();

                    finish(); // Close the activity
                } else {
                    Toast.makeText(AddStudentActivity.this, "Failed.",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
