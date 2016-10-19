package com.appdockproject.appdock;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class eduActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edu);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int UI_OPTIONS = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            getWindow().getDecorView().setSystemUiVisibility(UI_OPTIONS);
        }

 /**       //Starting to add support for facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this); **/

        Button devBtn = (Button) findViewById(R.id.devBtn);
        Button homeBtn = (Button) findViewById(R.id.appBtn);
        Button cmntBtn = (Button) findViewById(R.id.comBtn);
        Button fbBtn = (Button) findViewById(R.id.fbBtn);

        ImageButton vid1 = (ImageButton) findViewById(R.id.gmail);
        ImageButton vid2 = (ImageButton) findViewById(R.id.mobileapp);
        ImageButton vid3 = (ImageButton) findViewById(R.id.play);
        ImageButton vid4 = (ImageButton) findViewById(R.id.download);

        devBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(eduActivity.this, devActivity.class);
                startActivity(intent);
            }
        });


        homeBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(eduActivity.this, appPage.class);
                startActivity(intent);
            }
        });

        cmntBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(eduActivity.this, feedbackActivity.class);
                startActivity(intent);
            }
        });

        fbBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(eduActivity.this, facebookActivity.class);
                startActivity(intent);
            }
        });

        //to play videos -- only first button goes somewhere right now

        vid1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(eduActivity.this, eduVid1Activity.class);
                startActivity(intent);
            }
        });


    }
}
