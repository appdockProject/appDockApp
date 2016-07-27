package com.appdockproject.appdock;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class devActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int UI_OPTIONS = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            getWindow().getDecorView().setSystemUiVisibility(UI_OPTIONS);
        }

        Button homeBtn = (Button) findViewById(R.id.appBtn);
        Button eduBtn = (Button) findViewById(R.id.eduBtn);
        Button cmntBtn = (Button) findViewById(R.id.comBtn);
        Button fbBtn = (Button) findViewById(R.id.fbBtn);

        ImageButton dev1 = (ImageButton) findViewById(R.id.dev1);


        homeBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(devActivity.this, appPage.class);
                startActivity(intent);
            }
        });


        eduBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(devActivity.this, eduActivity.class);
                startActivity(intent);
            }
        });

        cmntBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(devActivity.this, feedbackActivity.class);
                startActivity(intent);
            }
        });

        fbBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(devActivity.this, facebookActivity.class);
                startActivity(intent);
            }
        });


        //go to developer pages
        dev1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(devActivity.this, dev1Activity.class);
                startActivity(intent);
            }
        });

    }
}
