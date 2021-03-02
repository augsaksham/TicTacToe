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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class OnlineLoginActivity extends AppCompatActivity  {
    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    EditText etEmail;
//    EditText etPassword;
    Button btnLogin;
    ListView lv_loginUsers;
    ArrayList<String> list_loginUsers=new ArrayList<String>();
    ArrayAdapter adpt;
    ListView lv_requestedUsers;
    ArrayList<String> list_requestedUsers=new ArrayList<String>();
    ArrayAdapter reqUserAdpt;
    TextView tvUserID,tvSendRequest,tvAcceptRequest;
    String LoginUserID,UserName,LoginUID;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference myRef=database.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_login);
        tvAcceptRequest=(TextView)findViewById(R.id.tvAcceptRequest);
        tvSendRequest=(TextView)findViewById(R.id.tvSendRequest);
        tvAcceptRequest.setText("Please Wait ....");
        tvSendRequest.setText("Please Wait ....");
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mAuth = FirebaseAuth.getInstance();
        lv_loginUsers=(ListView) findViewById(R.id.lv_loginUsers);
        adpt=new ArrayAdapter(this,android.R.layout.simple_list_item_1,list_loginUsers);
        lv_loginUsers.setAdapter(adpt);
        lv_requestedUsers=(ListView) findViewById(R.id.lv_requestedUsers);
        adpt=new ArrayAdapter(this,android.R.layout.simple_list_item_1,list_requestedUsers);
        lv_loginUsers.setAdapter(reqUserAdpt);
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user!=null){
                    Log.d("Auth","onAuthStateChanged:signed_in:"+user.getUid());
                }
                else{
                    Intent i=new Intent(getApplicationContext(),GlobalLoginActivity.class);
                    startActivity(i);
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
//    public void RegisterUser(String email,String Password){
//        mAuth.createUserWithEmailAndPassword(email,Password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Log.d("Auth","createUserWithEmail:onComplete:"+task);
//                        if(!task.isSuccessful()){
//                            Toast.makeText(getApplicationContext(),"Auth failed",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }
//    void Register(){
//        AlertDialog.Builder b=new AlertDialog.Builder(this);
//        LayoutInflater inflater=this.getLayoutInflater();
//        final View diaglogView=inflater.inflate(R.layout.login_layout,null);
//        b.setView(diaglogView);
//        final EditText etEmail=(EditText) diaglogView.findViewById(R.id.etEmail);
//        final EditText etPassword=(EditText) diaglogView.findViewById(R.id.etPassword);
//        b.setTitle("Please Register");
//        b.setMessage("Enter Your Email And Password For Registration");
//        b.setPositiveButton("Register", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                RegisterUser(etEmail.getText().toString(),etPassword.getText().toString());
//            }
//        });
//        b.setNegativeButton("Back", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Intent i=new Intent(getApplicationContext(),MenuActivity.class);
//                startActivity(i);
//                finish();
//            }
//        });
//      b.show();
//    }

}