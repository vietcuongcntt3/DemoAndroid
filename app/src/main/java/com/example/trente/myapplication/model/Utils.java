package com.example.trente.myapplication.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

public class Utils {
    public static final int LOGIN_NORMAL = 0;
    public static final int LOGIN_FACEBOOK = 1;
    public static final int LOGIN_GOOGLE = 2;

    public static String APP_TOKEN = "";
    public static String FCM_TOKEN = "";

    //Email Validation pattern
    public static final String regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";

    //Fragments Tags
    public static final String Login_Fragment = "Login_Fragment";
    public static final String SignUp_Fragment = "SignUp_Fragment";
    public static final String ForgotPassword_Fragment = "ForgotPassword_Fragment";

    private static SimpleDateFormat currentFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat afterFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");


    /**
     * The method use to send param to request
     *
     * @param url
     * @param order_id
     * @return
     */
    public static String createURL(String url, String order_id) {

        String result = String.format(url, order_id);
        return result;
    }

    /**
     *
     * @param data
     * @param format
     * @return
     */
    public static boolean isValidlFormat(String data, String format) {
        Pattern p = Pattern.compile(format);
        java.util.regex.Matcher m = p.matcher(data);

        return m.matches();
    }

    /**
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;

    }

    /**
     * The method use to check empty string
     * @param data
     * @return
     */
    public static boolean isTextEmpty(String data) {
        return TextUtils.isEmpty(data.trim());
    }

    /**
     * The method use to check lengh of data
     * @param data
     * @param count
     * @return
     */
    public static boolean isGreaterThan(String data, int count) {
        return (data.trim().length() >= count);
    }

    /**
     * The method use to get distance
     *
     * @param latLng_1
     * @param latLng_2
     * @return
     */
    /*public static String distFrom(LatLng latLng_1, LatLng latLng_2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(latLng_2.latitude - latLng_1.latitude);
        double dLng = Math.toRadians(latLng_2.longitude - latLng_1.longitude);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(latLng_1.latitude)) * Math.cos(Math.toRadians(latLng_2.latitude)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float dist = (float) (earthRadius * c)/1000;
        DecimalFormat newFormat = new DecimalFormat("#.##");
        //dist = Float.parseFloat(newFormat.format(dist));
        String result = newFormat.format(dist);

        return result;
    }*/

    /**
     * The method use to convert time to display
     *
     * @param time
     * @return
     */
    public static String convertTime(String time){
        try {
            Date newDate = currentFormat.parse(time);
            String dateConvert = afterFormat.format(newDate);
            return dateConvert;
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
}
