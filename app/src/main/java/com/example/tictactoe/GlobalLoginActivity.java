package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class GlobalLoginActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText etEmail;
    EditText etPassword;
    EditText etUsername;
//    ProgressBar progressbar;
    String user,pass,name;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_login);
//        progressbar=(ProgressBar) findViewById(R.id.progressbar1);
//        progressbar.setVisibility(View.INVISIBLE);
        mAuth = FirebaseAuth.getInstance();
        etEmail=(EditText) findViewById(R.id.etEmail1);
        etPassword=(EditText) findViewById(R.id.etPassword1);
        etUsername=(EditText) findViewById(R.id.etUsername1);
        user=etEmail.getText().toString().trim();
        pass=etPassword.getText().toString().trim();
        name=etUsername.getText().toString();
        login=(Button)findViewById(R.id.login_btn1);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                progressbar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(user,pass).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
//                            progressbar.setVisibility(View.INVISIBLE);
                            Intent i = new Intent(getApplicationContext(), MenuActivity.class);
                            startActivity(i);
                        }else{
//                            progressbar.setVisibility(View.INVISIBLE);
                            Toast.makeText(GlobalLoginActivity.this, "Login Unsucessful", Toast.LENGTH_SHORT).show();
                        }
                        }
                });

            }
        });
    }

    public void Register2(View view) {
        Intent i=new Intent(getApplicationContext(),GlobalRegisterActivity.class);
        startActivity(i);
    }
}