package com.appdevlab.experiment6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NoteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent noteIntent = new Intent(Intent.ACTION_SEND)
                        .putExtra(Intent.EXTRA_TITLE, ((TextView)findViewById(R.id.title)).getText().toString())
                        .putExtra(Intent.EXTRA_TEXT, ((TextView)findViewById(R.id.message)).getText().toString());
                noteIntent.setType("text/plain");
                startActivity(Intent.createChooser(noteIntent, "Create note using"));
            }
        });
    }
}
