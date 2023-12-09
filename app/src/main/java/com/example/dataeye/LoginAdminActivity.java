package com.example.dataeye;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginAdminActivity extends AppCompatActivity {
    private EditText usernameEditText, passwordEditText;
    private Button loginButtonAdmin;

    private Button signupButton;
    private DatabaseHelper databaseHelper;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_admin);
        mAuth = FirebaseAuth.getInstance();
        usernameEditText = findViewById(R.id.editTextUsernametwo);
        passwordEditText = findViewById(R.id.editTextPasswordtwo);
        loginButtonAdmin = findViewById(R.id.buttonLoginAdmintwo);
        signupButton = findViewById(R.id.buttonSignuptwo);
        progressBar = findViewById(R.id.progressBar);

        databaseHelper = new DatabaseHelper(this);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start StudentActivity when the student button is clicked
                Intent intent = new Intent(LoginAdminActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        loginButtonAdmin.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    progressBar.setVisibility(View.VISIBLE);
                                                    // Check login credentials and redirect to the appropriate role
                                                    String email = usernameEditText.getText().toString().trim();
                                                    String password = passwordEditText.getText().toString().trim();


                                                    if (TextUtils.isEmpty(email)) {
                                                        Toast.makeText(LoginAdminActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                                                        return;
                                                    }
                                                    if (TextUtils.isEmpty(password)) {
                                                        Toast.makeText(LoginAdminActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                                                        return;
                                                    }
                                                    mAuth.signInWithEmailAndPassword(email, password)
                                                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                                    progressBar.setVisibility(View.VISIBLE);
                                                                    if (task.isSuccessful()) {
                                                                        Toast.makeText(LoginAdminActivity.this, "Successful Login.",
                                                                                Toast.LENGTH_SHORT).show();
                                                                        startActivity(new Intent(LoginAdminActivity.this, AdminActivity.class));
                                                                        //finish();


                                                                    } else {
                                                                        // If sign in fails, display a message to the user.

                                                                        Toast.makeText(LoginAdminActivity.this, "Authentication failed.",
                                                                                Toast.LENGTH_SHORT).show();

                                                                    }
                                                                }
                                                            });

                                                }

                                            }
        );}
}
