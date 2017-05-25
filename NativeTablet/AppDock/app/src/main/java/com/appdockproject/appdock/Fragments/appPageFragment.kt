package com.appdockproject.appdock.Fragments


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
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView

import com.appdockproject.appdock.Data.App
import com.appdockproject.appdock.Data.AppViewHolder
import com.appdockproject.appdock.R
import com.appdockproject.appdock.TwilioSMS
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

import android.content.Context.LAYOUT_INFLATER_SERVICE

class appPageFragment : Fragment() {

    internal var TAG = "appsPage"

    private var mPopupWindow: PopupWindow? = null
    private var mLinearLayout: LinearLayout? = null
    private var popUpView: View? = null
    internal var popUpInflater: LayoutInflater? = null

    internal var mAdapter: FirebaseRecyclerAdapter<*, *>? = null
    internal var mAppGridView: RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Set up Layoutinflater for the popup windows
        popUpInflater = context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // Inflate the layout for this fragment
        val v = inflater!!.inflate(R.layout.activity_app_page, container, false)

        val headerTitle = v.findViewById(R.id.titleOfFragment) as TextView
        headerTitle.setText(R.string.appTitle)

        mLinearLayout = v.findViewById(R.id.appPage_layout) as LinearLayout

        // Setup firebase to get information about apps
        val mDatabase = FirebaseDatabase.getInstance().getReference("Apps")

        mAppGridView = v.findViewById(R.id.appDrawer) as RecyclerView
        mAppGridView!!.setHasFixedSize(false)
        val gm = GridLayoutManager(context, 3)
        mAppGridView!!.layoutManager = gm
        mAdapter = object : FirebaseRecyclerAdapter<App, AppViewHolder>(App::class.java, R.layout.app_icon, AppViewHolder::class.java, mDatabase) {

            override fun populateViewHolder(viewHolder: AppViewHolder, app: App, position: Int) {

                Glide.with(context).load(app.logo).into(viewHolder.logo)
                viewHolder.setAppName(app.name!!)

                viewHolder.setOnClickListener(object : AppViewHolder.ClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        setupWindow(app)
                    }
                })
            }
        }

        mAppGridView!!.adapter = mAdapter

        return v
    }

    /**
     * Opens a popup window populating activity_app.xml with relevant information
     * extracted from an App object
     * @param app
     */
    internal fun setupWindow(app: App) {
        // Inflate the custom layout/view
        popUpView = popUpInflater!!.inflate(R.layout.activity_app, null)

        // Initialize a new instance of popup window
        mPopupWindow = PopupWindow(
                popUpView,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        )
        mPopupWindow!!.isFocusable = true

        // Set an elevation value for popup window
        // Call requires API level 21
        if (Build.VERSION.SDK_INT >= 21) {
            mPopupWindow!!.elevation = 5.0f
        }

        Log.i(TAG, "Opening app " + app.name!!)

        // Setup logos
        val logo = popUpView!!.findViewById(R.id.appLogo) as ImageView
        Glide.with(context).load(app.logo).into(logo)
        val dev = popUpView!!.findViewById(R.id.devLogo) as ImageView
        Glide.with(context).load(app.dev1).into(dev)

        // Setup all text in app
        val title = popUpView!!.findViewById(R.id.appTitle) as TextView
        title.text = app.name
        val keyWords = popUpView!!.findViewById(R.id.appKeywords) as TextView
        keyWords.text = app.keywords
        val desc = popUpView!!.findViewById(R.id.appDesc) as TextView
        desc.text = app.desc
        val webLink = popUpView!!.findViewById(R.id.bitly) as TextView
        webLink.text = app.link

        // Get a reference for the custom view close button
        val closeButton = popUpView!!.findViewById(R.id.closeApp) as ImageButton

        // Set a click listener for the popup window close button
        closeButton.setOnClickListener {
            // Dismiss the popup window
            mPopupWindow!!.dismiss()
        }

        //Activity Elements
        val smsBtn = popUpView!!.findViewById(R.id.getAppPhoneBtn) as Button
        val phoneNumInput = popUpView!!.findViewById(R.id.smsNumber) as EditText
        val smsLink = app.smslink

        //get number by Text
        smsBtn.setOnClickListener {
            //to send the text

            val userSMSNum = phoneNumInput.text.toString() //user input number as a string
            
            val ts = TwilioSMS(activity)

            if (ts.sendSMS(userSMSNum, smsLink!!))
                phoneNumInput.text.clear() // Clear text if number is correct
        }

        // Finally, show the popup window at the center location of root relative layout
        mPopupWindow!!.showAtLocation(mLinearLayout, Gravity.CENTER, 0, 0)
    }

}
