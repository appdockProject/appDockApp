package com.appdockproject.appdock.Fragments

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import com.appdockproject.appdock.R
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookSdk
import com.facebook.GraphRequest
import com.facebook.GraphResponse
import com.facebook.HttpMethod
import com.facebook.appevents.AppEventsLogger
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.model.SharePhoto
import com.facebook.share.model.SharePhotoContent
import com.facebook.share.widget.ShareDialog
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

import org.json.JSONException
import org.json.JSONObject

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

import android.app.Activity.RESULT_OK
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import com.appdockproject.appdock.R.drawable.d
import com.appdockproject.appdock.R.drawable.e
import com.appdockproject.appdock.R.drawable.i
import com.appdockproject.appdock.R.id.bShareToFacebook
import com.appdockproject.appdock.R.id.bTakePhoto
import com.appdockproject.appdock.R.id.devBtn
import com.appdockproject.appdock.R.id.eduBtn
import com.appdockproject.appdock.R.string.facebookTitle
import com.facebook.FacebookSdk.getApplicationContext

class facebookFragment : Fragment() {
    private val TAG = "fbPhotoActivity"
    internal var callbackManager: CallbackManager? = null
    internal var shareDialog: ShareDialog? = null
    private var imPreview: ImageView? = null
    private var mCurrentPhotoPath = ""
    internal var photoTaken: Boolean = false
    private var accessToken: AccessToken? = null
    internal var bShareToFacebook: Button? = null
    internal var output: File? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val v = inflater!!.inflate(R.layout.activity_facebook, container, false)

        Log.i(TAG, "Starting " + activity.componentName.shortClassName)

        val headerTitle = v.findViewById(R.id.titleOfFragment) as TextView
        headerTitle.setText(R.string.facebookTitle)

        photoTaken = false

        imPreview = v.findViewById(R.id.imFacebookphoto) as ImageView

        //The following is needed to use facebook
        Log.i(TAG, "Setting up facebook")
        FacebookSdk.sdkInitialize(getApplicationContext())
        AppEventsLogger.activateApp(activity.application)
        callbackManager = CallbackManager.Factory.create()
        accessToken = AccessToken.getCurrentAccessToken()
        shareDialog = ShareDialog(this)

        Log.i(TAG, "Initializing permissions")

