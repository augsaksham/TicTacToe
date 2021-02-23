package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class OnlineLoginActivity extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    EditText etEmail;
    EditText etPassword;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_login);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user!=null){
                    Log.d("Auth","onAuthStateChanged:signed_in:"+user.getUid());
                }
                else{
                    Register();
                    Log.d("Auth Failed","onAuthStateChanged:signed_out or login");

                }
            }
        };
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
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
                        if(!task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Auth failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    void Register(){
        AlertDialog.Builder b=new AlertDialog.Builder(this);
        LayoutInflater inflater=this.getLayoutInflater();
        final View diaglogView=inflater.inflate(R.layout.login_layout,null);
        b.setView(diaglogView);
        final EditText etEmail=(EditText) diaglogView.findViewById(R.id.etEmail);
        final EditText etPassword=(EditText) diaglogView.findViewById(R.id.etPassword);
        b.setTitle("Please Register");
        b.setMessage("Enter Your Email And Password For Registration");
        b.setPositiveButton("Register", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RegisterUser(etEmail.getText().toString(),etPassword.getText().toString());
            }
        });
        b.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i=new Intent(getApplicationContext(),MenuActivity.class);
                startActivity(i);
                finish();
            }
        });
      b.show();
    }

}