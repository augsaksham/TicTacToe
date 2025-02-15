package com.example.tictactoe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GlobalRegisterActivity extends AppCompatActivity {
    //    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    EditText etEmail;
    EditText etPassword;
    EditText etUsername;
    ProgressBar progressbar;
    String userName,tmp1,tmp2,tmp3;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_register);
        progressbar=findViewById(R.id.progressbar2);
        progressbar.setVisibility(View.INVISIBLE);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user!=null){
                    Log.d("Auth","onAuthStateChanged:signed_in:"+user.getUid());
                    Intent i=new Intent(getApplicationContext(),MenuActivity.class);
                    startActivity(i);
                }
                else{
                    Log.d("Auth Failed","onAuthStateChanged:signed_out or login");

                }
            }
        };
    }
    @Override
    public void onStop(){
        super.onStop();
        if(mAuthListener!=null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    public void RegisterUser(String email,String Password){
        mAuth.createUserWithEmailAndPassword(email,Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Auth","createUserWithEmail:onComplete:"+task);
                        if(task.isSuccessful()){
                            progressbar.setVisibility(View.INVISIBLE);
                            Intent i=new Intent(getApplicationContext(),MenuActivity.class);
                            startActivity(i);
                        }
                        if(!task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Auth failed "+task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    void RegisterNewUser(){
        progressbar.setVisibility(View.VISIBLE);
        etEmail=(EditText) findViewById(R.id.editTextEmail);
        etPassword=(EditText) findViewById(R.id.editTextPassword);
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
        userName=convertEmailString(etEmail.getText().toString());
        RegisterUser(etEmail.getText().toString(),etPassword.getText().toString());
        SharedPreferences sp = getSharedPreferences("FILE_NAME", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("key",userName);
        edit.apply();
        progressbar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    public void Register(View view) {
        RegisterNewUser();
    }

    public void Login(View view) {
        Intent i=new Intent(getApplicationContext(),GlobalLoginActivity.class);
        startActivity(i);
    }
    private String convertEmailString(String Email){
        String value = Email.substring(0, Email.indexOf('@'));
        value = value.replace(".", "");
        return value;
    }
}