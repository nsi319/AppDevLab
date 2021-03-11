package com.appdevlab.experiment6;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);
        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse("mailto:"
                        + ((TextView)findViewById(R.id.to)).getText().toString()
                        + "?subject=" + ((TextView)findViewById(R.id.subject)).getText().toString() + "&body=" + ((TextView)findViewById(R.id.body)).getText().toString());
                intent.setData(data);
                startActivity(intent);
            }
        });

    }
}
