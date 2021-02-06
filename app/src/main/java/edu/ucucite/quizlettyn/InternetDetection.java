package edu.ucucite.quizlettyn;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetDetection {
    public static void detectInternetAvailability(Context context){
        if (isNetworkAvailable(context) == false){
            showAlertInternetError(context);
        }
    }


    private static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



    private static void showAlertInternetError(final Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getString(R.string.internet_error))
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        detectInternetAvailability(context);
                    }
                });

        // Create the AlertDialog object and return it
        builder.create();
        builder.show();
    }


}
