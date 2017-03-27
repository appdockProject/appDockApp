package com.appdockproject.appdock.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.appdockproject.appdock.R;
import com.appdockproject.appdock.VideoActivities.eduVid1Activity;
import com.appdockproject.appdock.VideoActivities.eduVid4Activity;
import com.appdockproject.appdock.VideoActivities.eduVid5Activity;
import com.appdockproject.appdock.VideoActivities.eduVid6Activity;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class eduFragment extends Fragment {

    String TAG = "EduVids";

    private PopupWindow mPopupWindow;
    private LinearLayout mLinearLayout;
    private View popUpView;
    LayoutInflater popUpInflater;

    public eduFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Set up Layoutinflater for the popup windows
        popUpInflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_edu, container, false);

        mLinearLayout = (LinearLayout) v.findViewById(R.id.eduPage_layout);

        ImageButton vid1 = (ImageButton) v.findViewById(R.id.gmail);
        ImageButton vid2 = (ImageButton) v.findViewById(R.id.mobileapp);
        ImageButton vid3 = (ImageButton) v.findViewById(R.id.play);
        ImageButton vid4 = (ImageButton) v.findViewById(R.id.download);

        vid1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //setupPopup(R.layout.activity_eduvid1, R.raw.eduvideo1);
                Intent intent = new Intent(getContext(), eduVid1Activity.class);
                startActivity(intent);
            }
        });

        vid2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //setupPopup(R.layout.activity_eduvid4, R.raw.eduvideo4);
                //setupDialogView(R.layout.activity_eduvid4, R.raw.eduvideo4);
                Intent intent = new Intent(getContext(), eduVid4Activity.class);
                startActivity(intent);
            }
        });

        vid3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //setupPopup(R.layout.activity_eduvid5, R.raw.eduvideo5);
                Intent intent = new Intent(getContext(), eduVid5Activity.class);
                startActivity(intent);
            }
        });

        vid4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //setupPopup(R.layout.activity_eduvid6, R.raw.eduvideo6);
                Intent intent = new Intent(getContext(), eduVid6Activity.class);
                startActivity(intent);
            }
        });

        return v;
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
