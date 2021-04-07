package com.example.tictactoe;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;
public class GameActivity extends AppCompatActivity {
    int gameState;
    int activePlayer;
    int comMove=0;
    int playerMove=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gameState=1;
        activePlayer=1;
    }

    public void GameBoardClick(View view) {
        ImageView selectedImage=(ImageView) view;
        int selectedBlock=0;
        switch (selectedImage.getId()) {
            // row 1
            case R.id.iv_11:
                selectedBlock = 1;
                break;
            case R.id.iv_12:
                selectedBlock = 2;
                break;
            case R.id.iv_13:
                selectedBlock = 3;
                break;
            // row 2
            case R.id.iv_21:
                selectedBlock = 4;
                break;
            case R.id.iv_22:
                selectedBlock = 5;
                break;
            case R.id.iv_23:
                selectedBlock = 6;
                break;
            //row 3
            case R.id.iv_31:
                selectedBlock = 7;
                break;
            case R.id.iv_32:
                selectedBlock = 8;
                break;
            case R.id.iv_33:
                selectedBlock = 9;
                break;
        }
        PlayGame(selectedBlock,selectedImage);
    }
    ArrayList<Integer> player1=new ArrayList<Integer>();
    ArrayList<Integer> player2=new ArrayList<Integer>();
    private void PlayGame(int selectedBlock, ImageView selectedImage) {
        if(gameState==1){
            if(selectedImage==null){
                if(activePlayer==2){
                    Autoplay();
                }
            }
            if(activePlayer==1){
                selectedImage.setImageResource(R.drawable.multi_x);
                player1.add(selectedBlock);
                playerMove++;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        activePlayer=2;
                        Autoplay();
                    }
                }, 50);
            }
            else if(activePlayer==2){
                selectedImage.setImageResource(R.drawable.multi_o);
                player2.add(selectedBlock);
                activePlayer=1;
            }
            selectedImage.setEnabled(false); //single use of a imj
            CheckWinner ();
        }
    }
    private void Autoplay() {
        ArrayList<Integer> emptyBlocks=new ArrayList<Integer>() ;
        for(int i=0;i<=9;i++){
            if(!(player1.contains(i)||player2.contains(i))){
                emptyBlocks.add(i);
            }
        }if(emptyBlocks.size()==0){
            CheckWinner();
//            if(gameState==1){
////                AlertDialog.Builder b=new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
//                showAlert("Draw");
//            }
//            gameState=3; //draw
        }else{
            int selectedBlock=0;
            boolean flg=false;
            while (!flg){
                if(comMove==4){
                    selectedBlock=10;
                    break;
                }
                Random r=new Random();
                int randomIndex=r.nextInt(10-1)+1;
                flg=emptyBlocks.contains(randomIndex);
                if(flg){
                    selectedBlock=randomIndex;
                    comMove++;
                }
            }
            ImageView selectedImage=null;
            switch(selectedBlock){
                case 1:selectedImage=(ImageView) findViewById(R.id.iv_11); break;
                case 2:selectedImage=(ImageView) findViewById(R.id.iv_12); break;
                case 3:selectedImage=(ImageView) findViewById(R.id.iv_13); break;

                case 4:selectedImage=(ImageView) findViewById(R.id.iv_21); break;
                case 5:selectedImage=(ImageView) findViewById(R.id.iv_22); break;
                case 6:selectedImage=(ImageView) findViewById(R.id.iv_23); break;

                case 7:selectedImage=(ImageView) findViewById(R.id.iv_31); break;
                case 8:selectedImage=(ImageView) findViewById(R.id.iv_32); break;
                case 9:selectedImage=(ImageView) findViewById(R.id.iv_33); break;
                case 10:selectedImage=null;break;
            }
            PlayGame(selectedBlock,selectedImage);
        }

    }

    private void showAlert(String Title) {
        AlertDialog.Builder b=new AlertDialog.Builder(this,R.style.TransparentDialog);
        b.setTitle(Title)
                .setMessage("Start a new game?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ResetGame();
                    }
                })
                .setNegativeButton("Menu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i=new Intent(getApplicationContext(),MenuActivity.class);
                        startActivity(i);
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();

    }
    void ResetGame(){
        playerMove=0;
        comMove=0;
        gameState=1;
        activePlayer=1;
        player1.clear();
        player2.clear();
        ImageView iv;
        iv=(ImageView) findViewById(R.id.iv_11); iv.setImageResource(0);iv.setEnabled(true);
        iv=(ImageView) findViewById(R.id.iv_12); iv.setImageResource(0);iv.setEnabled(true);
        iv=(ImageView) findViewById(R.id.iv_13); iv.setImageResource(0);iv.setEnabled(true);

        iv=(ImageView) findViewById(R.id.iv_21); iv.setImageResource(0);iv.setEnabled(true);
        iv=(ImageView) findViewById(R.id.iv_22); iv.setImageResource(0);iv.setEnabled(true);
        iv=(ImageView) findViewById(R.id.iv_23); iv.setImageResource(0);iv.setEnabled(true);

        iv=(ImageView) findViewById(R.id.iv_31); iv.setImageResource(0);iv.setEnabled(true);
        iv=(ImageView) findViewById(R.id.iv_32); iv.setImageResource(0);iv.setEnabled(true);
        iv=(ImageView) findViewById(R.id.iv_33); iv.setImageResource(0);iv.setEnabled(true);
    }
    private void CheckWinner() {
        int winner=0;
        // checking rows
        if(player1.contains(1)&&player1.contains(2)&&player1.contains(3)) winner=1;
        if(player1.contains(4)&&player1.contains(5)&&player1.contains(6)) winner=1;
        if(player1.contains(7)&&player1.contains(8)&&player1.contains(9)) winner=1;
        // checking coloums
        if(player1.contains(1)&&player1.contains(4)&&player1.contains(7)) winner=1;
        if(player1.contains(2)&&player1.contains(5)&&player1.contains(8)) winner=1;
        if(player1.contains(3)&&player1.contains(6)&&player1.contains(9)) winner=1;
        // checking diagnols
        if(player1.contains(1)&&player1.contains(5)&&player1.contains(9)) winner=1;
        if(player1.contains(3)&&player1.contains(5)&&player1.contains(7)) winner=1;
        // checking rows p2
        if(player2.contains(1)&&player2.contains(2)&&player2.contains(3)) winner=2;
        if(player2.contains(4)&&player2.contains(5)&&player2.contains(6)) winner=2;
        if(player2.contains(7)&&player2.contains(8)&&player2.contains(9)) winner=2;
        // checking coloums p2
        if(player2.contains(1)&&player2.contains(4)&&player2.contains(7)) winner=2;
        if(player2.contains(2)&&player2.contains(5)&&player2.contains(8)) winner=2;
        if(player2.contains(3)&&player2.contains(6)&&player2.contains(9)) winner=2;
        // checking diagnols p2
        if(player2.contains(1)&&player2.contains(5)&&player2.contains(9)) winner=2;
        if(player2.contains(3)&&player2.contains(5)&&player2.contains(7)) winner=2;
        if(winner!=0 && gameState==1){
            if(winner==1){
                showAlert("You win the game");
            }else if(winner==2){
                showAlert("Computer wins");
            }
            gameState=2;//symolising game over
        }
        if((comMove==4)&&(playerMove==5)&&(winner!=1)){
            gameState=3;
            showAlert("Draw");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i=new Intent(getApplicationContext(),MenuActivity.class);
                    startActivity(i);
                    finish();
                }
            }, 5000);

        }
    }
}