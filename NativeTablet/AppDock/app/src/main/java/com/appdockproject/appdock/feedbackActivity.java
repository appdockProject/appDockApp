package com.appdockproject.appdock;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.appdockproject.appdock.Data.Answer;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class feedbackActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;

    RadioButton selectedAge, selectedGender, selectedEdu, selectedProfession, selectedPhone, selectedTime, selectedRating, selectedUse;
    RadioGroup ageGroup, genderGroup, eduGroup, professionGroup, phoneGroup, timeGroup, ratingGroup, useGroup;
    Button submit;
    int selectedId;
    boolean send;

    String ageString, genderString, eduString, professionString, phoneString, timeString, ratingString, useString;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int UI_OPTIONS = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            getWindow().getDecorView().setSystemUiVisibility(UI_OPTIONS);
        }

        Button devBtn = (Button) findViewById(R.id.devBtn);
        Button eduBtn = (Button) findViewById(R.id.eduBtn);
        Button homeBtn = (Button) findViewById(R.id.appBtn);
        Button fbBtn = (Button) findViewById(R.id.fbBtn);

        devBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(feedbackActivity.this, devActivity.class);
                startActivity(intent);
            }
        });


        eduBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(feedbackActivity.this, eduActivity.class);
                startActivity(intent);
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(feedbackActivity.this, appPage.class);
                startActivity(intent);
            }
        });

        fbBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(feedbackActivity.this, facebookActivity.class);
                startActivity(intent);
            }
        });


        //Submit button
        submit = (Button) findViewById(R.id.submit);

        ageGroup = (RadioGroup) findViewById(R.id.ageGroup);
        genderGroup = (RadioGroup) findViewById(R.id.genderGroup);
        eduGroup = (RadioGroup) findViewById(R.id.eduGroup);
        professionGroup = (RadioGroup)findViewById(R.id.professionGroup);
        phoneGroup = (RadioGroup)findViewById(R.id.phoneGroup);
        timeGroup = (RadioGroup)findViewById(R.id.timeGroup);
        ratingGroup = (RadioGroup) findViewById(R.id.ratingGroup);
        useGroup = (RadioGroup) findViewById(R.id.useGroup);



//      Connect and initialize firebase
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //age and make sure value is not null (in this case -1)
                if (ageGroup.getCheckedRadioButtonId() != -1) {
                    selectedId = ageGroup.getCheckedRadioButtonId();
                    selectedAge = (RadioButton) findViewById(selectedId);
                    ageString = selectedAge.getText().toString();
                }

                //gender
                if (genderGroup.getCheckedRadioButtonId() != -1) {
                    selectedId = genderGroup.getCheckedRadioButtonId();
                    selectedGender = (RadioButton) findViewById(selectedId);
                    genderString = selectedGender.getText().toString();
                }

                //edu level
                if (eduGroup.getCheckedRadioButtonId() != -1) {
                    selectedId = eduGroup.getCheckedRadioButtonId();
                    selectedEdu = (RadioButton) findViewById(selectedId);
                    eduString = selectedEdu.getText().toString();
                }

                //occupation
                if (professionGroup.getCheckedRadioButtonId() != -1) {

                    selectedId = professionGroup.getCheckedRadioButtonId();
                    selectedProfession = (RadioButton) findViewById(selectedId);
                    professionString = selectedProfession.getText().toString();
                }

                //phone type
                if (phoneGroup.getCheckedRadioButtonId() != -1) {
                    selectedId = phoneGroup.getCheckedRadioButtonId();
                    selectedPhone = (RadioButton) findViewById(selectedId);
                    phoneString = selectedPhone.getText().toString();
                }

                //time spent
                if (timeGroup.getCheckedRadioButtonId() != -1) {
                    selectedId = timeGroup.getCheckedRadioButtonId();
                    selectedTime = (RadioButton) findViewById(selectedId);
                    timeString = selectedTime.getText().toString();
                }

                //rating
                if (ratingGroup.getCheckedRadioButtonId() != -1) {
                    selectedId = ratingGroup.getCheckedRadioButtonId();
                    selectedRating = (RadioButton) findViewById(selectedId);
                    ratingString = selectedRating.getText().toString();
                }

                //use of appdock
                if (useGroup.getCheckedRadioButtonId() != -1) {
                    selectedId = useGroup.getCheckedRadioButtonId();
                    selectedUse = (RadioButton) findViewById(selectedId);
                    useString = selectedUse.getText().toString();
                }

                if ((ageGroup.getCheckedRadioButtonId() != -1) && (genderGroup.getCheckedRadioButtonId() != -1) && (eduGroup.getCheckedRadioButtonId() != -1) && (professionGroup.getCheckedRadioButtonId() != -1) && (phoneGroup.getCheckedRadioButtonId() != -1) && (timeGroup.getCheckedRadioButtonId() != -1) && (ratingGroup.getCheckedRadioButtonId() != -1) && (useGroup.getCheckedRadioButtonId() != -1)) {
                    addData(ageString, genderString, eduString, professionString, phoneString, timeString, ratingString, useString);

                    //Send user to facebook page to take a picture?
                    Intent intent = new Intent(feedbackActivity.this, facebookActivity.class);
                    startActivity(intent);
                }
                else{
                    //Add toast here?
                    Context context = getApplicationContext();
                    CharSequence text = "Please fill out all information";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });}


    private void addData(String age, String gender, String edu, String profession, String phone, String time, String rating, String use){
        Answer a = new Answer();
        a.setAge(age);
        a.setGender(gender);
        a.setEdu(edu);
        a.setProfession(profession);
        a.setPhone(phone);
        a.setTime(time);
        a.setRating(rating);
        a.setUse(use);

        myRef.child("Answer").push().setValue(a);
    }
}





