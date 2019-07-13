package com.athira.otpverify;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by root on 8/1/18
 */

public class SharedAppPreference {
    private static final String NAME_PREFS = "app_preferences";
    private static SharedPreferences sharedPreferences;

    public SharedAppPreference(Context mContext) {
        if (sharedPreferences == null) {
            sharedPreferences = mContext.getSharedPreferences(NAME_PREFS, Context.MODE_PRIVATE);
        }
    }

    public void putString(String key, String value) {
        remove(key);
        sharedPreferences.edit().putString(key, value).apply();
    }

    public void putBoolean(String key, boolean value) {
        remove(key);
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public void putInt(String key, int value) {
        remove(key);
        sharedPreferences.edit().putInt(key, value).apply();
    }

    public void putLong(String key, long value) {
        remove(key);
        sharedPreferences.edit().putLong(key, value).apply();
    }


    public String getString(String key) {
        if (sharedPreferences.contains(key))
            return sharedPreferences.getString(key, null);
        return null;
    }

    public boolean getBoolean(String key) {
        if (sharedPreferences.contains(key))
            return sharedPreferences.getBoolean(key, false);
        return false;
    }

    public int getInt(String key) {
        if (sharedPreferences.contains(key))
            return sharedPreferences.getInt(key, 0);
        return 0;
    }

    public long getLong(String key) {
        if (sharedPreferences.contains(key))
            return sharedPreferences.getLong(key, 0);
        return 0;
    }

    public void remove(String key) {
        if (sharedPreferences.contains(key))
            sharedPreferences.edit().remove(key).apply();
    }

    public Boolean getContains(String key) {
        if (sharedPreferences.contains(key))
            return true;
        else
            return false;
    }

    public void logout() {
        sharedPreferences.edit().clear().apply();
    }
}