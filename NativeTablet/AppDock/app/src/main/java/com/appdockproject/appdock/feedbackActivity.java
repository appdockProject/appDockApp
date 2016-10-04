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

    RadioButton selectedAge, selectedProfession, selectedQuestion, selectedTime;
    RadioGroup age, profession, question, time;
    Button submit;
    int selectedId;
    boolean send;

    String ageString, professionString, questionString, timeString;




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

        age = (RadioGroup) findViewById(R.id.typeGroup);
        profession = (RadioGroup)findViewById(R.id.profession);
        question = (RadioGroup)findViewById(R.id.question);
        time = (RadioGroup)findViewById(R.id.time);



//      Connect and initialize firebase
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //age and make sure value is not null (in this case -1)
                if (age.getCheckedRadioButtonId() == -1) {
                    selectedId = age.getCheckedRadioButtonId();
                    selectedAge = (RadioButton) findViewById(selectedId);
                    ageString = selectedAge.getText().toString();
                }

                //profession
                if (profession.getCheckedRadioButtonId() != -1) {

                    selectedId = profession.getCheckedRadioButtonId();
                    selectedProfession = (RadioButton) findViewById(selectedId);
                    professionString = selectedProfession.getText().toString();
                }

                //question
                if (question.getCheckedRadioButtonId() != -1) {
                    selectedId = question.getCheckedRadioButtonId();
                    selectedQuestion = (RadioButton) findViewById(selectedId);
                    questionString = selectedQuestion.getText().toString();
                }

                //time
                if (time.getCheckedRadioButtonId() != -1) {
                    selectedId = time.getCheckedRadioButtonId();
                    selectedTime = (RadioButton) findViewById(selectedId);
                    timeString = selectedTime.getText().toString();
                }

                if ((age.getCheckedRadioButtonId() != -1) && (profession.getCheckedRadioButtonId() != -1) && (question.getCheckedRadioButtonId() != -1) && (time.getCheckedRadioButtonId() != -1)) {
                    addData(ageString, professionString, questionString, timeString);

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


    private void addData(String age, String profession, String question, String time){
        Answer a = new Answer();
        a.setAge(age);
        a.setProfession(profession);
        a.setQuestion(question);
        a.setTime(time);

        myRef.child("Answer").push().setValue(a);
    }
}





