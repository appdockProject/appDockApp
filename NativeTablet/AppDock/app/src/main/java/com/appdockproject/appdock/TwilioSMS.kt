package com.appdockproject.appdock

/**
 * Created by jangerhard on 29-Oct-16.
 */

// You may want to be more specific in your imports

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.util.Base64
import android.util.Log
import android.widget.Toast

import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import java.util.HashMap

import android.Manifest.permission_group.SMS

class TwilioSMS(private val context: Context) {
    private val TAG = "Twilio"

    fun sendSMS(number: String, text: String): Boolean {

        if (!isConnectedToInternet) {
            Log.e(TAG, "Not Connected to internet")
            Toast.makeText(context, context.getString(R.string.facebook_no_internet), Toast.LENGTH_SHORT).show()
            return false
        } else if (!verifyNumber(formatPhoneNumber(number))) {
            Log.e(TAG, "Phone number invalid format")
            Toast.makeText(context, context.getString(R.string.twilio_Invalid_number), Toast.LENGTH_SHORT).show()
            return false
        }

        val ACCOUNT_SID = context.resources.getString(R.string.twilio_account)
        val AUTH_TOKEN = context.resources.getString(R.string.twilio_auth)
        val PHONE_NUMBER = context.resources.getString(R.string.twilio_number)
        val NUMBER_TO = formatPhoneNumber(number)

        Log.i(TAG, "SID: " + ACCOUNT_SID)
        Log.i(TAG, "Auth: " + AUTH_TOKEN)
        Log.i(TAG, "Number: " + PHONE_NUMBER)
        Log.i(TAG, "Sending to: " + number)

        val base64EncodedCredentials = "Basic " + Base64.encodeToString(
                (ACCOUNT_SID + ":" + AUTH_TOKEN).toByteArray(),
                Base64.NO_WRAP)

        val queue = Volley.newRequestQueue(context)
        val sr = object : StringRequest(Request.Method.POST, "https://api.twilio.com/2010-04-01/Accounts/$ACCOUNT_SID/SMS/Messages", Response.Listener<String> {
            Log.i(TAG, "Sent SMS!")
            Toast.makeText(context, context.getString(R.string.twilio_SMS_Sent) + " " + number, Toast.LENGTH_SHORT).show()
        }, Response.ErrorListener { error ->
            Log.e(TAG, "Error code: " + error.networkResponse.statusCode)
            Toast.makeText(context, context.getString(R.string.twilio_SMS_Error) + " " + number, Toast.LENGTH_SHORT).show()
        }) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("From", PHONE_NUMBER)
                params.put("To", NUMBER_TO)
                params.put("Body", text)

                return params
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("Authorization", base64EncodedCredentials)
                return params
            }
        }
        queue.add(sr)

        Toast.makeText(context, context.getString(R.string.twilio_SMS_Sending) + " " + number, Toast.LENGTH_LONG).show()
        return true
    }

    // TODO: Improve phone-number verification
    private fun verifyNumber(number: String): Boolean {

        // Senegal phone number: +221 xx xxx xxxx
        // US number: +1xxxxxxxxxx
        // Formated number: +221xxxxxxxxx OR xxxxxxxxx

        if (number.contains("+221"))
            return number.length == 13
        else if (number.contains("+1"))
            return number.length == 12

        return number.length == 9
    }

    private fun formatPhoneNumber(number: String): String {

        return number.replace("\\s+".toRegex(), "")
    }

    internal val isConnectedToInternet: Boolean
        get() {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }
}