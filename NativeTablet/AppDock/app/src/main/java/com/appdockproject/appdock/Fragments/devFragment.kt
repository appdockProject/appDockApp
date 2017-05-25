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

import com.appdockproject.appdock.Data.Developer
import com.appdockproject.appdock.Data.DeveloperHolder
import com.appdockproject.appdock.R
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.widget.*

class devFragment : Fragment() {

    internal var TAG = "devPage"

    internal var mLinearLayout: LinearLayout? = null
    internal var popUpInflater: LayoutInflater? = null
    internal var mAdapter: FirebaseRecyclerAdapter<*, *>? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val v = inflater!!.inflate(R.layout.activity_dev, container, false)

        val headerTitle = v.findViewById(R.id.titleOfFragment) as TextView
        headerTitle.setText(R.string.devTitle)

        mLinearLayout = v.findViewById(R.id.devFragment) as LinearLayout

        // Set up Layoutinflater for the popup windows
        popUpInflater = context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // Setup firebase to get information about apps
        val mDatabase = FirebaseDatabase.getInstance().getReference("Developers")

        val mListView = v.findViewById(R.id.developers_layout) as RecyclerView
        mListView.setHasFixedSize(false)
        val gm = GridLayoutManager(context, 2)
        mListView.layoutManager = gm
        mAdapter = object : FirebaseRecyclerAdapter<Developer, DeveloperHolder>(Developer::class.java, R.layout.dev_icon, DeveloperHolder::class.java, mDatabase) {

            override fun populateViewHolder(viewHolder: DeveloperHolder, dev: Developer, position: Int) {
                Glide.with(context).load(dev.devPic).into(viewHolder.devPic)
                Glide.with(context).load(dev.appPic).into(viewHolder.appLogo)
                viewHolder.setDevelopers(dev)

                viewHolder.setOnClickListener { _, _ -> openPopupWindow(dev) }
            }
        }

        mListView.adapter = mAdapter

        return v
    }

    /**
     * Opens a popup window populating activity_app.xml with relevant information
     * extracted from an App object

     * @param dev
     */
    private fun openPopupWindow(dev: Developer) {

        // Inflate the custom layout/view
        val popUpView = popUpInflater?.inflate(R.layout.developer_info, null)

        // Initialize a new instance of popup window
        val mPopupWindow = PopupWindow(
                popUpView,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        )
        mPopupWindow.isFocusable = true

        // Set an elevation value for popup window
        // Call requires API level 21
        if (Build.VERSION.SDK_INT >= 21) {
            mPopupWindow.elevation = 5.0f
        }

        Log.i(TAG, "Opening developers of app " + dev.name!!)

        // Setup logos
        val logo = popUpView?.findViewById(R.id.appLogo) as ImageView
        Glide.with(context).load(dev.appPic).into(logo)

        val dev_im = popUpView.findViewById(R.id.devLogo) as ImageView
        Glide.with(context).load(dev.devPic).into(dev_im)

        // Setup all text in app
        val title = popUpView.findViewById(R.id.appTitle) as TextView
        title.text = dev.name
        val developers = popUpView.findViewById(R.id.developerNames) as TextView
        developers.text = getDevelopers(dev)
        val desc = popUpView.findViewById(R.id.devDescription) as TextView
        desc.text = dev.description

        // Get a reference for the custom view close button
        val closeButton = popUpView.findViewById(R.id.closeApp) as ImageButton

        // Set a click listener for the popup window close button
        closeButton.setOnClickListener {
            // Dismiss the popup window
            mPopupWindow.dismiss()
        }

        // Finally, show the popup window at the center location of root relative layout
        mPopupWindow.showAtLocation(mLinearLayout, Gravity.CENTER, 0, 0)
    }

    private fun getDevelopers(dev: Developer): String {

        val sb = StringBuilder()

        sb.append(dev.dev1)

        if (dev.dev2 != null)
            sb.append(", ").append(dev.dev2)

        if (dev.dev3 != null)
            sb.append(", ").append(dev.dev3)

        return sb.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        mAdapter?.cleanup()
    }
}
