package com.appdockproject.appdock.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.appdockproject.appdock.Data.App;
import com.appdockproject.appdock.Data.Developer;
import com.appdockproject.appdock.Data.DeveloperHolder;
import com.appdockproject.appdock.R;
import com.appdockproject.appdock.TwilioSMS;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.ChangeEventListener;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.appdockproject.appdock.R.color.titles;
import static com.appdockproject.appdock.R.drawable.e;
import static com.appdockproject.appdock.R.drawable.g;
import static com.appdockproject.appdock.R.string.app;

public class devFragment extends Fragment {

    String TAG = "devPage";

    PopupWindow mPopupWindow;
    RecyclerView mListView;
    View popUpView;
    LayoutInflater popUpInflater;
    FirebaseRecyclerAdapter mAdapter;

    LinearLayout mLinearLayout;

    App[] apps = new App[9];
    TextView[] titles = new TextView[9];
    LinearLayout[] buttons = new LinearLayout[9];

    public devFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.activity_dev, container, false);

        mLinearLayout = (LinearLayout) v.findViewById(R.id.devFragment);

        // Set up Layoutinflater for the popup windows
        popUpInflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        // Setup firebase to get information about apps
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Developers");
        //setupFirebase(mDatabase);

        mListView = (RecyclerView) v.findViewById(R.id.developers_layout);
        mListView.setHasFixedSize(false);
        GridLayoutManager gm = new GridLayoutManager(getContext(), 2);
        mListView.setLayoutManager(gm);
        mAdapter = new FirebaseRecyclerAdapter<Developer, DeveloperHolder>(Developer.class, R.layout.dev_icon, DeveloperHolder.class, mDatabase) {

            @Override
            protected void populateViewHolder(DeveloperHolder viewHolder, final Developer dev, int position) {
                Glide.with(getContext()).load(dev.getDevPic()).into(viewHolder.devPic);
                Glide.with(getContext()).load(dev.getAppPic()).into(viewHolder.appLogo);
                viewHolder.setDevelopers(dev);

                viewHolder.setOnClickListener(new DeveloperHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        openPopupWindow(dev);
                    }
                });
            }
        };

        mListView.setAdapter(mAdapter);

        return v;
    }

    /**
     * Opens a popup window populating activity_app.xml with relevant information
     * extracted from an App object
     *
     * @param dev
     */
    private void openPopupWindow(Developer dev) {

        // Inflate the custom layout/view
        popUpView = popUpInflater.inflate(R.layout.developer_info, null);

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

        Log.i(TAG, "Opening developers of app " + dev.getName());

        // Setup logos
        ImageView logo = (ImageView) popUpView.findViewById(R.id.appLogo);
        Glide.with(getContext()).load(dev.getAppPic()).into(logo);

        ImageView dev_im = (ImageView) popUpView.findViewById(R.id.devLogo);
        Glide.with(getContext()).load(dev.getDevPic()).into(dev_im);

        // Setup all text in app
        TextView title = (TextView) popUpView.findViewById(R.id.appTitle);
        title.setText(dev.getName());
        TextView developers = (TextView) popUpView.findViewById(R.id.developerNames);
        developers.setText(getDevelopers(dev));
        TextView desc = (TextView) popUpView.findViewById(R.id.devDescription);
        desc.setText(dev.getDescription());

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

        // Finally, show the popup window at the center location of root relative layout
        mPopupWindow.showAtLocation(mLinearLayout, Gravity.CENTER, 0, 0);
    }

    private String getDevelopers(Developer dev) {

        StringBuilder sb = new StringBuilder();

        sb.append(dev.getDev1());

        if (dev.getDev2() != null)
            sb.append(", ").append(dev.getDev2());

        if (dev.getDev3() != null)
            sb.append(", ").append(dev.getDev3());

        return sb.toString();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
    }
}
