package com.example.clima;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Search extends AppCompatActivity {
    public static final String EXTRA = "com.example.clima.EXTRA";
    EditText city;
    Button get;
    String citynames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        city = findViewById(R.id.searchcity);
        get = findViewById(R.id.btngetweather);

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                citynames = city.getText().toString().trim();
                openbackwards(citynames);
            }
        });

    }

    private void openbackwards(String s) {
        Intent in = new Intent(this,Splash_screen.class);
        in.putExtra(EXTRA,s);
        startActivity(in);
        overridePendingTransition(R.anim.slide_left,R.anim.slide_left);
        Search.this.finish();
    }
}
