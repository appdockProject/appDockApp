package com.appdockproject.appdock;

/**
 * Created by jangerhard on 29-Oct-16.
 */

// You may want to be more specific in your imports

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission_group.SMS;

public class TwilioSMS {

    private Context context;
    private final String TAG = "Twilio";

    public TwilioSMS(Context context) {

        this.context = context;

    }

    public boolean sendSMS(final String number, final String text) {

        if (!verifyNumber(number)) {
            Log.e(TAG, "Phone number invalid format");
            return false;
        } else if (!isConnectedToInternet()) {
            Log.e(TAG, "Not Connected to internet");
            return false;
        }

        String ACCOUNT_SID = context.getResources().getString(R.string.twilio_account);
        String AUTH_TOKEN = context.getResources().getString(R.string.twilio_auth);
        final String PHONE_NUMBER = context.getResources().getString(R.string.twilio_number);

        Log.i(TAG, "SID: " + ACCOUNT_SID);
        Log.i(TAG, "Auth: " + AUTH_TOKEN);
        Log.i(TAG, "Number: " + PHONE_NUMBER);
        Log.i(TAG, "Sending to: " + number);

        final String base64EncodedCredentials = "Basic "
                + Base64.encodeToString(
                (ACCOUNT_SID + ":" + AUTH_TOKEN).getBytes(),
                Base64.NO_WRAP);

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST, "https://api.twilio.com/2010-04-01/Accounts/" + ACCOUNT_SID + "/SMS/Messages", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "Sent SMS!");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getLocalizedMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("From", PHONE_NUMBER);
                params.put("To", number);
                params.put("Body", text);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", base64EncodedCredentials);
                return params;
            }
        };
        queue.add(sr);

        return true;
    }

    // TODO: Improve phone-number verification
    public boolean verifyNumber(String number) {

        // Senegal phone number: +221 xxx xx

        if (number.contains("+221"))
            return number.length() == 11;
        else if (number.contains("+1"))
            return number.length() == 12;

        return number.length() == 7;

    }

    public boolean isConnectedToInternet(){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}