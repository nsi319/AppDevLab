package com.appdevlab.experiment2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;

public class MainActivity extends AppCompatActivity {

    EditText num1, num2;
    TextView ans;
    RadioGroup radio;
    Button calculate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        num1 = (EditText) findViewById(R.id.num1);
        num2 = (EditText) findViewById(R.id.num2);
        ans = (TextView) findViewById(R.id.result);

        radio=(RadioGroup)findViewById(R.id.radio);

        calculate = (Button) findViewById(R.id.calculate);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = num1.getText().toString();
                String b = num2.getText().toString();
                if(a.isEmpty() || b.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Required fields cannot be empty", Toast.LENGTH_LONG).show();
                }
                else {
                    Float val1 = Float.valueOf(a);
                    Float val2 = Float.valueOf(b);
                    int id=radio.getCheckedRadioButtonId();
                    switch (id) {
                        case R.id.add: ans.setText(String.valueOf(val1+val2));
                        break;
                        case R.id.sub: ans.setText(String.valueOf(val1-val2));
                        break;
                        case R.id.mul: ans.setText(String.valueOf(val1*val2));
                        break;
                        case R.id.div: ans.setText(String.valueOf(val1/val2));
                        break;
                        default: ans.setText("Choose one button");
                    }
                }
            }
        });


    }
}
