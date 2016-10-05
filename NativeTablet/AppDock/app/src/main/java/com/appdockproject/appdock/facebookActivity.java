package com.appdockproject.appdock;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class facebookActivity extends AppCompatActivity {

    private static final int CONTENT_REQUEST = 1337;
    private final String TAG = "fbPhotoActivity";
    private File output = null;
    private ImageView imPreview;
    private String mCurrentPhotoPath = "";

    CallbackManager callbackManager;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);

        Log.i(TAG, "Starting " + this.getComponentName().getShortClassName());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int UI_OPTIONS = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            getWindow().getDecorView().setSystemUiVisibility(UI_OPTIONS);
        }

        Button devBtn = (Button) findViewById(R.id.devBtn);
        Button eduBtn = (Button) findViewById(R.id.eduBtn);
        Button cmntBtn = (Button) findViewById(R.id.comBtn);
        Button homeBtn = (Button) findViewById(R.id.appBtn);

        imPreview = (ImageView) findViewById(R.id.imFacebookphoto);

        devBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(facebookActivity.this, devActivity.class);
                startActivity(intent);
            }
        });


        eduBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(facebookActivity.this, eduActivity.class);
                startActivity(intent);
            }
        });

        cmntBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(facebookActivity.this, feedbackActivity.class);
                startActivity(intent);
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(facebookActivity.this, appPage.class);
                startActivity(intent);
            }
        });


        //The following is needed to use facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getApplication());
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

//        //Used to get Permissions
//        Dexter.initialize(this);
//        Dexter.checkPermissions(new MultiplePermissionsListener() {
//            @Override
//            public void onPermissionsChecked(MultiplePermissionsReport report) {/* ... */}
//
//            @Override
//            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
//        }, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);

    }

    public void takePicture(View view) throws IOException {
        //access camera, tell where to save
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        //name photograph
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String picName = "APPDOCK_" + timeStamp + ".jpg";

        output = new File(dir, picName);

        mCurrentPhotoPath = output.getAbsolutePath();

        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(output));
        startActivityForResult(takePictureIntent, CONTENT_REQUEST);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CONTENT_REQUEST) {
            if (resultCode == RESULT_OK) {

                Log.i(TAG, "Got photo in OnActivityResult");
                setPic();
            }
        }
    }

    public void shareToFacebook(View view) throws IOException {

        if (mCurrentPhotoPath == "") {
            Toast.makeText(this, "No photo taken!", Toast.LENGTH_SHORT).show();
            return;
        }

        Bitmap image;
        try {
            image = BitmapFactory.decodeFile(mCurrentPhotoPath);
        } catch (Error e) {
            Log.e(TAG, "Couldn't find image when sharing to facebook..");
            return;
        }

        Log.i(TAG, "Bitmap set");
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
                .setCaption("Loving the Appdock from Pace University!")
                .build();
        Log.i(TAG, "Sharephoto set");
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();

        Log.i(TAG, "Showing sharedialog");
        shareDialog.show(content);

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
        imPreview.setImageBitmap(bitmap);
        imPreview.postInvalidate();
        Log.i(TAG, "Image set from location: " + mCurrentPhotoPath);
    }
}
