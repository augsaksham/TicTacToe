package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MenuActivity extends AppCompatActivity {


    private View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void startGame_SinglePlayer(View view) {
        Intent i=new Intent(getApplicationContext(),GameActivity.class);
        startActivity(i);
    }

    public void StartGameOnline(View view) {
        Intent i=new Intent(getApplicationContext(),OnlineLoginActivity.class);
        startActivity(i);

    }

    public void Exit(View view) {
        int pid=android.os.Process.myPid();
        android.os.Process.killProcess(pid);

    }

    public void OpenChat(View view) {
        Intent i=new Intent(getApplicationContext(),ChatMainActivity2.class);
        startActivity(i);
    }

    public void OpenBlog(View view) {
        Intent i=new Intent(getApplicationContext(),ChatMainActivity.class);
        startActivity(i);
    }
}