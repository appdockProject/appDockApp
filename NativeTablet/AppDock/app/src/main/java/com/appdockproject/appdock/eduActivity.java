package com.appdockproject.appdock;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import static com.appdockproject.appdock.R.id.devBtn;
import static com.appdockproject.appdock.R.id.fbBtn;

public class eduActivity extends Fragment {

    public eduActivity() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.activity_edu, container, false);



        ImageButton vid1 = (ImageButton) v.findViewById(R.id.gmail);
        ImageButton vid2 = (ImageButton) v.findViewById(R.id.mobileapp);
        ImageButton vid3 = (ImageButton) v.findViewById(R.id.play);
        ImageButton vid4 = (ImageButton) v.findViewById(R.id.download);

        //to play videos -- only first button goes somewhere right now

        vid1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), eduVid1Activity.class);
                startActivity(intent);
            }
        });

        vid2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), eduVid4Activity.class);
                startActivity(intent);
            }
        });

        vid3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), eduVid5Activity.class);
                startActivity(intent);
            }
        });

        vid4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), eduVid6Activity.class);
                startActivity(intent);
            }
        });

        return v;
    }
}
