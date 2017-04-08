package keepmeout.travel.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import keepmeout.travel.R;


/**
 * Created by Dhananjay Mohnot on 4/13/2016.
 */
public class AppUtil {
    private static final String LOG_TAG = AppUtil.class.getSimpleName();

    public static void showToast(Context context, String strToastMessage, boolean isShownLong) {
        if (isShownLong)
            Toast.makeText(context, strToastMessage, Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context, strToastMessage, Toast.LENGTH_SHORT).show();
    }

    public static void showAlertDialogWith1Button(Context context, String messageToShowOnAlert, final AlertDialog_OnClickInterface
            mAlertDialog_OnClickListener, String buttonText, final String strTAG, boolean isCancellable) {

        if (context == null) {
            return;
        }
        AlertDialog.Builder alertDialog_builder = new AlertDialog.Builder(context);
        alertDialog_builder.setCancelable(isCancellable);
        alertDialog_builder.setMessage(messageToShowOnAlert);

        if (buttonText == null) {
            buttonText = context.getString(android.R.string.ok);
        }
        final String finalButtonText = buttonText;

        alertDialog_builder.setPositiveButton(buttonText, new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mAlertDialog_OnClickListener != null && strTAG != null) {
                            mAlertDialog_OnClickListener.onAlertDialogButtonClicked(
                                    finalButtonText, strTAG);
                        }
                    }
                });
        alertDialog_builder.show();
    }

    public static void showAlertDialogWith_TwoButtons(Context context, String messageToShowOnAlert, final AlertDialog_OnClickInterface
            mAlertDialog_OnClickListener, String positiveButtonText, String negativeButtonText, final String strTAG, boolean isCancellable) {

        if (context == null) {
            return;
        }
        AlertDialog.Builder alertDialog_builder = new AlertDialog.Builder(context);
        alertDialog_builder.setCancelable(isCancellable);
        alertDialog_builder.setMessage(messageToShowOnAlert);

        if (positiveButtonText == null) {
            positiveButtonText = context.getString(android.R.string.ok);
        }
        final String finalPositiveButtonText = positiveButtonText;
        alertDialog_builder.setPositiveButton(positiveButtonText, new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (mAlertDialog_OnClickListener != null && strTAG != null) {
                            mAlertDialog_OnClickListener.onAlertDialogButtonClicked(
                                    finalPositiveButtonText, strTAG);
                        }
                    }
                });

        if (negativeButtonText == null) {
            negativeButtonText = context.getString(android.R.string.cancel);
        }
        final String finalNegativeButtonText = negativeButtonText;
        alertDialog_builder.setNegativeButton(negativeButtonText, new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (mAlertDialog_OnClickListener != null && strTAG != null) {
                            mAlertDialog_OnClickListener.onAlertDialogButtonClicked(
                                    finalNegativeButtonText, strTAG);
                        }
                    }
                });

        alertDialog_builder.show();
    }

    public static ProgressDialog showProgressDialog(Context context, String msgOnProgressDialog, boolean isCancellable) {
        ProgressDialog progressDialog = null;
        if (context != null) {
            progressDialog = new ProgressDialog(context);

            if (msgOnProgressDialog == null)
                msgOnProgressDialog = context.getString(R.string.Loading);

            progressDialog.setMessage(msgOnProgressDialog);
            progressDialog.setCancelable(isCancellable);
            progressDialog.show();
        }
        return progressDialog;
    }

    /**
     * method to check if internet is connected or not
     *
     * @param mContext Context
     * @return true if connected else false
     */
    public static boolean isInternetAvailable(Context mContext) {

        ConnectivityManager cm = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiNetwork = cm
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }

        NetworkInfo mobileNetwork = cm
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        //activeNetwork.getType() == ConnectivityManager.TYPE_WIFI
        if (activeNetwork != null && activeNetwork.isConnected()) {
            return true;
        }

        return false;
    }

    public static void openFragmentByReplacing(FragmentActivity fragmentActivity, int frameLayoutId,
                                               Fragment mFragment, String strTagForBackStack,
                                               Bundle mBundleArguments) {

        if (mBundleArguments != null) {
            mFragment.setArguments(mBundleArguments);
        }

        AppLog.v(LOG_TAG, "in openFragmentByReplacing() before replacing new mFragment with strTagForBackStack:"
                + strTagForBackStack + " backStackEntryCount:" + fragmentActivity.
                getSupportFragmentManager().getBackStackEntryCount());

        try {
            fragmentActivity.getSupportFragmentManager().beginTransaction()
                    .replace(frameLayoutId, mFragment)
                    .addToBackStack(strTagForBackStack)
                    .commit();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public static void setTypeFaceForTextView_abeatbykai(Context context, TextView textView_toSetTypeFace) {
        if (context != null) {
            if (textView_toSetTypeFace != null) {

                textView_toSetTypeFace.setTypeface(Typeface.createFromAsset(context.getAssets(),
                        "fonts/abeatbykai_regular_font.otf"));
            } else
                AppLog.e(LOG_TAG, "mTextView null in setTypeFaceForTextView_abeatbykai()");
        } else
            AppLog.e(LOG_TAG, "context null in setTypeFaceForTextView_abeatbykai()");
    }

    public static String getDeviceID(Context context) {

        String deviceId = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return deviceId != null ? deviceId : "";
    }

    /**
     * check if Camera Permission is available to the app then returns camera Intent object
     *
     * @param activity
     * @param cameraPermissionIntentRequestCode
     * @return if permission is available then returns camera Intent object
     */
    public static Intent checkCameraPermission(Activity activity, int cameraPermissionIntentRequestCode) {
        //Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Intent takePictureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {

                    return takePictureIntent;

                } else {

                    if (activity.shouldShowRequestPermissionRationale(android.Manifest.permission.
                            CAMERA)) {
                        AppUtil.showToast(activity, "No Permission to use the Camera services", true);
                    }

                    activity.requestPermissions(new String[]{android.Manifest.permission.CAMERA},
                            cameraPermissionIntentRequestCode);
                }

            } else {
                return takePictureIntent;

            }
        } else {
            AppUtil.showAlertDialogWith1Button(activity, activity.getString(R.string.
                    YourDeviceDoesntHaveASupportedCameraApp), null, null, LOG_TAG, false);
        }

        return null;
    }


    public static String saveBitmapOfImageToFile(Context context, Bitmap bitmapToSaveToFile) throws IOException {
        FileOutputStream out = null;
        String imageFileName = getANewImageFileName(context);
        try {
            out = new FileOutputStream(imageFileName);
            if (bitmapToSaveToFile.compress(Bitmap.CompressFormat.PNG, 100, out)) {
                return imageFileName;
            }
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return imageFileName;
    }

    private static String getANewImageFileName(Context context) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
//mCurrentPhotoPath:/storage/emulated/0/Android/data/trustid.interviewapp/files/Pictures/JPEG_20160727_223710_-1435487978.jpg
        return String.valueOf(image);
    }

    /**
     * Hide Soft Keyboard
     *
     * @param activity
     */
    public static void hideSoftKeyboard(Activity activity) {
        if (activity != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(
                    Activity.INPUT_METHOD_SERVICE);
            View focusView = activity.getCurrentFocus();
            if (focusView != null) {
                inputMethodManager.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
            }
        }
    }

 /*   public static void startNavigationInGoogleMapsIntentWithLatLng(Activity activity, String latitude, String longitude) {

        // src: https://developers.google.com/maps/documentation/android-api/intents
        //Uri.parse("geo:37.7749,-122.4194(myLabel)?q=" + Uri.encode("1st & Pike, Seattle")");
        Uri uri = Uri.parse("google.navigation:q=" + latitude + "," + longitude + "&mode=d");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(mapIntent, AppConstants.REQUEST_CODE_GOOGLE_MAPS_NAVIGATION, null);
        } else {
            AppLog.e(LOG_TAG, "mapIntent.resolveActivity(getPackageManager()) returns null");
            showToast(activity, "Can't find Google Maps App for Navigation!", true);
        }
    }*/

}