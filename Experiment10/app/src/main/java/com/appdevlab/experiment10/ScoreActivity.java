package com.appdevlab.experiment10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.appdevlab.experiment10.MainActivity.SHARED_PREF;

public class ScoreActivity extends AppCompatActivity {
    private TextView leaderboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        setTitle("Leaderboard");
        leaderboard = findViewById(R.id.leaderboard);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);

        String scores = sharedPreferences.getString("scores","");
        ArrayList<String> score = new ArrayList<String>(Arrays.asList(scores.split(":")));

        String players = sharedPreferences.getString("players","");
        ArrayList<String> player = new ArrayList<>(Arrays.asList(players.split(":")));


        String text = "";

        for(int i=score.size()-1;i>=0;i--) {
            text = text + "<b>" + player.get(i) + "</b> has scored <i>" + score.get(i) + "/6 </i> <br><br>";
        }
        leaderboard.setText(Html.fromHtml(text));
    }
}