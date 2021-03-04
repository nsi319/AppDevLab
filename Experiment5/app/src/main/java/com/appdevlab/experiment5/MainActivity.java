package com.appdevlab.experiment5;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText roll,name;
    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        roll = findViewById(R.id.roll);
        name = findViewById(R.id.name);
        next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int k=0;
                if (roll.getText().toString().isEmpty()) {
                    roll.setError("Please enter roll number");
                    k=1;
                }
                if(name.getText().toString().isEmpty()) {
                    name.setError("Please enter name");
                    k=1;
                }
                if(k==0) {
                    Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                    intent.putExtra("roll", roll.getText().toString());
                    intent.putExtra("name",name.getText().toString());
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"Back from Activity 2", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
