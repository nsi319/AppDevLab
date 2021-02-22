package com.appdevlab.experiment3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.itis.libs.parserng.android.expressParser.MathExpression;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList <Button> numbers = new ArrayList<>(17);
    ArrayList<Integer> ids = new ArrayList<>();
    TextView ans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ids.add(R.id.num0);
        ids.add(R.id.num1);
        ids.add(R.id.num2);
        ids.add(R.id.num3);
        ids.add(R.id.num4);
        ids.add(R.id.num5);
        ids.add(R.id.num6);
        ids.add(R.id.num7);
        ids.add(R.id.num8);
        ids.add(R.id.num9);

        ids.add(R.id.numdec);
        ids.add(R.id.clear);

        ids.add(R.id.add);
        ids.add(R.id.sub);
        ids.add(R.id.mul);
        ids.add(R.id.div);
        ids.add(R.id.equal);

        ans = (TextView) findViewById(R.id.result);

        for(int i=0;i<17;i++) {
            numbers.add((Button) findViewById(ids.get(i)));
        }
    }
    public void inputNum (View v) {
        int id = v.getId();
        if(id==ids.get(16)) {
            if(ans.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter some expression", Toast.LENGTH_LONG).show();
            }
            else {
                MathExpression expr = new MathExpression(ans.getText().toString());
                ans.setText(expr.solve());
            }
        }
        else if(id==ids.get(11)) {
            ans.setText("");
        }
        else {
            String prev = ans.getText().toString();
            ans.setText(prev + ((Button) findViewById(id)).getText().toString());
        }
    }


}
