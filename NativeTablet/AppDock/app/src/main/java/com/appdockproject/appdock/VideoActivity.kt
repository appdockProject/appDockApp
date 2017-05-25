package com.appdockproject.appdock

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView

/**
 * Created by jangerhard on 05-May-17.
 */

class VideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val name = intent.getStringExtra("VIDEO_NAME")
        val video_resource = intent.getIntExtra("VIDEO_RESOURCE", 0)

        if (video_resource == 0) {
            Log.e("VideoActivity", "No video source was given when activity started")
            finish()
        }

        setContentView(R.layout.activity_video)

        val closeButton = findViewById(R.id.closeWindow) as ImageButton
        closeButton.setOnClickListener { finish() }

        val tv = findViewById(R.id.titleOfVideo) as TextView
        tv.text = name

        val videoLink = "android.resource://com.appdockproject.appdock/" + video_resource
        val videoView = findViewById(R.id.videoView) as VideoView

        videoView.setVideoPath(videoLink)

        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        videoView.start()

    }
}
