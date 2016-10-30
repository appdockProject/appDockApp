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
import android.widget.Toast;

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

        if (!isConnectedToInternet()) {
            Log.e(TAG, "Not Connected to internet");
            Toast.makeText(context, context.getString(R.string.facebook_no_internet), Toast.LENGTH_SHORT).show();
            return false;
        } else if (!verifyNumber(formatPhoneNumber(number))) {
            Log.e(TAG, "Phone number invalid format");
            Toast.makeText(context, context.getString(R.string.twilio_Invalid_number), Toast.LENGTH_SHORT).show();
            return false;
        }

        String ACCOUNT_SID = context.getResources().getString(R.string.twilio_account);
        String AUTH_TOKEN = context.getResources().getString(R.string.twilio_auth);
        final String PHONE_NUMBER = context.getResources().getString(R.string.twilio_number);
        final String NUMBER_TO = formatPhoneNumber(number);

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
                Toast.makeText(context, context.getString(R.string.twilio_SMS_Sent) + " " + number, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error code: " + error.networkResponse.statusCode);
                Toast.makeText(context, context.getString(R.string.twilio_SMS_Error) + " " + number, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("From", PHONE_NUMBER);
                params.put("To", NUMBER_TO);
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

        Toast.makeText(context, context.getString(R.string.twilio_SMS_Sending) + " " + number, Toast.LENGTH_SHORT).show();

        return true;
    }

    // TODO: Improve phone-number verification
    boolean verifyNumber(String number) {

        // Senegal phone number: +221 xxx xxxx

        if (number.contains("+221"))
            return number.length() == 13;
        else if (number.contains("+1"))
            return number.length() == 12;

        return number.length() == 9;

    }

    private String formatPhoneNumber(String number){

        if (number.contains("+1"))
            return number.trim();
        else if (!number.contains("+211"))
            return "+221" + number.trim();

        return number.trim();
    }

    boolean isConnectedToInternet(){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}