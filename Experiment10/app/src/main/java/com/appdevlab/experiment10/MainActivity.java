package com.appdevlab.experiment10;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.text.Html;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    public static final String SHARED_PREF = "com.appdevlab.experiment10";
    int playerScore = 0;
    TextView name;
    TextView flight,gas,phone,battery,camera,android;
    ImageView flightImage,gasImage,batteryImage,phoneImage,cameraImage,androidImage;

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);

        flight = findViewById(R.id.flight);
        gas = findViewById(R.id.gas);
        phone = findViewById(R.id.phone);
        battery = findViewById(R.id.battery);
        camera = findViewById(R.id.camera);
        android = findViewById(R.id.android);

        flightImage = findViewById(R.id.iv_flight);
        gasImage = findViewById(R.id.iv_gas);
        phoneImage = findViewById(R.id.iv_phone);
        batteryImage = findViewById(R.id.iv_battery);
        cameraImage = findViewById(R.id.iv_camera);
        androidImage = findViewById(R.id.iv_android);

        flight.setOnTouchListener(new TouchListener());
        gas.setOnTouchListener(new TouchListener());
        phone.setOnTouchListener(new TouchListener());
        battery.setOnTouchListener(new TouchListener());
        camera.setOnTouchListener(new TouchListener());
        android.setOnTouchListener(new TouchListener());

        flightImage.setOnDragListener(new DragListener());
        gasImage.setOnDragListener(new DragListener());
        phoneImage.setOnDragListener(new DragListener());
        batteryImage.setOnDragListener(new DragListener());
        cameraImage.setOnDragListener(new DragListener());
        androidImage.setOnDragListener(new DragListener());

        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(),"Please enter player name",Toast.LENGTH_SHORT).show();
                else {

                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    String scores = sharedPreferences.getString("scores","");
                    String players = sharedPreferences.getString("players","");

                    String[] score = scores.split(":");
                    String[] player = players.split(":");

                    if(score.length>2) {
                        scores = players = "";
                        for(int i=score.length-2;i<score.length;i++) {
                            scores = scores + score[i] + ":";
                            players = players + player[i] + ":";
                        }
                        scores = scores + playerScore;
                        players = players + name.getText().toString();
                    }
                    else {
                        scores = scores + ":" + playerScore;
                        players = players + ":" + name.getText().toString();
                    }

                    editor.putString("scores",scores);
                    editor.putString("players",players);
                    editor.apply();

                    String notificationBody;

                    if(playerScore==6)
                        notificationBody = "Congratulations " + name.getText().toString().toUpperCase() + "!! You have won the game";
                    else
                        notificationBody = "You have lost the game " + name.getText().toString().toUpperCase() + " :( ";


                    NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this,"Experiment 10")
                            .setSmallIcon(R.drawable.ic_account_circle_black_24dp)
                            .setContentTitle("You have scored " + playerScore + " out of 6")
                            .setContentText(Html.fromHtml(notificationBody))
                            .setAutoCancel(true);


                    Intent intent = new Intent(MainActivity.this, ScoreActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


                    PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(pendingIntent);

                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(MainActivity.this);
                    notificationManagerCompat.notify(1,builder.build());

                    Toast.makeText(getApplicationContext(),"Restarting game",Toast.LENGTH_SHORT).show();
                    refreshGame();
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.refresh:
                Toast.makeText(getApplicationContext(),"Refreshing game",Toast.LENGTH_SHORT).show();
                refreshGame();
                break;
            case R.id.exit:
                Toast.makeText(getApplicationContext(),"Exiting App",Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }


    private static final class TouchListener implements OnTouchListener {
        @SuppressLint("NewApi")
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                view.setVisibility(View.INVISIBLE);
                ClipData data = ClipData.newPlainText("", "");
                DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);

                return true;
            } else {
                return false;
            }
        }
    }

    @SuppressLint("NewApi")
    private class DragListener implements OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DROP:
                    View view = (View) event.getLocalState();
                    TextView item = (TextView) view;
                    ImageView target =(ImageView) v;

                    view.setVisibility(View.INVISIBLE);

                    String targetName = String.valueOf(target.getTag());
                    String textName = item.getText().toString();

                    Log.d("MY_LOG_TAG", targetName + "  " + textName);
                    if(targetName.equals(textName)){
                        playerScore++;
                    }

                    target.setVisibility(View.INVISIBLE);
                    target.setOnDragListener(null);

                    break;
                default:
                    break;
            }
            return true;
        }
    }


    private void refreshGame() {

        playerScore = 0;
        name.setText("");

        flight.setVisibility(TextView.VISIBLE);
        gas.setVisibility(TextView.VISIBLE);
        phone.setVisibility(TextView.VISIBLE);
        camera.setVisibility(TextView.VISIBLE);
        battery.setVisibility(TextView.VISIBLE);
        android.setVisibility(TextView.VISIBLE);

        flightImage.setVisibility(TextView.VISIBLE);
        gasImage.setVisibility(TextView.VISIBLE);
        phoneImage.setVisibility(TextView.VISIBLE);
        batteryImage.setVisibility(TextView.VISIBLE);
        cameraImage.setVisibility(TextView.VISIBLE);
        androidImage.setVisibility(TextView.VISIBLE);

        flightImage.setOnDragListener(new DragListener());
        gasImage.setOnDragListener(new DragListener());
        phoneImage.setOnDragListener(new DragListener());
        batteryImage.setOnDragListener(new DragListener());
        cameraImage.setOnDragListener(new DragListener());
        androidImage.setOnDragListener(new DragListener());
    }
}