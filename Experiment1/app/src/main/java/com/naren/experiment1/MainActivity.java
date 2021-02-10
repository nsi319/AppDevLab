package com.naren.experiment1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText name;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText)findViewById(R.id.name);
        submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setError(null);
                String value = name.getText().toString();
                if(value.trim().equals("")) {
                    name.setError("Please enter a valid name");
                }
                else {
                    Log.d("name", value);
                    Toast.makeText(getApplicationContext(), "Your name is: " + value, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
