package com.example.dataeye;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    private ListView listViewStudents;
    private Button buttonAddStudent, buttonUpdateStudent, buttonDeleteStudent;
    private DatabaseHelper databaseHelper;
    private List<Student> studentList;
    private StudentAdapter studentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        listViewStudents = findViewById(R.id.listViewStudents);
        buttonAddStudent = findViewById(R.id.buttonAddStudent);
        buttonUpdateStudent = findViewById(R.id.buttonUpdateStudent);
        buttonDeleteStudent = findViewById(R.id.buttonDeleteStudent);

        databaseHelper = new DatabaseHelper(this);
        studentList = databaseHelper.getAllStudents();
        studentAdapter = new StudentAdapter(studentList, this);
        listViewStudents.setAdapter(studentAdapter);


        listViewStudents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                int studentIdToUpdate = studentList.get(position).getId();
                Intent intent = new Intent(AdminActivity.this, UpdateStudentActivity.class);
                intent.putExtra("studentId", studentIdToUpdate);
                startActivity(intent);
            }
        });

        //deleting student

        listViewStudents.setOnItemLongClickListener((parent, view, position, id) -> {

            int studentIdToDelete = studentList.get(position).getId();

            showDeleteConfirmationDialog(studentIdToDelete);

            return true;
        });

        buttonAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminActivity.this, AddStudentActivity.class);
                startActivity(intent);
            }
        });

        buttonUpdateStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (studentList.isEmpty()) {

                    Toast.makeText(AdminActivity.this, "No students available to update", Toast.LENGTH_SHORT).show();
                } else {

                    int studentIdToUpdate = studentList.get(0).getId();
                    Intent intent = new Intent(AdminActivity.this, UpdateStudentActivity.class);
                    intent.putExtra("studentId", studentIdToUpdate);
                    startActivity(intent);
                }
            }
        });

        buttonDeleteStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (studentList.isEmpty()) {

                    Toast.makeText(AdminActivity.this, "No students available to delete", Toast.LENGTH_SHORT).show();
                } else {

                    int studentIdToDelete = studentList.get(0).getId();


                    showDeleteConfirmationDialog(studentIdToDelete);
                }
            }
        });
    }



    private void showDeleteConfirmationDialog(final int studentIdToDelete) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Deletion");
        builder.setMessage("Are you sure you want to delete this student?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Perform the delete operation
                deleteStudent(studentIdToDelete);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        builder.show();
    }


    private void deleteStudent(int studentIdToDelete) {
        int rowsDeleted = databaseHelper.deleteStudent(studentIdToDelete);

        if (rowsDeleted > 0) {

            studentList = databaseHelper.getAllStudents();
            studentAdapter.notifyDataSetChanged();

            Toast.makeText(AdminActivity.this, "Student deleted successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AdminActivity.this, "Failed to delete student", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseHelper.close();
    }
}
