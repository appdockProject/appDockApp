package com.appdockproject.appdock;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.appdockproject.appdock.Data.App;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.R.id.closeButton;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.appdockproject.appdock.R.drawable.a;

public class appPage extends Fragment {

    String TAG = "appsPage";

    private PopupWindow mPopupWindow;
    private LinearLayout mLinearLayout;
    private View popUpView;
    LayoutInflater popUpInflater;

    App[] apps = new App[9];

    public appPage(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Set up Layoutinflater for the popup windows
        popUpInflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_app_page, container, false);

        mLinearLayout = (LinearLayout) v.findViewById(R.id.appPage_layout);

        // Setup firebase to get information about apps
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Apps");

        DatabaseReference app1_ref = mDatabase.child("app1"),
            app2_ref = mDatabase.child("app2"),
            app3_ref = mDatabase.child("app3"),
            app4_ref = mDatabase.child("app4"),
            app5_ref = mDatabase.child("app5"),
            app6_ref = mDatabase.child("app6"),
            app7_ref = mDatabase.child("app7"),
            app8_ref = mDatabase.child("app8"),
            app9_ref = mDatabase.child("app9");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                int key = Integer.parseInt(dataSnapshot.getKey().replace("app", ""));
                Log.i(TAG, "Datachange on app number: " + key);
                App app = dataSnapshot.getValue(App.class);
                apps[key-1] = app;
                //setupWindow(apps[key]);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadApp:onCancelled", databaseError.toException());
                // ...
            }
        };
        app1_ref.addValueEventListener(postListener);
        app2_ref.addValueEventListener(postListener);
        app3_ref.addValueEventListener(postListener);
        app4_ref.addValueEventListener(postListener);
        app5_ref.addValueEventListener(postListener);
        app6_ref.addValueEventListener(postListener);
        app7_ref.addValueEventListener(postListener);
        app8_ref.addValueEventListener(postListener);
        app9_ref.addValueEventListener(postListener);

        //setupImageButtons();

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
                //setupPopup(R.layout.activity_app1, R.string.app1SMSLink);
                setupWindow(apps[0]);
            }
        });

        app2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //setupPopup(R.layout.activity_app2, R.string.app2SMSLink);
                setupWindow(apps[1]);
            }
        });

        app3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //setupPopup(R.layout.activity_app3, R.string.app3SMSLink);
                setupWindow(apps[2]);
            }
        });

        app4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //setupPopup(R.layout.activity_app4, R.string.app4SMSLink);
                setupWindow(apps[3]);
            }
        });

        app5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //setupPopup(R.layout.activity_app5, R.string.app5SMSLink);
                setupWindow(apps[4]);
            }
        });

        app6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //setupPopup(R.layout.activity_app6, R.string.app6SMSLink);
                setupWindow(apps[5]);
            }
        });

        app7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //setupPopup(R.layout.activity_app7, R.string.app7SMSLink);
                setupWindow(apps[6]);
            }
        });

        app8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //setupPopup(R.layout.activity_app8, R.string.app8SMSLink);
                setupWindow(apps[7]);
            }
        });

        app9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //setupPopup(R.layout.activity_app9, R.string.app9SMSLink);
                setupWindow(apps[8]);
            }
        });

        return v;
    }

    /**
     * Opens a popup window populating activity_app.xml with relevant information
     * extracted from an App object
     * @param app
     */
    void setupWindow(App app){
        // Inflate the custom layout/view
        popUpView = popUpInflater.inflate(R.layout.activity_app, null);

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

        Log.i(TAG, "Opening app " + app.getName());
        Log.i(TAG, "Dev logo: " + app.getDev1().length());
        Log.i(TAG, "Logo:  " + app.getLogo().length());

        // Setup logos
        ImageView logo = (ImageView) popUpView.findViewById(R.id.appLogo);
        if (app.getLogo() != null)
            logo.setImageBitmap(decodeBit64Image(app.getLogo()));
        ImageView dev = (ImageView) popUpView.findViewById(R.id.devLogo);
        if (app.getDev1() != null)
            dev.setImageBitmap(decodeBit64Image(app.getDev1()));

        // Setup all text in app
        TextView title = (TextView) popUpView.findViewById(R.id.appTitle);
        title.setText(app.getName());
        TextView keyWords = (TextView) popUpView.findViewById(R.id.appKeywords);
        keyWords.setText(app.getKeywords());
        TextView desc = (TextView) popUpView.findViewById(R.id.appDesc);
        desc.setText(app.getDesc());
        TextView webLink = (TextView) popUpView.findViewById(R.id.bitly);
        webLink.setText(app.getLink());

        // Get a reference for the custom view close button
        ImageButton closeButton = (ImageButton) popUpView.findViewById(R.id.closeApp);

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
        final String smsLink =  app.getSmslink();

        //get number by Text
        smsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //to send the text

                String userSMSNum = phoneNumInput.getText().toString(); //user input number as a string

                TwilioSMS ts = new TwilioSMS(getActivity());

                if (ts.sendSMS(userSMSNum, smsLink))
                    phoneNumInput.getText().clear(); // Clear text if number is correct

            }
        });

        // Finally, show the popup window at the center location of root relative layout
        mPopupWindow.showAtLocation(mLinearLayout, Gravity.CENTER, 0, 0);
    }

    Bitmap decodeBit64Image(String image){
        byte[] imageAsBytes;
        try {
            imageAsBytes = Base64.decode(image, Base64.DEFAULT);
        } catch (IllegalArgumentException e){
            Log.e(TAG, "Bad bit64..");
            return null;
        }
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }

}
