package com.appdockproject.appdock;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.appdockproject.appdock.apps_activities.App1Activity;
import com.appdockproject.appdock.apps_activities.App2Activity;
import com.appdockproject.appdock.apps_activities.App3Activity;
import com.appdockproject.appdock.apps_activities.App4Activity;
import com.appdockproject.appdock.apps_activities.App5Activity;
import com.appdockproject.appdock.apps_activities.App6Activity;
import com.appdockproject.appdock.apps_activities.App7Activity;
import com.appdockproject.appdock.apps_activities.App8Activity;
import com.appdockproject.appdock.apps_activities.App9Activity;

import static com.appdockproject.appdock.R.drawable.g;


public class appPage extends Fragment {

    public appPage() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.activity_app_page, container, false);

        //Buttons to the app pages
        ImageButton app1 = (ImageButton) v.findViewById(R.id.aImage);
        ImageButton app2 = (ImageButton) v.findViewById(R.id.bImage);
        ImageButton app3 = (ImageButton) v.findViewById(R.id.cImage);
        ImageButton app4 = (ImageButton) v.findViewById(R.id.dImage);
        ImageButton app5 = (ImageButton) v.findViewById(R.id.eImage);
        ImageButton app6 = (ImageButton) v.findViewById(R.id.fImage);
        ImageButton app7 = (ImageButton) v.findViewById(R.id.gImage);
        ImageButton app8 = (ImageButton) v.findViewById(R.id.hImage);
        ImageButton app9 = (ImageButton) v.findViewById(R.id.iImage);

        //Listeners to navigate to the app activities
        app1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), App1Activity.class);
                startActivity(intent);
            }
        });

        app2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), App2Activity.class);
                startActivity(intent);
            }
        });

        app3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), App3Activity.class);
                startActivity(intent);
            }
        });

        app4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), App4Activity.class);
                startActivity(intent);
            }
        });

        app5.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), App5Activity.class);
                startActivity(intent);
            }
        });

        app6.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), App6Activity.class);
                startActivity(intent);
            }
        });

        app7.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), App7Activity.class);
                startActivity(intent);
            }
        });

        app8.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), App8Activity.class);
                startActivity(intent);
            }
        });

        app9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), App9Activity.class);
                startActivity(intent);
            }
        });

        return v;
    }
}
