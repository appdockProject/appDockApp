package com.appdockproject.appdock;

/**
 * Created by jangerhard on 29-Oct-16.
 */

// You may want to be more specific in your imports

import android.content.Context;
import android.net.Uri;
import android.util.Base64;

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

    // Find your Account Sid and Auth Token at twilio.com/console
    private static String ACCOUNT_SID = null;
    private static String AUTH_TOKEN = null;
    private static String PHONE_NUMBER = null;

    private Context context;

    public TwilioSMS(Context context){

        ACCOUNT_SID = context.getResources().getString(R.string.twilio_account);
        AUTH_TOKEN = context.getResources().getString(R.string.twilio_auth);
        PHONE_NUMBER = context.getResources().getString(R.string.twilio_number);
        this.context = context;

    }

    public boolean sendSMS(final String number, final String text) {
        if (ACCOUNT_SID == null || AUTH_TOKEN == null || PHONE_NUMBER == null)
            return false;

        final String base64EncodedCredentials = "Basic "
                + Base64.encodeToString(
                (ACCOUNT_SID + ":" + AUTH_TOKEN).getBytes(),
                Base64.NO_WRAP);

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST,"https://api.twilio.com/2010-04-01/Accounts/"+ ACCOUNT_SID +"/SMS/Messages", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Sent SMS!");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("From", PHONE_NUMBER);
                params.put("To", number);
                params.put("Body", text);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Authorization", base64EncodedCredentials);
                return params;
            }
        };
        queue.add(sr);

        return true;
    }

    // TODO: Improve phone-number verification
    public boolean verifyNumber(String number){

        // Senegal phone number: +221 xxx xx

        if (number.contains("+221"))
            return number.length() == 11;

        return number.length() == 7;

    }
}