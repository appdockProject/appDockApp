package com.appdockproject.appdock;


import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;


public class appPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_page);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int UI_OPTIONS = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            getWindow().getDecorView().setSystemUiVisibility(UI_OPTIONS);
        }
        Button devBtn = (Button) findViewById(R.id.devBtn);
        Button eduBtn = (Button) findViewById(R.id.eduBtn);
        Button cmntBtn = (Button) findViewById(R.id.comBtn);
        Button fbBtn = (Button) findViewById(R.id.fbBtn);

        //Buttons to the app pages
        ImageButton app1 = (ImageButton) findViewById(R.id.aImage);
        ImageButton app2 = (ImageButton) findViewById(R.id.bImage);
        ImageButton app3 = (ImageButton) findViewById(R.id.cImage);
        ImageButton app4 = (ImageButton) findViewById(R.id.dImage);
        ImageButton app5 = (ImageButton) findViewById(R.id.eImage);
        ImageButton app6 = (ImageButton) findViewById(R.id.fImage);
        ImageButton app7 = (ImageButton) findViewById(R.id.gImage);
        ImageButton app8 = (ImageButton) findViewById(R.id.hImage);
        ImageButton app9 = (ImageButton) findViewById(R.id.iImage);



        //navigation bar buttons
        devBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(appPage.this, devActivity.class);
                startActivity(intent);
            }
        });


        eduBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(appPage.this, eduActivity.class);
                startActivity(intent);
            }
        });

        cmntBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(appPage.this, feedbackActivity.class);
                startActivity(intent);
            }
        });

        fbBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(appPage.this, facebookActivity.class);
                startActivity(intent);
            }
        });


        //Listeners to navigate to the app activities
        app1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(appPage.this, App1Activity.class);
                startActivity(intent);
            }
        });

        app2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(appPage.this, App2Activity.class);
                startActivity(intent);
            }
        });

        app3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(appPage.this, App3Activity.class);
                startActivity(intent);
            }
        });

        app4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(appPage.this, App4Activity.class);
                startActivity(intent);
            }
        });

        app5.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(appPage.this, App5Activity.class);
                startActivity(intent);
            }
        });

        app6.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(appPage.this, App6Activity.class);
                startActivity(intent);
            }
        });

        app7.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(appPage.this, App7Activity.class);
                startActivity(intent);
            }
        });

        app8.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(appPage.this, App8Activity.class);
                startActivity(intent);
            }
        });

        app9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(appPage.this, App9Activity.class);
            }
        });

    }
}
