package com.appdockproject.appdock;


import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class appPage extends Fragment {

    private PopupWindow mPopupWindow;
    private LinearLayout mLinearLayout;
    private View popUpView;
    LayoutInflater popUpInflater;

    public appPage(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Set up Layoutinflater for the popup windows
        popUpInflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_app_page, container, false);

        mLinearLayout = (LinearLayout) v.findViewById(R.id.appPage_layout);

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
        app1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setupPopup(R.layout.activity_app1, R.string.app1SMSLink);
            }
        });

        app2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setupPopup(R.layout.activity_app2, R.string.app2SMSLink);
            }
        });

        app3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setupPopup(R.layout.activity_app3, R.string.app3SMSLink);
            }
        });

        app4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setupPopup(R.layout.activity_app4, R.string.app4SMSLink);
            }
        });

        app5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setupPopup(R.layout.activity_app5, R.string.app5SMSLink);
            }
        });

        app6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setupPopup(R.layout.activity_app6, R.string.app6SMSLink);
            }
        });

        app7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setupPopup(R.layout.activity_app7, R.string.app7SMSLink);
            }
        });

        app8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setupPopup(R.layout.activity_app8, R.string.app8SMSLink);
            }
        });

        app9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setupPopup(R.layout.activity_app9, R.string.app9SMSLink);
            }
        });

        return v;
    }

    void setupPopup(int activity_app, int appSMSLink){

        // Inflate the custom layout/view
        popUpView = popUpInflater.inflate(activity_app, null);

        // Initialize a new instance of popup window
        mPopupWindow = new PopupWindow(
                popUpView,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        mPopupWindow.setFocusable(true);

        // Set an elevation value for popup window
        // Call requires API level 21
        if (Build.VERSION.SDK_INT >= 21) {
            mPopupWindow.setElevation(5.0f);
        }

        // Get a reference for the custom view close button
        ImageButton closeButton = (ImageButton) popUpView.findViewById(R.id.wolofTop);

        // Set a click listener for the popup window close button
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                mPopupWindow.dismiss();
            }
        });

        //Activity Elements
        Button smsBtn = (Button) popUpView.findViewById(R.id.getAppPhoneBtn);
        final EditText phoneNumInput = (EditText) popUpView.findViewById(R.id.smsNumber);
        final String smsLink = getString(appSMSLink);

        //get number by Text
        smsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //to send the text

                String userSMSNum = phoneNumInput.getText().toString(); //user input number as a string

                TwilioSMS ts = new TwilioSMS(getActivity());

                if (ts.sendSMS(userSMSNum, smsLink))
                    phoneNumInput.getText().clear();

            }
        });

        // Finally, show the popup window at the center location of root relative layout
        mPopupWindow.showAtLocation(mLinearLayout, Gravity.CENTER, 0, 0);
    }
}
