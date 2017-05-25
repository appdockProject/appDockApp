package com.appdockproject.appdock

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.View
import android.widget.Button
import com.appdockproject.appdock.Fragments.appPageFragment
import com.appdockproject.appdock.Fragments.devFragment
import com.appdockproject.appdock.Fragments.eduFragment
import com.appdockproject.appdock.Fragments.facebookFragment

class MainActivity : FragmentActivity() {

    val TAG = "fragments"

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        hideSystemUI()

        val devBtn = findViewById(R.id.devBtn) as Button
        val eduBtn = findViewById(R.id.eduBtn) as Button
        val appBtn = findViewById(R.id.appBtn) as Button
        val facebookBtn = findViewById(R.id.fbBtn) as Button

        devBtn.setOnClickListener { changeFragment(devFragment()) }

        eduBtn.setOnClickListener { changeFragment(eduFragment()) }

        appBtn.setOnClickListener { changeFragment(appPageFragment()) }

        facebookBtn.setOnClickListener { changeFragment(facebookFragment()) }

        /*
        cmntBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.i(TAG, "Pressed Cmnt");
                resetButtons();
                cmntBtn.setPressed(true);
                changeFragment(new feedbackFragment());
            }
        });
        */

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) return

            // Create a new Fragment to be placed in the activity layout
            val firstFragment = appPageFragment()

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.arguments = intent.extras

            // Add the fragment to the 'fragment_container' FrameLayout
            supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit()
        }
    }

    fun changeFragment(fragment: Fragment) {

        val transaction = supportFragmentManager.beginTransaction()

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)

        // Commit the transaction
        transaction.commit()
    }

    // This snippet hides the system bars.
    private fun hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        Log.i(TAG, "Hiding UI")
        window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or // hide status bar
                        View.SYSTEM_UI_FLAG_IMMERSIVE
    }

    // Needs this to remove statusbar and navigation layout after focus of activity regains focus
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        if (hasFocus) hideSystemUI()

    }

}
