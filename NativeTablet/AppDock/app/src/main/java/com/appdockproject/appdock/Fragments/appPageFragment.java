package com.appdockproject.appdock.Fragments;


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
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.appdockproject.appdock.Data.App;
import com.appdockproject.appdock.Data.AppViewHolder;
import com.appdockproject.appdock.R;
import com.appdockproject.appdock.TwilioSMS;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class appPageFragment extends Fragment {

    String TAG = "appsPage";

    private PopupWindow mPopupWindow;
    private LinearLayout mLinearLayout;
    private View popUpView;
    LayoutInflater popUpInflater;

    FirebaseRecyclerAdapter mAdapter;
    RecyclerView mAppGridView;

    public appPageFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Set up Layoutinflater for the popup windows
        popUpInflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_app_page, container, false);

        TextView headerTitle = (TextView) v.findViewById(R.id.titleOfFragment);
        headerTitle.setText(R.string.appTitle);

        mLinearLayout = (LinearLayout) v.findViewById(R.id.appPage_layout);

        // Setup firebase to get information about apps
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Apps");

        mAppGridView = (RecyclerView) v.findViewById(R.id.appDrawer);
        mAppGridView.setHasFixedSize(false);
        GridLayoutManager gm = new GridLayoutManager(getContext(), 3);
        mAppGridView.setLayoutManager(gm);
        mAdapter = new FirebaseRecyclerAdapter<App, AppViewHolder>(App.class, R.layout.app_icon, AppViewHolder.class, mDatabase) {

            @Override
            protected void populateViewHolder(AppViewHolder viewHolder, final App app, int position) {

                Glide.with(getContext()).load(app.getLogo()).into(viewHolder.logo);
                viewHolder.setAppName(app.getName());

                viewHolder.setOnClickListener(new AppViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        setupWindow(app);
                    }
                });
            }
        };

        mAppGridView.setAdapter(mAdapter);

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

        // Setup logos
        ImageView logo = (ImageView) popUpView.findViewById(R.id.appLogo);
        Glide.with(getContext()).load(app.getLogo()).into(logo);
        ImageView dev = (ImageView) popUpView.findViewById(R.id.devLogo);
        Glide.with(getContext()).load(app.getDev1()).into(dev);

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

}
