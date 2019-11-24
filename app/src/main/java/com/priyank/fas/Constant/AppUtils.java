package com.priyank.fas.Constant;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class AppUtils {

    static SharedPreferences sharedPreferences;

    public static void Toast(Context context, String msg) {
        Toast.makeText(context, "  " + msg + "  ", Toast.LENGTH_SHORT).show();
    }

    public static void enterPrefrences(Context context, String key, String value) {
        sharedPreferences = context.getSharedPreferences("MyPrefrences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static SharedPreferences getPrefrences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefrences", Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    public static String getPrefrencesValues(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefrences", Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(key, null);
        return value;
    }

    public static void cleanPrefrences(Context context, String key) {
        context.getSharedPreferences("MyPrefrences", Context.MODE_PRIVATE).edit().remove(key).commit();
    }
}
