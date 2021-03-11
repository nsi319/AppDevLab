package com.appdevlab.experiment6;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    RadioGroup radio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radio = (RadioGroup) findViewById(R.id.radio);
        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected_id = radio.getCheckedRadioButtonId();
                switch (selected_id) {
                    case R.id.mail : startActivity(new Intent(MainActivity.this, MailActivity.class));
                    break;
                    case R.id.camera: startActivity(new Intent(MainActivity.this, CameraActivity.class));
                    break;
                    case R.id.note : startActivity( new Intent(MainActivity.this, NoteActivity.class));
                    break;
                    default: Toast.makeText(getApplicationContext(),"Choose an option", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
