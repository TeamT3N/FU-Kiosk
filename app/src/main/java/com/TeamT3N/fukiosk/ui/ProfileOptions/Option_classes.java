package com.TeamT3N.fukiosk.ui.Profile.ProfileOptions;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.TeamT3N.fukiosk.R;
import com.TeamT3N.fukiosk.ui.Profile.ProfileOptions.Classes.capstone;

public class Option_classes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_classes);




            Button subject_it414ag = findViewById(R.id.it414a);
                subject_it414ag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(Option_classes.this, capstone.class);
                    startActivity(i);
                }

                });


    }
}
