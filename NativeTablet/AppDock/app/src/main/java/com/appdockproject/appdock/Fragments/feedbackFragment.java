package com.appdockproject.appdock.Fragments;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.appdockproject.appdock.*;
import com.appdockproject.appdock.Data.Answer;
import com.appdockproject.appdock.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class feedbackFragment extends Fragment {
    FirebaseDatabase database;
    DatabaseReference myRef;

    String permission = "android.permission.ACCESS_FINE_LOCATION";

    RadioButton selectedAge, selectedGender, selectedEdu, selectedProfession, selectedPhone, selectedTime, selectedRating, selectedUse;
    RadioGroup ageGroup, genderGroup, eduGroup, professionGroup, professionGroup2, phoneGroup, timeGroup, ratingGroup, useGroup;
    Button submit;
    int selectedId;
    boolean send;

    View v;
    String TAG = "feedback";

    private RadioGroup.OnCheckedChangeListener listener1, listener2;

    String ageString, genderString, eduString, professionString, phoneString, timeString, ratingString, useString;

    private double locationLatitude = 0.0;
    private double locationLongitude = 0.0;
    private String gpsData = "";
    private String latit, longit;

    public feedbackFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(com.appdockproject.appdock.R.layout.fragment_survey, container, false);

        initializeUI(v);

        return v;
    }

    private void initializeUI(View v) {

        this.v = v;

        //Submit button
        submit = (Button) v.findViewById(R.id.submit);

        ageGroup = (RadioGroup) v.findViewById(R.id.ageGroup);
        genderGroup = (RadioGroup) v.findViewById(R.id.genderGroup);
        eduGroup = (RadioGroup) v.findViewById(R.id.eduGroup);
        professionGroup = (RadioGroup) v.findViewById(R.id.professionGroup);
        professionGroup2 = (RadioGroup) v.findViewById(R.id.professionGroup2);
        phoneGroup = (RadioGroup) v.findViewById(R.id.phoneGroup);
        timeGroup = (RadioGroup) v.findViewById(R.id.timeGroup);
        ratingGroup = (RadioGroup) v.findViewById(R.id.ratingGroup);
        useGroup = (RadioGroup) v.findViewById(R.id.useGroup);

        listener1 = new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1) {
                    professionGroup2.setOnCheckedChangeListener(null); // remove the listener before clearing so we don't throw that stackoverflow exception(like Vladimir Volodin pointed out)
                    professionGroup2.clearCheck(); // clear the second RadioGroup!
                    professionGroup2.setOnCheckedChangeListener(listener2); //reset the listener

                }
            }
        };

        listener2 = new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1) {
                    professionGroup.setOnCheckedChangeListener(null);
                    professionGroup.clearCheck();
                    professionGroup.setOnCheckedChangeListener(listener1);
                }
            }
        };

        professionGroup.clearCheck(); // this is so we can start fresh, with no selection on both RadioGroups
        professionGroup2.clearCheck();
        professionGroup.setOnCheckedChangeListener(listener1);
        professionGroup2.setOnCheckedChangeListener(listener2);


