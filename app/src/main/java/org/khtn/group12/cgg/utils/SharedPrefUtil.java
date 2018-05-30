package org.khtn.group12.cgg.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefUtil {

    private static final String APP_PREFS = "application_preferences";

    private Context mContext;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    // Shared pref mode
    private int PRIVATE_MODE = 0;

    public SharedPrefUtil(Context mContext) {
        this.mContext = mContext;
        mSharedPreferences = mContext.getSharedPreferences(APP_PREFS, PRIVATE_MODE);
        mEditor = mSharedPreferences.edit();
    }

    public void storeUser(String name, String password, String uid) {
        this.storeUsername(name);
        this.storePassword(password);
        this.storeUID(uid);
    }

    public void storeUsername(String name) {
        mEditor.putString(AppController.SAVE_USER_NAME, name);
        mEditor.commit();
    }

    public String getUsername() {
        return mSharedPreferences.getString(AppController.SAVE_USER_NAME, "");
    }

    public void storePassword(String name) {
        mEditor.putString(AppController.SAVE_PASSWORD, name);
        mEditor.commit();
    }

    public String getPassword() {
        return mSharedPreferences.getString(AppController.SAVE_PASSWORD, "");
    }

    public void storeUID(String uid) {
        mEditor.putString(AppController.SAVE_UID, uid);
        mEditor.commit();
    }

    public String getUID() {
        return mSharedPreferences.getString(AppController.SAVE_UID, "");
    }

    public void clear() {
        this.storeUser("", "", "");
    }
}
