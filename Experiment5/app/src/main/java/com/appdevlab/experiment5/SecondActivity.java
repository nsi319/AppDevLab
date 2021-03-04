package com.appdevlab.experiment5;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class SecondActivity extends AppCompatActivity {
    RadioGroup radio;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        Toast.makeText(getApplicationContext(),"Welcome " + name + " !!!!", Toast.LENGTH_SHORT).show();

        ((TextView)findViewById(R.id.title)).setText("Welcome " + name);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Back from Activity 2", Toast.LENGTH_SHORT).show();
                finish();  // finish the activity
            }
        });
        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio = findViewById(R.id.radio);
                switch (radio.getCheckedRadioButtonId()) {
                    case R.id.call: {
                        ActivityCompat.requestPermissions(SecondActivity.this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                1);
                    }
                    break;
                    case R.id.youtube: {
                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://www.youtube.com/channel/UCEPOEe5azp3FbUjvMwttPqw"));
                        startActivity(intent);
                    }
                    break;
                    case R.id.website: {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.nitt.edu"));
                        startActivity(intent);
                    }
                }
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "04312503000"));
                    startActivity(intent);
                } else {
                    Toast.makeText(SecondActivity.this, "Permission denied to make phone call", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}
