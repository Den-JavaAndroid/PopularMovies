package com.example.denx7.popularmovies.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;


public class NetworkUtils {


    /**
     * Detect Internet Connection Status
     */
    public static boolean isOnline(Context ctx) {
        boolean isConnected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
            int[] networkTypes = {ConnectivityManager.TYPE_WIFI, ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_ETHERNET};
            for (int networkType : networkTypes) {
                NetworkInfo netInfo = cm.getNetworkInfo(networkType);
                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                    isConnected=true;
                }
            }
        } catch (Exception e) {
            Log.e("ERROR", "ERROR in checking connectivity");
            isConnected = false;
        }
        if(!isConnected){
            Log.w("WARNING", "Internet connection not found");
            Toast toast = Toast.makeText(ctx,
                    "Internet connection not found", Toast.LENGTH_LONG);
            toast.show();
        }
        System.out.println(isConnected + "+++=====================================");
        return isConnected;
    }


}
