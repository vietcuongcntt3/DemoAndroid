package com.example.trente.myapplication.Tictactoe.ultils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by cuongnv on 6/13/19.
 */

public class SharedPreferencesUtils {
    private static final String SHARED_PREFERENCE_NAME = "Garage";
    private SharedPreferences mPreferences;

    public SharedPreferencesUtils(Context context) {
        if (mPreferences == null && context != null) {
            mPreferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME,
                    Context.MODE_PRIVATE);
        }
    }

    public boolean writeStringPreference(String key, String value) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(key, value);
        boolean success = editor.commit();
        return success;
    }

    public String readStringPreference(String key, String defValue) {
        String result = mPreferences.getString(key, defValue);
        return result;
    }

    public boolean writeIntegerPreference(String key, Integer value) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(key, value);
        boolean success = editor.commit();
        return success;
    }

    public Integer readIntegerPreference(String key, int defValue) {
        Integer result = mPreferences.getInt(key, defValue);
        return result;
    }

    public boolean writeBooleanPreference(String key, boolean value) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean(key, value);
        boolean success = editor.commit();
        return success;
    }

    public Boolean readIntegerPreference(String key, boolean defValue) {
        boolean result = mPreferences.getBoolean(key, defValue);
        return result;
    }
}
