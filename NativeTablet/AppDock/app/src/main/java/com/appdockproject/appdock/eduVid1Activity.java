package com.appdockproject.appdock;

import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

/**
 * Created by CS on 7/31/16.
 */
public class eduVid1Activity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eduvid1);

        Button playVideo = (Button) findViewById(R.id.button1);

        getWindow().setFormat(PixelFormat.UNKNOWN);

        //displays video file
        VideoView video1 = (VideoView) findViewById(R.id.videoView);

        String uriPath = "android.resource://com.appdockproject.appdock/"+R.raw.crossdakarcity;
        Uri uri = Uri.parse(uriPath);
        video1.setVideoURI(uri);
        video1.requestFocus();
        video1.start();

        playVideo.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View view) {
                VideoView video1 = (VideoView) findViewById(R.id.videoView);

                String uriPath = "android.resource://com.appdockproject.appdock/"+R.raw.crossdakarcity;
                Uri uri = Uri.parse(uriPath);
                video1.setVideoURI(uri);
                video1.requestFocus();
                video1.start();
            }
        });

        }
    }

