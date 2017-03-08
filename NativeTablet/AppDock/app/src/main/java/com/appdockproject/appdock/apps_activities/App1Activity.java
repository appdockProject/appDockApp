package com.appdockproject.appdock.apps_activities;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.appdockproject.appdock.R;
import com.appdockproject.appdock.TwilioSMS;
import com.appdockproject.appdock.appPage;
import com.appdockproject.appdock.devActivity;
import com.appdockproject.appdock.eduActivity;
import com.appdockproject.appdock.facebookActivity;
import com.appdockproject.appdock.feedbackActivity;

import static com.appdockproject.appdock.R.id.appBtn;
import static com.appdockproject.appdock.R.id.devBtn;

public class App1Activity extends AppCompatActivity {

    Button smsBtn;
    EditText phoneNumInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app1);

        //Activity Elements
        smsBtn = (Button) findViewById(R.id.getAppPhoneBtn);
        phoneNumInput = (EditText) findViewById(R.id.smsNumber);

        //get number by Text
        smsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //to send the text

                String userSMSNum = phoneNumInput.getText().toString(); //user input number as a string

                TwilioSMS ts = new TwilioSMS(App1Activity.this);

                if (ts.sendSMS(userSMSNum, getString(R.string.app1SMSLink)))
                    phoneNumInput.getText().clear();

            }
        });

    }
}
