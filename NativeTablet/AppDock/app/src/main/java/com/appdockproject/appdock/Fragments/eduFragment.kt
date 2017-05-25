package com.appdockproject.appdock.Fragments

import android.app.Dialog
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.VideoView

import com.appdockproject.appdock.Data.Video
import com.appdockproject.appdock.Data.VideoViewHolder
import com.appdockproject.appdock.R
import com.appdockproject.appdock.VideoActivity
import com.bumptech.glide.Glide

import java.util.ArrayList

import android.content.Context.LAYOUT_INFLATER_SERVICE

class eduFragment : Fragment() {

    internal var TAG = "EduVids"

    private val mPopupWindow: PopupWindow? = null
    private var mLinearLayout: LinearLayout? = null
    private val popUpView: View? = null
    internal var popUpInflater: LayoutInflater? = null

    internal var mGridView: RecyclerView? = null

    internal var videos: ArrayList<Video>? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Set up Layoutinflater for the popup windows
        popUpInflater = context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // Inflate the layout for this fragment
        val v = inflater!!.inflate(R.layout.activity_edu, container, false)

        val headerTitle = v.findViewById(R.id.titleOfFragment) as TextView
        headerTitle.setText(R.string.eduTitle)

        mLinearLayout = v.findViewById(R.id.eduPage_layout) as LinearLayout

        // ADD NEW VIDEOS in this method
        setupVideos()

        setupGridview(v)

        return v
    }

    internal fun setupVideos() {
        videos = ArrayList<Video>()

        // Change to add more videos. Can make as many as you want.

        videos?.add(Video(getString(R.string.video1Title), R.drawable.whatisandroid, R.raw.eduvideo1))
        videos?.add(Video(getString(R.string.video4Title), R.drawable.whatisanemail, R.raw.eduvideo4))
        videos?.add(Video(getString(R.string.video6Title), R.drawable.whatisanapp, R.raw.eduvideo6))

    }

    internal fun setupGridview(v: View) {
        mGridView = v.findViewById(R.id.videos_grid) as RecyclerView
        mGridView?.setHasFixedSize(false)
        val gm = GridLayoutManager(context, 3) // Use 3 columns
        mGridView?.layoutManager = gm

        val mAdapter = object : RecyclerView.Adapter<VideoViewHolder>() {
            override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): VideoViewHolder {
                val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.video_icon, null)

                val vvh = VideoViewHolder(view)
                return vvh
            }

            override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
                //Set the name of the video
                holder.setVidName(videos?.get(position)?.name!!)
                //Set the image of the video
                Glide.with(context).load(videos?.get(position)?.imageResource).into(holder.vidLogo)

                holder.setOnClickListener(object : VideoViewHolder.ClickListener {
                    override fun onItemClick(view: View, pos: Int) {
                        val intent = Intent(context, VideoActivity::class.java)
                        intent.putExtra("VIDEO_NAME", videos?.get(position)?.name)
                        intent.putExtra("VIDEO_RESOURCE", videos?.get(position)?.videoResource)
                        startActivity(intent)
                    }
                })
            }

            override fun getItemCount(): Int {
                return videos?.size!!
            }

        }
        mGridView?.adapter = mAdapter
    }

}
