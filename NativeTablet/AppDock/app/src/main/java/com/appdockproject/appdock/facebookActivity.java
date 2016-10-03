package com.appdockproject.appdock;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import com.facebook.FacebookSdk;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class facebookActivity extends AppCompatActivity {

    private static final int CONTENT_REQUEST=1337;
    private File output = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int UI_OPTIONS = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            getWindow().getDecorView().setSystemUiVisibility(UI_OPTIONS);

            Button devBtn = (Button) findViewById(R.id.devBtn);
            Button eduBtn = (Button) findViewById(R.id.eduBtn);
            Button cmntBtn = (Button) findViewById(R.id.comBtn);
            Button homeBtn = (Button) findViewById(R.id.appBtn);
            

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
        }
    }

    public void takePicture(View view) throws IOException {
        //access camera, tell where to save
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);

        //name photograph
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String picName = "APPDOCK_" + timeStamp + ".jpg";

        output = new File(dir, picName);

        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(output));
        startActivityForResult(takePictureIntent, CONTENT_REQUEST);

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CONTENT_REQUEST) {
            if (resultCode == RESULT_OK) {
                Intent i = new Intent(Intent.ACTION_VIEW);

                i.setDataAndType(Uri.fromFile(output), "image/jpeg");
                startActivity(i);
                finish();
            }
        }
    }
}
