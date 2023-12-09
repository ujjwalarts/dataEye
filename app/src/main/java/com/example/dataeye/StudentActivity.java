package com.example.dataeye;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StudentActivity extends AppCompatActivity {

    private EditText editTextStudentName, editTextStudentAge, editTextStudentGrade;
    private Button buttonEditDetails;
    private DatabaseHelper databaseHelper;
    private int studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        editTextStudentName = findViewById(R.id.editTextStudentName);
        editTextStudentAge = findViewById(R.id.editTextStudentAge);
        editTextStudentGrade = findViewById(R.id.editTextStudentGrade);
        buttonEditDetails = findViewById(R.id.buttonEditDetails);

        databaseHelper = new DatabaseHelper(this);

        Student student = databaseHelper.getStudentDetails(studentId);
        if (student != null) {
            // Display student details if the student object is not null
            displayStudentDetails(student);
        } else {

            Toast.makeText(this, "Student details not found", Toast.LENGTH_SHORT).show();
            //finish();
        }

        buttonEditDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentActivity.this, AddStudentActivity.class);

                // Pass the studentId to the next activity
                intent.putExtra("STUDENT_ID", studentId);

                startActivity(intent);
            }
        });
    }

    private void displayStudentDetails(Student student) {
        editTextStudentName.setText(student.getName());
        editTextStudentAge.setText(String.valueOf(student.getAge()));
        editTextStudentGrade.setText(student.getGrade());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseHelper.close();
    }
}
