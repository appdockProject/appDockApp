package com.appdockproject.appdock.Fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.appdockproject.appdock.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.appdockproject.appdock.R.drawable.d;
import static com.appdockproject.appdock.R.drawable.e;
import static com.appdockproject.appdock.R.drawable.i;
import static com.appdockproject.appdock.R.id.bShareToFacebook;
import static com.appdockproject.appdock.R.id.bTakePhoto;
import static com.appdockproject.appdock.R.id.devBtn;
import static com.appdockproject.appdock.R.id.eduBtn;
import static com.facebook.FacebookSdk.getApplicationContext;

public class facebookFragment extends Fragment {

    private static final int CONTENT_REQUEST = 1337;
    private final String TAG = "fbPhotoActivity";
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    private ImageView imPreview;
    private String mCurrentPhotoPath = "";
    boolean photoTaken;
    private AccessToken accessToken;
    Button bShareToFacebook;
    File output;

    public facebookFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.activity_facebook, container, false);

        Log.i(TAG, "Starting " + getActivity().getComponentName().getShortClassName());

        photoTaken = false;

        imPreview = (ImageView) v.findViewById(R.id.imFacebookphoto);

        //The following is needed to use facebook
        Log.i(TAG, "Setting up facebook");
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getActivity().getApplication());
        callbackManager = CallbackManager.Factory.create();
        accessToken = AccessToken.getCurrentAccessToken();
        shareDialog = new ShareDialog(this);

        Log.i(TAG, "Initializing permissions");
        Dexter.initialize(getActivity()); //Used to get Permissions

        final Button bTakePhoto = (Button) v.findViewById(R.id.bTakePhoto);
        bTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Checking for camera and storage permissions.");
                Dexter.checkPermissions(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            Log.i(TAG, "Storage and camera permission granted");

                            takePicture();

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
                }, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        });
        bShareToFacebook = (Button) v.findViewById(R.id.bShareToFacebook);
        bShareToFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i(TAG, "Checking for internet permission.");
                Dexter.checkPermissions(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            Log.i(TAG, "Internet permission granted");

                            shareToFacebook();

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
                }, Manifest.permission.INTERNET, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        });

        imPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bTakePhoto.performClick();
            }
        });

        return v;
    }


    public void takePicture() {

        //access camera, tell where to save
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        //Start camera with front facing
        takePictureIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);

        //name photograph
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String picName = "APPDOCK_" + timeStamp + ".jpg";

        output = new File(dir, picName);

        mCurrentPhotoPath = output.getAbsolutePath();

        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(output));
        startActivityForResult(takePictureIntent, CONTENT_REQUEST);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CONTENT_REQUEST) {
            if (resultCode == RESULT_OK) {

                Log.i(TAG, "Got photo in OnActivityResult");
                photoTaken = true;
                setPic();
            }
        } else{
            Log.e(TAG, "Did not take photo. Requestcode: " + requestCode);
        }
    }
    
    private void postToFace(byte[] image) {

        String path;
        if (image == null)
            path = "/feed";
        else
            path = "/photos";

        Log.i(TAG, "Starting to post to Page" + path);

        JSONObject obj;

        try {
            obj = new JSONObject("{\"message\":\"" + getResources().getString(R.string.facebook_sample_text) + "\"}");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
            return;
        }

        GraphRequest request = GraphRequest.newPostRequest(
                accessToken,
                getString(R.string.facebook_page_id) + path,
                obj,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        // Insert your code here
                        Log.i(TAG, "Got response: " + response);

                        String txt;

                        if (response.getError() != null )
                            if (response.getError().getErrorCode() == -1){
                                Toast.makeText(getActivity(), R.string.facebook_no_internet, Toast.LENGTH_SHORT).show();
                                bShareToFacebook.setEnabled(true);
                                return;
                        }

                        try {
                            if (response.getConnection().getResponseCode() == 200)
                                txt = getResources().getString(R.string.facebook_upload_success);
                            else
                                txt = getResources().getString(R.string.facebook_upload_failure);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.e(TAG, e.toString());
                            txt = "Something went wrong..";
                        }

                        Log.i(TAG, txt);
                        Toast.makeText(getActivity(), txt, Toast.LENGTH_SHORT).show();

                        //Enable button after getting results
                        bShareToFacebook.setEnabled(true);

                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("access_token", getResources().getString(R.string.facebook_access_token));

        if (image != null)
            parameters.putByteArray("picture", image);

        //Show uploading toast
        Toast.makeText(getActivity(), getString(R.string.facebook_uploading), Toast.LENGTH_LONG).show();

        request.setParameters(parameters);
        request.executeAsync();
    }

    public void shareToFacebook() {

        if (!photoTaken) {
            Toast.makeText(getActivity(),
                    getResources().getString(R.string.facebook_no_photo),
                    Toast.LENGTH_SHORT).show();
            Log.i(TAG, "No photo taken");
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.facebook_upload_to_facebook));
        builder.setMessage(getString(R.string.facebook_upload_confirm_text));

        String positiveText = getString(R.string.yes);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                        Bitmap image;
                        try {
                            image = BitmapFactory.decodeFile(mCurrentPhotoPath);
                        } catch (Error e) {
                            Log.e(TAG, "Couldn't find image when sharing to facebook..");
                            return;
                        }

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] b = baos.toByteArray();

                        Log.i(TAG, "Image converted to byteArray");
                        postToFace(b);

                        //Disable button while getting results
                        bShareToFacebook.setEnabled(false);
                    }
                });

        String negativeText = getString(R.string.cancel);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();

    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = imPreview.getWidth();
        int targetH = imPreview.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        } catch (Error e) {
            System.out.println(e.toString());
            Log.e(TAG, "Couldn't find photo in setPhoto..");
            return;
        }
        imPreview.setBackgroundResource(0);
        imPreview.setImageBitmap(bitmap);
        imPreview.postInvalidate();
        Log.i(TAG, "Image set from location: " + mCurrentPhotoPath);
    }

}