//      Connect and initialize firebase
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Dexter.withActivity(getActivity()).withPermissions (
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ).withListener( new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            Log.i(TAG, "Internet permission granted");

                            getLocationData();
                            submitData();

                        } else if (report.isAnyPermissionPermanentlyDenied()) {
                            Log.e(TAG, "Permissions permanently denied!");
                        } else {
                            Toast.makeText(getActivity(),
                                    "You need to activate permissions!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

            }
        });
    }

    private void submitData() {
        //age and make sure value is not null (in this case -1)
        if (ageGroup.getCheckedRadioButtonId() != -1) {
            selectedId = ageGroup.getCheckedRadioButtonId();
            selectedAge = (RadioButton) v.findViewById(selectedId);
            ageString = selectedAge.getText().toString();
        }

        //gender
        if (genderGroup.getCheckedRadioButtonId() != -1) {
            selectedId = genderGroup.getCheckedRadioButtonId();
            selectedGender = (RadioButton) v.findViewById(selectedId);
            genderString = selectedGender.getText().toString();
        }

        //edu level
        if (eduGroup.getCheckedRadioButtonId() != -1) {
            selectedId = eduGroup.getCheckedRadioButtonId();
            selectedEdu = (RadioButton) v.findViewById(selectedId);
            eduString = selectedEdu.getText().toString();
        }

        //occupation
        if (professionGroup.getCheckedRadioButtonId() != -1) {

            selectedId = professionGroup.getCheckedRadioButtonId();
            selectedProfession = (RadioButton) v.findViewById(selectedId);
            professionString = selectedProfession.getText().toString();
        }

        //2nd occupation group
        if (professionGroup2.getCheckedRadioButtonId() != -1) {

            selectedId = professionGroup2.getCheckedRadioButtonId();
            selectedProfession = (RadioButton) v.findViewById(selectedId);
            professionString = selectedProfession.getText().toString();
        }

        //phone type
        if (phoneGroup.getCheckedRadioButtonId() != -1) {
            selectedId = phoneGroup.getCheckedRadioButtonId();
            selectedPhone = (RadioButton) v.findViewById(selectedId);
            phoneString = selectedPhone.getText().toString();
        }

        //time spent
        if (timeGroup.getCheckedRadioButtonId() != -1) {
            selectedId = timeGroup.getCheckedRadioButtonId();
            selectedTime = (RadioButton) v.findViewById(selectedId);
            timeString = selectedTime.getText().toString();
        }

        //rating
        if (ratingGroup.getCheckedRadioButtonId() != -1) {
            selectedId = ratingGroup.getCheckedRadioButtonId();
            selectedRating = (RadioButton) v.findViewById(selectedId);
            ratingString = selectedRating.getText().toString();
        }

        //use of appdock
        if (useGroup.getCheckedRadioButtonId() != -1) {
            selectedId = useGroup.getCheckedRadioButtonId();
            selectedUse = (RadioButton) v.findViewById(selectedId);
            useString = selectedUse.getText().toString();
        }

        if ((ageGroup.getCheckedRadioButtonId() != -1) && (genderGroup.getCheckedRadioButtonId() != -1) && (eduGroup.getCheckedRadioButtonId() != -1) && (professionGroup.getCheckedRadioButtonId() != -1 || professionGroup2.getCheckedRadioButtonId() != -1) && (phoneGroup.getCheckedRadioButtonId() != -1) && (timeGroup.getCheckedRadioButtonId() != -1) && (ratingGroup.getCheckedRadioButtonId() != -1) && (useGroup.getCheckedRadioButtonId() != -1)) {
            addData(ageString, genderString, eduString, professionString, phoneString, timeString, ratingString, useString, latit, longit);

            Context context = getApplicationContext();
            CharSequence text = "Merci!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            ageGroup.clearCheck();
            genderGroup.clearCheck();
            eduGroup.clearCheck();
            professionGroup.clearCheck();
            professionGroup2.clearCheck();
            phoneGroup.clearCheck();
            timeGroup.clearCheck();
            ratingGroup.clearCheck();
            useGroup.clearCheck();


        } else {
            //Add toast here?
            Context context = getApplicationContext();
            CharSequence text = "Veuillez remplir toutes les informations";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    private void getLocationData() {
        LocationManager locationManager = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                locationLatitude = location.getLatitude();
                locationLongitude = location.getLongitude();
                latit = Double.toString(locationLatitude);
                longit = Double.toString(locationLongitude);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, locationListener);
    }

    private void addData(String age, String gender, String edu, String profession, String phone, String time, String rating, String use, String lat, String longit) {
        Answer a = new Answer();
        a.setAge(age);
        a.setGender(gender);
        a.setEdu(edu);
        a.setProfession(profession);
        a.setPhone(phone);
        a.setTime(time);
        a.setRating(rating);
        a.setUse(use);
        a.setLocation(lat, longit);

        myRef.child("Answer").push().setValue(a);
    }

}





