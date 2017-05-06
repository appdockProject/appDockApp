package com.appdockproject.appdock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

/**
 * Created by jangerhard on 05-May-17.
 */

public class VideoActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        String name = intent.getStringExtra("VIDEO_NAME");
        int video_resource = intent.getIntExtra("VIDEO_RESOURCE", 0);

        if (video_resource == 0){
            Log.e("VideoActivity", "No video source was given when activity started");
            finish();
        }

        setContentView(R.layout.activity_video);

        ImageButton closeButton = (ImageButton) findViewById(R.id.closeWindow);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView tv = (TextView) findViewById(R.id.titleOfVideo);
        tv.setText(name);

        String videoLink = "android.resource://com.appdockproject.appdock/" + video_resource;
        final VideoView videoView = (VideoView) findViewById(R.id.videoView);

        videoView.setVideoPath(videoLink);

        MediaController mediaController = new
                MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        videoView.start();

    }
}
