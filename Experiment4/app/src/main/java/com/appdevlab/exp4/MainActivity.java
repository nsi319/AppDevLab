package com.appdevlab.exp4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String name, mail;
    EditText name_text, mail_text;
    Fragment1 frag1;
    Fragment2 frag2;
    FragmentManager fragmentManager;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void addFragment1(String name, int choice) {

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        frag1 = new Fragment1();
        Bundle args = new Bundle();
        args.putString("name", name);
        frag1.setArguments(args);

        fragmentTransaction.replace(R.id.fragmentContainer, frag1);
        fragmentTransaction.commit();
        if(choice==1)
        Toast.makeText(this, "Welcome to Fragment 1 (choose department)", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Welcome to Fragment 1 (from back)", Toast.LENGTH_SHORT).show();
    }

    private void addFragment2(String result) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        frag2 = new Fragment2(result);
        fragmentTransaction.replace(R.id.fragmentContainer, frag2);
        fragmentTransaction.commit();
    }

    public void handleMainSubmit(View view) {
        name_text = (EditText) findViewById(R.id.name);
        mail_text = (EditText) findViewById(R.id.mail);

        name = (name_text.getText().toString());
        mail = (mail_text.getText().toString());

        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(mail) ){
            Toast.makeText(this, "Required fields cannot be empty", Toast.LENGTH_SHORT).show();
        }
        else {
            addFragment1(name,1);
        }
    }

    public void Fragment1Submit(View view) {
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        int dept = radioGroup.getCheckedRadioButtonId();

        String result = "Your department is: ";
        result += ((RadioButton)findViewById(dept)).getText().toString();
        addFragment2(result);
    }

    public void Fragment2Back(View view) {
        name_text = (EditText) findViewById(R.id.name);
        mail_text = (EditText) findViewById(R.id.mail);
        name = (name_text.getText().toString());
        mail = (mail_text.getText().toString());
        addFragment1(name, 2);
    }
}
