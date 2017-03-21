package com.appdockproject.appdock;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class eduVid6Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eduvid6);


        String videoLink = "android.resource://com.appdockproject.appdock/"+R.raw.eduvideo6;
        final VideoView videoView = (VideoView) findViewById(R.id.videoView);

        videoView.setVideoPath(videoLink);

        MediaController mediaController = new
                MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);



        videoView.start();

    }
}
