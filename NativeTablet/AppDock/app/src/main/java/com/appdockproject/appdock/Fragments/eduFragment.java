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

import com.appdockproject.appdock.Data.DeveloperHolder;
import com.appdockproject.appdock.Data.Video;
import com.appdockproject.appdock.Data.VideoViewHolder;
import com.appdockproject.appdock.R;
import com.appdockproject.appdock.VideoActivities.eduVid1Activity;
import com.appdockproject.appdock.VideoActivities.eduVid4Activity;
import com.appdockproject.appdock.VideoActivities.eduVid5Activity;
import com.appdockproject.appdock.VideoActivities.eduVid6Activity;
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

    public eduFragment() {}

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

    void setupVideos(){
        videos = new ArrayList<>();

        // Change to add more videos. Can make as many as you want.
        for (int i=0; i<9; i++){
            switch (i){
                case 1:
                    videos.add(new Video(getString(R.string.video1Title), R.drawable.whatisandroid, eduVid1Activity.class));
                    break;
                case 2: case 3:
                    break;
                case 4:
                    videos.add(new Video(getString(R.string.video4Title), R.drawable.whatisanemail, eduVid4Activity.class));
                    break;
                case 5:
                    //videos.add(new Video(getString(R.string.video5Title), R.drawable.whatisanapp, eduVid5Activity.class));
                    break;
                case 6:
                    videos.add(new Video(getString(R.string.video6Title), R.drawable.whatisanapp, eduVid6Activity.class));
                    break;
            }
        }
    }

    void setupGridview(View v){
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
                        Intent intent = new Intent(getContext(), videos.get(position).videoResource);
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

    void setupPopup(int activity_vid, int raw_vid){

        // Inflate the custom layout/view
        popUpView = popUpInflater.inflate(activity_vid, null);

        // Initialize a new instance of popup window
        mPopupWindow = new PopupWindow(
                popUpView,
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        mPopupWindow.setFocusable(true);

        // Set an elevation value for popup window
        // Call requires API level 21
        if (Build.VERSION.SDK_INT >= 21) {
            mPopupWindow.setElevation(5.0f);
        }

        // Get a reference for the custom view close button
        ImageButton closeButton = (ImageButton) popUpView.findViewById(R.id.closeWindow);

        // Set a click listener for the popup window close button
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                mPopupWindow.dismiss();
            }
        });

        Log.i(TAG, "Setting up popup for " + getResources().getResourceEntryName(activity_vid));

        // Finally, show the popup window at the center location of root relative layout
        mPopupWindow.showAtLocation(mLinearLayout, Gravity.CENTER, 0, 0);

        //Activity Elements
        String videoLink = "android.resource://com.appdockproject.appdock/" + raw_vid;
        final VideoView videoView = (VideoView) popUpView.findViewById(R.id.videoView);

        videoView.setVideoPath(videoLink);

        MediaController mediaController = new MediaController(getActivity());
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        videoView.start();

        Log.i(TAG, "Video duration: " + videoView.getDuration());
        Log.i(TAG, "Video context: " + videoView.getContext());
        Log.i(TAG, "Video TransitionName: " + videoView.getTransitionName());

    }

    void setupDialogView(int activity_vid, int raw_vid){
        final Dialog dialog = new Dialog(getActivity());// add here your class name
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(activity_vid);//add your own xml with defied with and height of videoview
        dialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.copyFrom(dialog.getWindow().getAttributes());
        dialog.getWindow().setAttributes(lp);

        // Get a reference for the custom view close button
        ImageButton closeButton = (ImageButton) dialog.findViewById(R.id.closeWindow);

        // Set a click listener for the popup window close button
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                mPopupWindow.dismiss();
            }
        });

        getActivity().getWindow().setFormat(PixelFormat.TRANSLUCENT);

        String videoLink = "android.resource://com.appdockproject.appdock/" + raw_vid;
        final VideoView videoView = (VideoView) dialog.findViewById(R.id.videoView);

        videoView.setVideoPath(videoLink);

        MediaController mediaController = new MediaController(getActivity());
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

    }
}
