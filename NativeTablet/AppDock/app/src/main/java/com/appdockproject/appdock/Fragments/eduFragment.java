package com.appdockproject.appdock.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.PopupWindow;
import android.widget.VideoView;

import com.appdockproject.appdock.Data.Video;
import com.appdockproject.appdock.Data.VideoViewHolder;
import com.appdockproject.appdock.R;
import com.appdockproject.appdock.VideoActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class eduFragment extends Fragment {

    String TAG = "EduVids";

    private PopupWindow mPopupWindow;
    private LinearLayout mLinearLayout;
    private View popUpView;
    LayoutInflater popUpInflater;

    RecyclerView mGridView;

    ArrayList<Video> videos;

    public eduFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Set up Layoutinflater for the popup windows
        popUpInflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_edu, container, false);

        mLinearLayout = (LinearLayout) v.findViewById(R.id.eduPage_layout);

        // ADD NEW VIDEOS in this method
        setupVideos();

        setupGridview(v);

        return v;
    }

    void setupVideos() {
        videos = new ArrayList<>();

        // Change to add more videos. Can make as many as you want.

        videos.add(new Video(getString(R.string.video1Title), R.drawable.whatisandroid, R.raw.eduvideo1));
        videos.add(new Video(getString(R.string.video4Title), R.drawable.whatisanemail, R.raw.eduvideo4));
        videos.add(new Video(getString(R.string.video6Title), R.drawable.whatisanapp, R.raw.eduvideo6));

    }

    void setupGridview(View v) {
        mGridView = (RecyclerView) v.findViewById(R.id.videos_grid);
        mGridView.setHasFixedSize(false);
        GridLayoutManager gm = new GridLayoutManager(getContext(), 3); // Use 3 columns
        mGridView.setLayoutManager(gm);

        RecyclerView.Adapter mAdapter = new RecyclerView.Adapter<VideoViewHolder>() {
            @Override
            public VideoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_icon, null);

                VideoViewHolder vvh = new VideoViewHolder(view);
                return vvh;
            }

            @Override
            public void onBindViewHolder(final VideoViewHolder holder, final int position) {
                //Set the name of the video
                holder.setVidName(videos.get(position).getName());
                //Set the image of the video
                Glide.with(getContext()).load(videos.get(position).getImageResource()).into(holder.vidLogo);

                holder.setOnClickListener(new VideoViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int pos) {
                        Intent intent = new Intent(getContext(), VideoActivity.class);
                        intent.putExtra("VIDEO_NAME", videos.get(position).getName());
                        intent.putExtra("VIDEO_RESOURCE", videos.get(position).getVideoResource());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return videos.size();
            }

        };
        mGridView.setAdapter(mAdapter);
    }

}
