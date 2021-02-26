package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.Timer;
import java.util.TimerTask;

import kotlinx.coroutines.Delay;

public class GlobalLoginActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText etEmail;
    EditText etPassword;
    EditText etUsername;
    ProgressBar progressbar;
    String user,pass,name,tmp1,tmp2,tmp3,tmp4;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_login);
        progressbar=(ProgressBar) findViewById(R.id.progressbar1);
        progressbar.setVisibility(View.INVISIBLE);
        mAuth = FirebaseAuth.getInstance();
    }

    public void Register2(View view) {
        Intent i=new Intent(getApplicationContext(),GlobalRegisterActivity.class);
        startActivity(i);
    }

    public void Login(View view) {
        progressbar.setVisibility(View.VISIBLE);
        etEmail=(EditText) findViewById(R.id.etEmail1);
        etPassword=(EditText) findViewById(R.id.etPassword1);
        etUsername=(EditText) findViewById(R.id.etUsername1);
        user=etEmail.getText().toString().trim();
        pass=etPassword.getText().toString().trim();
        name=etUsername.getText().toString();
        tmp1=etEmail.getText().toString().trim();
        tmp1.trim();
        if(tmp1.compareToIgnoreCase("")==0){
            Toast.makeText(this, "Email can't be empty", Toast.LENGTH_SHORT).show();
            progressbar.setVisibility(View.INVISIBLE);
            return;
        }
        tmp2=etPassword.getText().toString().trim();;
        tmp2.trim();
        if(tmp2.compareToIgnoreCase("")==0){
            Toast.makeText(this, "Password can't be empty", Toast.LENGTH_SHORT).show();
            progressbar.setVisibility(View.INVISIBLE);
            return;
        }
        tmp3=etUsername.getText().toString().trim();;
        tmp3.trim();
        if(tmp3.compareToIgnoreCase("")==0){
            Toast.makeText(this, "Username can't be empty", Toast.LENGTH_SHORT).show();
            progressbar.setVisibility(View.INVISIBLE);
            return;
        }
        SharedPreferences sp = getSharedPreferences("FILE_NAME", MODE_PRIVATE);
        tmp4=sp.getString("key","Default user");
        if(tmp4.equalsIgnoreCase("Default user")){
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("key",name);
            edit.apply();
        }

        mAuth.signInWithEmailAndPassword(user,pass).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                            progressbar.setVisibility(View.INVISIBLE);
                    Intent i = new Intent(getApplicationContext(), MenuActivity.class);
                    startActivity(i);
                }else{
                    progressbar.setVisibility(View.INVISIBLE);
                    Toast.makeText(GlobalLoginActivity.this, "Failed "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}