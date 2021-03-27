package com.appdevlab.experiment7;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class ControlActivity extends AppCompatActivity {
    EditText bright;
    Switch dark, status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        bright = (EditText) findViewById(R.id.bright);
        dark = (Switch) findViewById(R.id.dark);
        status = (Switch) findViewById(R.id.status);
        dark.setChecked(true);
        status.setChecked(true);

        (findViewById(R.id.save_bright)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bright.getText().toString().isEmpty()) {
                    checkSystemWritePermission();
                    int value = Integer.parseInt(bright.getText().toString());
                    if (value >= 0 && value <= 100) {
                        Toast.makeText(getApplicationContext(), "Setting brightness to " + String.valueOf(value) + "%", Toast.LENGTH_SHORT).show();
                        Settings.System.putInt(getApplicationContext().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, value);
                    } else {
                        bright.setError("Enter value between 0 and 100");
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Enter brightness value", Toast.LENGTH_SHORT).show();
                }
            }
        });
        status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                } else {
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                }
            }
        });
        dark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        AppCompatDelegate
                                .setDefaultNightMode(
                                        AppCompatDelegate.MODE_NIGHT_YES);
                    } else {
                        AppCompatDelegate
                                .setDefaultNightMode(
                                        AppCompatDelegate.MODE_NIGHT_NO);
                    }
                }
        });

        (findViewById(R.id.set_ringtone)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Uri currentTone =
                        RingtoneManager.getActualDefaultRingtoneUri(ControlActivity.this, RingtoneManager.TYPE_RINGTONE);
                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_RINGTONE);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Ringtone");
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, currentTone);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
                startActivityForResult(intent, 100);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            Toast.makeText(getApplicationContext(), "Selected Ringtone: " + uri.getPath(), Toast.LENGTH_SHORT).show();
        }
    }
    private void openAndroidPermissionsMenu() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
        intent.setData(Uri.parse("package:" + this.getPackageName()));
        startActivity(intent);
    }
    private boolean checkSystemWritePermission() {
        boolean value = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            value = Settings.System.canWrite(this);
            if(value){
                return true;
            }else{
                openAndroidPermissionsMenu();
            }
        }
        return value;
    }

}