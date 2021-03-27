package com.appdevlab.experiment7;
import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
public class MainActivity extends AppCompatActivity {
    final String APP =  "com.appdevlab.experiment6";
    EditText name,mail,mobile;
    TextView fetched;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences(APP,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        name = (EditText) findViewById(R.id.name);
        mail = (EditText) findViewById(R.id.mail);
        mobile = (EditText) findViewById(R.id.mobile);
        fetched = (TextView) findViewById(R.id.fetched);

        (findViewById(R.id.save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences(APP,MODE_PRIVATE);
                fetched.setText("");
                if(name.getText().toString().isEmpty() || mail.getText().toString().isEmpty() || mobile.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Enter required fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    editor.putString("name",name.getText().toString());
                    editor.putString("mail",mail.getText().toString());
                    editor.putString("mobile",mobile.getText().toString());
                    editor.commit();
                    Toast.makeText(getApplicationContext(),"Saved details successfully",Toast.LENGTH_SHORT).show();
                }
            }
        });
        (findViewById(R.id.fetch)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences(APP,MODE_PRIVATE);
                fetched.setText("");
                String name = sharedPreferences.getString("name","");
                String mail = sharedPreferences.getString("mail","");
                String mobile = sharedPreferences.getString("mobile","");


                if(name.isEmpty() || mail.isEmpty() || mobile.isEmpty()) {
                    fetched.setText(Html.fromHtml("<b> No details fetched from storage :( </b>"));
                }
                else {
                    String res = "Name: <b>" + name + "</b> <br>Email: <b>" + mail + "</b> <br>Mobile Number: <b>" + mobile + " </b>";
                    fetched.setText("");
                    fetched.setText(Html.fromHtml(res));
                    Toast.makeText(getApplicationContext(),"Fetched details successfully",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app:
                Toast.makeText(this, "Selected Fragment Apps", Toast.LENGTH_SHORT).show();
                Intent appIntent = new Intent(Intent.ACTION_SEND);
                appIntent.setClassName("com.appdevlab.exp4","com.appdevlab.exp4.MainActivity");
                startActivity(appIntent);
                return true;
            case R.id.selfie:
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 100);
                }
                else {
                    Toast.makeText(this, "Selected Selfie Camera", Toast.LENGTH_SHORT).show();
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent.putExtra("android.intent.extra.USE_FRONT_CAMERA", 1);
                    startActivity(cameraIntent);
                }
                return true;
            case R.id.control:
                Toast.makeText(this, "Selected Control Option", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ControlActivity.class);
                startActivity(intent);
                return true;
            case R.id.exit:
                Toast.makeText(this, "Selected Exit App", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu, menu);
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Selected Selfie Camera", Toast.LENGTH_SHORT).show();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra("android.intent.extra.USE_FRONT_CAMERA", 1);
                startActivity(cameraIntent);
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
            }
        }
    }
}