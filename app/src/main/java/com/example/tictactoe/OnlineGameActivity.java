package com.example.tictactoe;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OnlineGameActivity extends AppCompatActivity {
    TextView tvPlayer1,tvPlayer2;
    String playerSession="";
    String requestType="";
    String otherPlayer="";
    String userName="";
    String loginUID="";
    String myGameSign="X";
    int gameState=0;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference myRef=database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_game);
    }

    public void GameBoardClick(View view) {
    }
}