        val bTakePhoto = v.findViewById(R.id.bTakePhoto) as Button
        bTakePhoto.setOnClickListener {
            Log.i(TAG, "Checking for camera and storage permissions.")
            Dexter.withActivity(activity).withPermissions(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        Log.i(TAG, "Storage and camera permission granted")

                        takePicture()

                    } else if (report.isAnyPermissionPermanentlyDenied) {
                        Log.e(TAG, "Permissions permanently denied!")
                    } else {
                        Toast.makeText(activity,
                                "You need to activate permissions!", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest>, token: PermissionToken) {
                    token.continuePermissionRequest()
                }
            }).check()
        }
        bShareToFacebook = v.findViewById(R.id.bShareToFacebook) as Button
        bShareToFacebook?.setOnClickListener {
            Log.i(TAG, "Checking for internet permission.")
            Dexter.withActivity(activity).withPermissions(
                    Manifest.permission.INTERNET,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        Log.i(TAG, "Internet permission granted")

                        shareToFacebook()

                    } else if (report.isAnyPermissionPermanentlyDenied) {
                        Log.e(TAG, "Permissions permanently denied!")
                    } else {
                        Toast.makeText(activity,
                                "You need to activate permissions!", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest>, token: PermissionToken) {
                    token.continuePermissionRequest()
                }
            }).check()
        }

        imPreview!!.setOnClickListener { bTakePhoto.performClick() }

        return v
    }


    fun takePicture() {

        //access camera, tell where to save
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)

        //Start camera with front facing
        takePictureIntent.putExtra("android.intent.extras.CAMERA_FACING", 1)

        //name photograph
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val picName = "APPDOCK_$timeStamp.jpg"

        output = File(dir, picName)

        mCurrentPhotoPath = (output as File).absolutePath

        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(output))
        startActivityForResult(takePictureIntent, CONTENT_REQUEST)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == CONTENT_REQUEST) {
            if (resultCode == RESULT_OK) {

                Log.i(TAG, "Got photo in OnActivityResult")
                photoTaken = true
                setPic()
            }
        } else {
            Log.e(TAG, "Did not take photo. Requestcode: " + requestCode)
        }
    }

    private fun postToFace(image: ByteArray?) {

        val path: String
        if (image == null)
            path = "/feed"
        else
            path = "/photos"

        Log.i(TAG, "Starting to post to Page" + path)

        val obj: JSONObject

        try {
            obj = JSONObject("{\"message\":\"" + resources.getString(R.string.facebook_sample_text) + "\"}")
        } catch (e: JSONException) {
            e.printStackTrace()
            Log.e(TAG, e.toString())
            return
        }

        val request = GraphRequest.newPostRequest(
                accessToken,
                getString(R.string.facebook_page_id) + path,
                obj,
                GraphRequest.Callback { response ->
                    // Insert your code here
                    Log.i(TAG, "Got response: " + response)

                    var txt: String

                    if (response.error != null)
                        if (response.error.errorCode == -1) {
                            Toast.makeText(activity, R.string.facebook_no_internet, Toast.LENGTH_SHORT).show()
                            bShareToFacebook?.isEnabled = true
                            return@Callback
                        }

                    try {
                        if (response.connection.responseCode == 200)
                            txt = resources.getString(R.string.facebook_upload_success)
                        else
                            txt = resources.getString(R.string.facebook_upload_failure)
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Log.e(TAG, e.toString())
                        txt = "Something went wrong.."
                    }

                    Log.i(TAG, txt)
                    Toast.makeText(activity, txt, Toast.LENGTH_SHORT).show()

                    //Enable button after getting results
                    bShareToFacebook?.isEnabled = true
                })
        val parameters = Bundle()
        parameters.putString("access_token", resources.getString(R.string.facebook_access_token))

        if (image != null)
            parameters.putByteArray("picture", image)

        //Show uploading toast
        Toast.makeText(activity, getString(R.string.facebook_uploading), Toast.LENGTH_LONG).show()

        request.parameters = parameters
        request.executeAsync()
    }

    fun shareToFacebook() {

        if (!photoTaken) {
            Toast.makeText(activity,
                    resources.getString(R.string.facebook_no_photo),
                    Toast.LENGTH_SHORT).show()
            Log.i(TAG, "No photo taken")
            return
        }

        val builder = AlertDialog.Builder(activity)
        builder.setTitle(getString(R.string.facebook_upload_to_facebook))
        builder.setMessage(getString(R.string.facebook_upload_confirm_text))

        val positiveText = getString(R.string.yes)
        builder.setPositiveButton(positiveText,
                DialogInterface.OnClickListener { dialog, which ->
                    // positive button logic
                    val image: Bitmap
                    try {
                        image = BitmapFactory.decodeFile(mCurrentPhotoPath)
                    } catch (e: Error) {
                        Log.e(TAG, "Couldn't find image when sharing to facebook..")
                        return@OnClickListener
                    }

                    val baos = ByteArrayOutputStream()
                    image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                    val b = baos.toByteArray()

                    Log.i(TAG, "Image converted to byteArray")
                    postToFace(b)

                    //Disable button while getting results
                    bShareToFacebook?.isEnabled = false
                })

        val negativeText = getString(R.string.cancel)
        builder.setNegativeButton(negativeText
        ) { dialog, which ->
            // negative button logic
        }

        val dialog = builder.create()
        // display dialog
        dialog.show()

    }

    private fun setPic() {
        // Get the dimensions of the View
        val targetW = imPreview!!.width
        val targetH = imPreview!!.height

        // Get the dimensions of the bitmap
        val bmOptions = BitmapFactory.Options()
        bmOptions.inJustDecodeBounds = true
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions)
        val photoW = bmOptions.outWidth
        val photoH = bmOptions.outHeight

        // Determine how much to scale down the image
        val scaleFactor = Math.min(photoW / targetW, photoH / targetH)

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false
        bmOptions.inSampleSize = scaleFactor
        bmOptions.inPurgeable = true
        val bitmap: Bitmap
        try {
            bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions)
        } catch (e: Error) {
            println(e.toString())
            Log.e(TAG, "Couldn't find photo in setPhoto..")
            return
        }

        imPreview!!.setBackgroundResource(0)
        imPreview!!.setImageBitmap(bitmap)
        imPreview!!.postInvalidate()
        Log.i(TAG, "Image set from location: " + mCurrentPhotoPath)
    }

    companion object {

        private val CONTENT_REQUEST = 1337
    }

}// Required empty public constructor
