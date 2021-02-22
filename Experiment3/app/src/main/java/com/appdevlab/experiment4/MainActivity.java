package com.appdevlab.experiment4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView dice1, dice2;
    TextView score1, score2;
    Button player1, player2;
    int winner=-1;
    int chance = -1;

    TextView chance_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dice1 = (ImageView) findViewById(R.id.dice1);
        dice2 = (ImageView) findViewById(R.id.dice2);

        score1 = (TextView) findViewById(R.id.score1);
        score2 = (TextView) findViewById(R.id.score2);

        player1 = (Button) findViewById(R.id.player1);
        player2 = (Button) findViewById(R.id.player2);

        chance_text = (TextView) findViewById(R.id.chance);

        Random rand = new Random();
        chance = rand.nextInt(2) + 1;

        if(chance==1) {
            chance_text.setText("Player 1 to play");
        }
        else {
            chance_text.setText("Player 2 to play");
        }

        player1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(winner==-1) {
                    if (chance == 1) {
                        Random rand = new Random();
                        int number = rand.nextInt(6) + 1;

                        int score = Integer.valueOf(score1.getText().toString()) + number;
                        score1.setText(String.valueOf(score));
                        if (score >= 25) {
                            winner = 1;
                            chance_text.setText("Player "+ String.valueOf(winner) +  " won the game!!!");
                            Toast.makeText(getApplicationContext(), "Player" + String.valueOf(winner) + " won", Toast.LENGTH_SHORT).show();
                        }

                        int res = R.drawable.d1;
                        switch (number) {
                            case 1:
                                res = R.drawable.d1;
                                break;
                            case 2:
                                res = R.drawable.d2;
                                break;
                            case 3:
                                res = R.drawable.d3;
                                break;
                            case 4:
                                res = R.drawable.d4;
                                break;
                            case 5:
                                res = R.drawable.d5;
                                break;
                            case 6:
                                res = R.drawable.d6;
                                break;
                        }
                        dice1.setImageResource(res);
                        chance=2;
                        if(winner==-1)
                            chance_text.setText("Player 2 to play");
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Oops.. Player 2 has to play now", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    chance_text.setText("Player "+ String.valueOf(winner) +  " won the game!!!");
                    Toast.makeText(getApplicationContext(), "Player" + String.valueOf(winner) + " won", Toast.LENGTH_SHORT).show();
                }
            }
        });

        player2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(winner==-1) {
                    if(chance==2) {
                        Random rand = new Random();
                        int number = rand.nextInt(6) + 1;

                        int score = Integer.valueOf(score2.getText().toString()) + number;
                        score2.setText(String.valueOf(score));

                        int res = R.drawable.d1;
                        switch (number) {
                            case 1:
                                res = R.drawable.d1;
                                break;
                            case 2:
                                res = R.drawable.d2;
                                break;
                            case 3:
                                res = R.drawable.d3;
                                break;
                            case 4:
                                res = R.drawable.d4;
                                break;
                            case 5:
                                res = R.drawable.d5;
                                break;
                            case 6:
                                res = R.drawable.d6;
                                break;
                        }
                        dice2.setImageResource(res);

                        if (score >= 25) {
                            winner = 2;
                            chance_text.setText("Player "+ String.valueOf(winner) +  " won the game!!!");
                            Toast.makeText(getApplicationContext(), "Player " + String.valueOf(winner) + " won the game!!!", Toast.LENGTH_LONG).show();
                        }
                        chance=1;
                        if(winner==-1)
                        chance_text.setText("Player 1 to play");
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Oops.. Player 1 has to play now", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    chance_text.setText("Player "+ String.valueOf(winner) +  " won the game!!!");
                    Toast.makeText(getApplicationContext(), "Player " + String.valueOf(winner) + " won the game!!!", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
